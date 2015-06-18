package com.philips.middleware.canonical;

import java.util.List;

public class CanonicalService {

	final CanonicalDAO canDAO = new CanonicalDAO();

	public String getProductForId(String productId) {
		return canDAO.getProductForId(productId);
	}

	public String getFeatureForId(String featureId) {
		return canDAO.getFeatureForId(featureId);
	}

	public List<String> getProductIdsForFeatureId(String featureId, String language, String country) {
		return canDAO.getProductIdsForFeatureId(language+"_"+country+"_"+featureId);
	}

	public List<String> getFeatureIdsForProductId(String productId, String language, String country) {
		return canDAO.getFeatureIdsForProductId(language+"_"+country+"_"+productId);
	}
}
