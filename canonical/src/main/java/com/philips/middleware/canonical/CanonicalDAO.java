package com.philips.middleware.canonical;

import java.util.List;

public class CanonicalDAO {
	
	final static DynamoDBConnection db = new DynamoDBConnection();

	public String getProductForId(String productId) {
		return db.getItemString(productId, "prod_id", "can_product");
	}

	public String getFeatureForId(String featureId) {
		return db.getItemString(featureId, "fea_id", "can_feature");
	}

	public List<String> getProductIdsForFeatureId(String featureId) {
		return db.getItemXIdsForItemYId(featureId, "product_id", "feature_id");
	}

	public List<String> getFeatureIdsForProductId(String productId) {
		return db.getItemXIdsForItemYId(productId, "feature_id", "product_id");
	}

}
