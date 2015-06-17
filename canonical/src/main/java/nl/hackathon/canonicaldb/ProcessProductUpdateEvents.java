package nl.hackathon.canonicaldb;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent.KinesisEventRecord;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

public class ProcessProductUpdateEvents implements RequestHandler<KinesisEvent, Void>{
    public void updateProduct(KinesisEvent event) throws IOException {
    	for(KinesisEventRecord rec : event.getRecords()) {
        	saveUpdatedProduct(convertToProductItem(rec));
        }
    }
    
    private void saveUpdatedProduct(Item item) {
    	if(item != null){
    		DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(new ProfileCredentialsProvider()));
    		Table table = dynamoDB.getTable("can_product");
    		PutItemOutcome outcome = table.putItem(item);
    	}
	}

	private Item convertToProductItem(KinesisEventRecord rec){
		Item item = null;
		try {
			JSONObject data = new JSONObject(new String(rec.getKinesis().getData().array()));
			JSONObject stepproduct = data.getJSONObject("Product");
			JSONObject canonicalproduct = new JSONObject();
			JSONObject product = new JSONObject();	
			canonicalproduct.put("product", product);
			item = new Item()
			.withPrimaryKey("prod_id", stepproduct.getString("ID"))
			.withJSON("product", canonicalproduct.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    return item;
    }

	public Void handleRequest(KinesisEvent event, Context context) {
    	for(KinesisEventRecord rec : event.getRecords()) {
        	saveUpdatedProduct(convertToProductItem(rec));
        }
    	return null;
	}
    
    
}