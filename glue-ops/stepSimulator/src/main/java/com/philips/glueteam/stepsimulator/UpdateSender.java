package com.philips.glueteam.stepsimulator;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.Random;

/**
 * Created by mikke on 17-06-2015.
 */
public class UpdateSender {
    public static void main(String[] args) throws InterruptedException {
        AmazonDynamoDBClient dbClient1 = new AmazonDynamoDBClient(StepSimulator.CREDENTIALS.getCredentials());
        dbClient1.setEndpoint("https://preview-dynamodb.us-east-1.amazonaws.com");
        DynamoDB db = new DynamoDB(dbClient1);
        Table t = db.getTable(StepSimulator.PREFIX+"products");
        Random rnd = new Random(0);

        boolean toggle = false;
        while (true) {
            System.out.println("Tick");
            String id;
            if (toggle) {
                id = String.format("P%d",rnd.nextInt(StepSimulator.NUMBER_OF_PRODUCTS)+1);
            } else {
                id = String.format("F%d",rnd.nextInt(StepSimulator.NUMBER_OF_FEATURES)+1);
            }

            for (String context : StepSimulator.CONTEXT) {
                Item i = t.getItem(new PrimaryKey("id", id, "context", context));
                if (rnd.nextBoolean()) {
                    i = i.withString("name", "update "+System.currentTimeMillis());
                    System.out.println("Update id="+id+", context="+context);
                }
                t.putItem(i);
            }
            toggle = !toggle;
            Thread.sleep(1500);
        }
    }
}
