package com.philips.healthtech.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.XML;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

public class SourceDiff {

    AmazonKinesis kinesisClient;
    DynamoDB dynamodb;

    static LambdaLogger logger;

    public SourceDiff() {
        AWSClientProvider clientProvider = new AWSClientProvider();
        //s3Client = clientProvider.getS3Client();
        kinesisClient = clientProvider.getKinesisClient();
        dynamodb = new DynamoDB(clientProvider.getDynamoDBClient());
    }

    public void handleRequest(InputStream recordContainerInputStream, OutputStream outputStream, Context context) {
        logger = context.getLogger();
        logger.log("Start handle");
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(new MyNameStrategy());
        RecordContainer recordContainer;
		try {
			recordContainer = mapper.readValue(recordContainerInputStream, RecordContainer.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        
        for (Record record : recordContainer.getRecords()) {
        	logger.log("new image " + record.getDynamodb().getNewImage());
        	try {
				SourceDelta delta = new SourceDelta(mapper.writeValueAsString(record.getDynamodb().getOldImage()), mapper.writeValueAsString(record.getDynamodb().getNewImage()));
				if(delta.isDifferent()) {
					logger.log("keys: " + record.getDynamodb().getKeys().toString());
					String id = JsonPath.read(record.getDynamodb().getKeys().toString(), "$.id.S");
					String json = mapper.writeValueAsString(record.getDynamodb().getNewImage());
					logger.log("Put on kinesis");
					putOnKinesis(id, json, delta);
					logger.log("id: " + id);
					logger.log("json: " + json);
				} else {
					logger.log("Nothing changed");
				}
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}
        
        try {
			new OutputStreamWriter(outputStream).write("ok");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

    protected void putOnKinesis(String id, String jsonString, SourceDelta sourceDelta) {
        String payload = getKinesisPayload(id, jsonString, sourceDelta);

        //random for now
        Random random = new Random();

        PutRecordRequest putRecordRequest = new PutRecordRequest()
                .withStreamName("delta-to-canonical2")
                .withData(ByteBuffer.wrap(payload.getBytes()))
                .withPartitionKey(Long.toString(random.nextLong()));

        kinesisClient.putRecord(putRecordRequest);

        logger.log("Kinesis record putted: " + payload);
    }

    /*protected String getXmlString(S3EventNotification.S3EventNotificationRecord s3Notification){
        String bucket = s3Notification.getS3().getBucket().getName();
        String key = s3Notification.getS3().getObject().getKey();

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);

        S3Object xmlObject = s3Client.getObject(getObjectRequest);
        return streamToString(xmlObject.getObjectContent());
    }*/

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
