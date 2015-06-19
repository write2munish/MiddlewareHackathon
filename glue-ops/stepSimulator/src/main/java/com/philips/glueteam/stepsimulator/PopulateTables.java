package com.philips.glueteam.stepsimulator;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.*;

/**
 * Created by mikke on 17-06-2015.
 */
public class PopulateTables {
    private static final Random RANDOM = new Random(0L);
    public static void main(String[] args) throws InterruptedException {
//        Region region = Region.getRegion(Regions.EU_WEST_1);
//        AmazonDynamoDBClient dbClient = region.createClient(AmazonDynamoDBClient.class, StepSimulator.CREDENTIALS, new ClientConfiguration());
//        DynamoDB db = new DynamoDB(dbClient);

        AmazonDynamoDBClient dbClient1 = new AmazonDynamoDBClient(StepSimulator.CREDENTIALS.getCredentials());
        dbClient1.setEndpoint("https://preview-dynamodb.us-east-1.amazonaws.com");
        DynamoDB db = new DynamoDB(dbClient1);
        Table t = db.getTable(StepSimulator.PREFIX+"products");

        try {
            t.describe();
            System.out.println("Deleting table "+t.getTableName());
            t.delete();
            t.waitForDelete();
        } catch (ResourceNotFoundException e) {
            // Ignore, the table doesnt exist
        }
        CreateTableRequest ctr = new CreateTableRequest();
        ctr.setTableName(StepSimulator.PREFIX + "products");
        ctr.setKeySchema(Arrays.asList(new KeySchemaElement("id", KeyType.HASH), new KeySchemaElement("context", KeyType.RANGE)));
        ctr.setProvisionedThroughput(new ProvisionedThroughput(4L, 10L));
        ctr.setAttributeDefinitions(Arrays.asList(
                new AttributeDefinition("id", ScalarAttributeType.S),
                new AttributeDefinition("context", ScalarAttributeType.S)));
        ctr.setStreamSpecification(new StreamSpecification().withStreamEnabled(Boolean.TRUE).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES));
        System.out.println("Creating table " + t.getTableName());
        t = db.createTable(ctr);
        t.waitForActive();

        System.out.println("Populating table");
        for (String context : StepSimulator.CONTEXT) {
            for (int i = 0;i<StepSimulator.NUMBER_OF_PRODUCTS;i++) {
                String id = String.format("P%d", i + 1);
                t.putItem(new Item()
                                .withString("id", id)
                                .withString("context", context)
                                .withJSON("content", createProductJSON(i, context))
                );
                System.out.println(id);
            }
        }

        for (String context : StepSimulator.CONTEXT) {
            for (int i = 0;i<StepSimulator.NUMBER_OF_FEATURES;i++) {
                String id = String.format("F%d", i + 1);
                t.putItem(new Item()
                                .withString("id", id)
                                .withString("context",context)
                                .withJSON("content", createFeatureJSON(i, context))
                );
                System.out.println(id);
            }
        }
    }

    private static String createFeatureJSON(int i, String context) {
        String id = String.format("P%d",i);

        StringBuilder sb = new StringBuilder();

        sb.append("{")
                .append(" \"id\": \"").append(id).append("\",\n")
                .append(" \"name\": \"").append(id + " name in " + context).append("\",\n")
                .append(" \"values\": [" )
                .append("   { \"attributeId\": \"PH-FEA-Description\", \n")
                .append("    \"value\": \"Advanced MR\" \n")
                .append("   }, \n")
                .append("   { \"attributeId\": \"PH-FEA-Glossary\", \n")
                .append("    \"value\": \"A long text\" \n")
                .append("   }, \n")
                .append("   { \"attributeId\": \"PH-FEA-Rank\", \n")
                .append("    \"value\": \"1\" \n")
                .append("   } ] \n")
                .append("}");

        return sb.toString();
    }

    private static String createProductJSON(int i, String context) {
        String id = String.format("P%d",i);

        StringBuilder sb = new StringBuilder();

        sb.append("{")
                .append(" \"id\": \"").append(id).append("\",\n")
                .append(" \"userType\": \"PRD\",\n")
                .append(" \"name\": \"").append(id+" name in "+context).append("\",\n")
                .append(" \"productReferences\": [\n");


        int numReferences = RANDOM.nextInt(10);
        Set<Integer> refs = new LinkedHashSet<>();
        for (int numRefs = 0;numRefs< numReferences;numRefs++) {
            refs.add(RANDOM.nextInt(1200)+1);
        }
        int idx = 0;
        for (Integer ref : refs) {
            idx++;
            sb.append("  {\n");
            sb.append("   \"type\": \"FeatureReference\",\n");
            sb.append("   \"id\": \"F"+ref+"\"\n");
            sb.append("  }\n");
            if (idx != refs.size()) {
                sb.append(",");
            }
        }

        sb
                .append(" ],\n")
                .append(" \"values\": [" )
                .append("   { \"attributeId\": \"PH-Code-CTN\", \n")
                .append("    \"value\": \"HC781342\" \n")
                .append("   }, \n")
                .append("   { \"attributeId\": \"PH-MT-Descriptor\", \n")
                .append("    \"value\": \"MR system\" \n")
                .append("   }, \n")
                .append("   { \"attributeId\": \"PH-MT-Concept\", \n")
                .append("    \"value\": \"Ingenia\" \n")
                .append("   }, \n")
                .append("   { \"attributeId\": \"PH-MT-MarketingText\", \n")
                .append("    \"value\": \"Some long text\" \n")
                .append("   }, \n")
                .append("   { \"attributeId\": \"TypicalHomogeneityAt45CMDSV\", \n")
                .append("    \"value\": \"≤ 1.1\", \n")
                .append("    \"unitId\": \"unece.unit.ppm\" \n")
                .append("   } ] \n")
                .append("}");

        return sb.toString();
    }
}
