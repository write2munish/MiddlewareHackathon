package nl.hackathon.canonicaldb;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class CanonicalDAO {
	public List<String> getProductIdsForFeatureId(String featureId) {
		return getItemXIdsForItemYId(featureId, "product_id", "feature_id");
	}
	
	public List<String> getFeatureIdsForProductId(String productId) {
		return getItemXIdsForItemYId(productId, "feature_id", "product_id");
	}
	
	private List<String> getItemXIdsForItemYId(String ItemYId, String ItemX, String ItemY) {
		List<String> itemXIds = new ArrayList<String>();
		DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(new ProfileCredentialsProvider()));
		Table linkTable = dynamoDB.getTable("can_product_feature_link");	
		for ( Item item : linkTable.query(ItemY, ItemYId ) ) {
			itemXIds.add(item.getString(ItemX));
		}
		return itemXIds;
	}
	
}
