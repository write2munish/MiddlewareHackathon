package com.philips.healthtech.content;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class AWSClientProvider {
    private static Regions REGION = Regions.US_EAST_1;
    private String endpoint = "https://preview-dynamodb.us-east-1.amazonaws.com";

    private AWSCredentialsProvider provider;

    public AWSClientProvider() {
        provider = new DefaultAWSCredentialsProviderChain();
    }

    public AmazonS3 getS3Client(){
         return new AmazonS3Client(provider).withRegion(REGION);
    }

    public AmazonKinesis getKinesisClient(){
        return new AmazonKinesisClient(provider).withRegion(REGION);
    }

    public AmazonDynamoDB getDynamoDBClient(){
        return new AmazonDynamoDBClient(provider).withEndpoint("https://preview-dynamodb.us-east-1.amazonaws.com");
    }

    public AmazonDynamoDB getDynamoDBClient(BasicAWSCredentials credentials) {
        return new AmazonDynamoDBClient(credentials).withEndpoint("https://preview-dynamodb.us-east-1.amazonaws.com");
    }
}
