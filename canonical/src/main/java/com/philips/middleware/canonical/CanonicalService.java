package com.philips.middleware.canonical;

import java.util.List;

public class CanonicalService {

	final CanonicalDAO canDAO = new CanonicalDAO();

	public String getProductForId(String productId, String language, String country) {
		return canDAO.getProductForId(language+"_"+country+"_"+productId);
	}

	public String getFeatureForId(String featureId, String language, String country) {
		return canDAO.getFeatureForId(language+"_"+country+"_"+featureId);
	}

	public List<String> getProductIdsForFeatureId(String featureId, String language, String country) {
		return canDAO.getProductIdsForFeatureId(language+"_"+country+"_"+featureId);
	}

	public List<String> getFeatureIdsForProductId(String productId, String language, String country) {
		return canDAO.getFeatureIdsForProductId(language+"_"+country+"_"+productId);
	}
}
