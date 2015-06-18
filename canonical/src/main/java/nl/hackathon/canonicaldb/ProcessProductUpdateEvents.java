package nl.hackathon.canonicaldb;

import java.io.IOException;
import java.util.Date;

import us.monoid.web.Content;
import us.monoid.web.Resty;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent.KinesisEventRecord;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

public class ProcessProductUpdateEvents implements RequestHandler<KinesisEvent, Void>{
        
    private static String url = "http://ec2-54-154-230-236.eu-west-1.compute.amazonaws.com/logchange";

	private void saveUpdatedProduct(Item item) {
    	if(item != null){
    		AmazonDynamoDBClient client = new AmazonDynamoDBClient(new DefaultAWSCredentialsProviderChain());
    		client.setRegion(Region.getRegion(Regions.EU_WEST_1));
    		DynamoDB dynamoDB = new DynamoDB(client);
    		Table table = dynamoDB.getTable("can_product");
    		PutItemOutcome outcome = table.putItem(item);
    		
    	}
	}

    
	private Item convertToProductItem(KinesisEventRecord rec, final Context context){
		Item item = null;
		try {
			//context.getLogger().log(new String(rec.getKinesis().getData().array()));
			
			String received = new String(rec.getKinesis().getData().array());
			JSONObject data = new JSONObject(received);
			JSONObject content = data.getJSONObject("content");
			JSONObject canonicalproduct = convertToProductJSON(content);
			PrimaryKey pk = new PrimaryKey("prod_id", data.getString("id"));
			item = new Item()
			.withPrimaryKey(pk)
			.withJSON("product", canonicalproduct.toString());

			
    		try {
				final JSONObject change = new JSONObject();
				change.put("id", data.getString("id"));
				change.put("type", "product");
				change.put("country", data.getString("id").substring(0, 2));
				change.put("language", data.getString("id").substring(4, 6));
				change.put("lastmodified", new Date());
				//change.put("sequencenumber", data.getString("sequencenumber"));
				Runnable post = new Runnable() {
					
					public void run() {
						try {
							context.getLogger().log("Start sending : "+change.toString());
							String result = new Resty().json(url , Resty.put(new Content("application/json",change.toString().getBytes()))).toString();
							context.getLogger().log(result);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}					}
				};
				 new Thread(post).start();
				
			} catch (JSONException e) {
				e.printStackTrace();
			} 
    		
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    return item;
    }

	public Void handleRequest(KinesisEvent event, Context context) {
    	for(KinesisEventRecord rec : event.getRecords()) {
        	saveUpdatedProduct(convertToProductItem(rec,context));
        }
    	return null;
	}
    
	public JSONObject convertToProductJSON(JSONObject source){
		JSONObject canonicalproduct = new JSONObject();
		try {
			JSONObject stepproduct = source.getJSONObject("Product");
			JSONObject product = new JSONObject();	
			JSONObject name = new JSONObject();
			String stepName = stepproduct.getJSONObject("Name").getString("content");
			name.put("name", stepName);
			String concept = stepName.substring(0,stepName.indexOf(" "));
			name.put("concept", concept);
			product.put("name", name);
			canonicalproduct.put("product", product);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    return canonicalproduct;
}
	
	private void postChangeLog(){
		
	}
    
}