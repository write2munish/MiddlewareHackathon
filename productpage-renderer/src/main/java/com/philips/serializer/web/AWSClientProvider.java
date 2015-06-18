package com.philips.serializer.web;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class AWSClientProvider {

    private AWSCredentialsProvider provider;

    public AWSClientProvider() {
        provider = new DefaultAWSCredentialsProviderChain();
    }

    public AmazonS3 getS3Client(){
         return new AmazonS3Client(provider);
    }

}
