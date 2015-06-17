package com.philips.healthtech.content;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class SourceDiff implements RequestHandler<S3Event, String> {

    AmazonS3 s3Client;
    AmazonKinesis kinesisClient;
    DynamoDB dynamodb;

    LambdaLogger logger;

    public SourceDiff() {
        AWSClientProvider clientProvider = new AWSClientProvider();
        s3Client = clientProvider.getS3Client();
        kinesisClient = clientProvider.getKinesisClient();
        dynamodb = new DynamoDB(clientProvider.getDynamoDBClient());
    }

    public String handleRequest(S3Event s3Event, Context context) {
        logger = context.getLogger();

        for (S3EventNotification.S3EventNotificationRecord s3EventNotificationRecord : s3Event.getRecords()) {
            String id = getId(s3EventNotificationRecord.getS3().getObject().getKey());
            String xmlString = getXmlString(s3EventNotificationRecord);
            String jsonString = getJsonString(xmlString);
            SourceDelta sourceDelta = putOnDynamoDB(id, jsonString);
            if(sourceDelta.isDifferent()) {
                putOnKinesis(id, jsonString, sourceDelta);
            }
        }
        return "ok";
    }

    protected void putOnKinesis(String id, String jsonString, SourceDelta sourceDelta) {
        String payload = getKinesisPayload(id, jsonString, sourceDelta);

        //random for now
        Random random = new Random();

        PutRecordRequest putRecordRequest = new PutRecordRequest()
                .withStreamName("delta-to-canonical")
                .withData(ByteBuffer.wrap(payload.getBytes()))
                .withPartitionKey(Long.toString(random.nextLong()));

        kinesisClient.putRecord(putRecordRequest);

        logger.log("Kinesis record putted: " + payload);
    }

    protected String getXmlString(S3EventNotification.S3EventNotificationRecord s3Notification){
        String bucket = s3Notification.getS3().getBucket().getName();
        String key = s3Notification.getS3().getObject().getKey();

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);

        S3Object xmlObject = s3Client.getObject(getObjectRequest);
        return streamToString(xmlObject.getObjectContent());
    }

    protected String getJsonString(String xmlString){
        JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
        return xmlJSONObj.toString(4);
    }

    protected SourceDelta putOnDynamoDB(String id, String jsonString){
        Table table = dynamodb.getTable("source_input");

        Item item = new Item()
                .withPrimaryKey("id", id)
                .withJSON("content", jsonString);

        PutItemSpec putItemSpec = new PutItemSpec()
                .withItem(item)
                .withReturnValues(ReturnValue.ALL_OLD);

        PutItemOutcome putItemOutcome = table.putItem(putItemSpec);

        if(putItemOutcome.getItem() != null && putItemOutcome.getItem().getJSON("content") != null){
            String oldContent = putItemOutcome.getItem().getJSON("content");
            logger.log("Existing item with id: " + id + "\n");
            return new SourceDelta(oldContent, jsonString);

        } else {
            //new item
            logger.log("New item with id: " + id+ "\n");
            return new SourceDelta(null, jsonString);

        }
    }

    protected String getId(String key){
        String id = key;
        int lastDotXMLIndex = id.lastIndexOf(".xml");
        if(lastDotXMLIndex > 0){
            id = id.substring(0, lastDotXMLIndex);
        } else {
            throw new IllegalArgumentException("Invalid S3 file key, expecting key_path/[item_id].xml. Given key: " + key);
        }
        int lastSlashIndex = id.lastIndexOf("/");
        if(id.length() > lastSlashIndex){
            if(lastSlashIndex > 0){
                id = id.substring(lastSlashIndex + 1);
            }
            return id;
        } else {
            throw new IllegalArgumentException("Invalid S3 file key, expecting [key_path/]item_id.xml. Given key: " + key);
        }



    }

    protected String getKinesisPayload(String id, String jsonString, SourceDelta sourceDelta){
        KinesisSourceRecordPayload payload = new KinesisSourceRecordPayload(id, jsonString, sourceDelta);

        ObjectMapper mapper = new ObjectMapper();
        String payloadString;
        try {
            payloadString = mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid payload for kinesis", e);
        }

        return payloadString;
    }

    protected String streamToString(final InputStream inputStream) {
        String text = null;
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }

        return text;
    }

}
