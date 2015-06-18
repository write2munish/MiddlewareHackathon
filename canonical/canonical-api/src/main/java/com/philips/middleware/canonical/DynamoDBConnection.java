package com.philips.middleware.canonical;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class DynamoDBConnection {
	
	private static DynamoDB dynamoDB  = null;
	
	public DynamoDBConnection() {
		AmazonDynamoDB dynamoDBClient = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
		Region euWest1 = Region.getRegion(Regions.EU_WEST_1);
		dynamoDBClient.setRegion(euWest1);
		dynamoDB = new DynamoDB(dynamoDBClient);
	}
	
	public String getItemString(String id, String idField, String table) {
		Table productTable = getDynamoDBTable(table);

		Item item = productTable.getItem(idField, id);

		if (item != null) {
			return item.toJSON();
		} else {
			return "{ \"ERROR\": \"No item with " + idField + " " + id
					+ " found in table " + table + "\" }";
		}
	}

	public List<String> getItemXIdsForItemYId(String ItemYId, String ItemX,
			String ItemY) {
		List<String> itemXIds = new ArrayList<String>();
		Table linkTable = getDynamoDBTable("can_product_feature_link");
		for (Item item : linkTable.query(ItemY, ItemYId)) {
			itemXIds.add(item.getString(ItemX));
		}
		return itemXIds;
	}

	public Table getDynamoDBTable(String tableName) {
		return dynamoDB.getTable(tableName);
	}
}
