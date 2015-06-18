package com.philips.middleware.canonical;

import java.util.Date;
import java.util.List;

public class CanonicalDAO {
	
	final static DynamoDBConnection db = new DynamoDBConnection();

	public String getProductForId(String productId) {
		return "{'product':{'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance','dc.identifier':'PRD-HC781342','metadata':{'dc.country':'AA','dc.language':'en'},'codes':{'code':[{'type':'PH-Code-CTN','text':'HC781342'},{'type':'PH-Code-DTN','text':'781342'}]},'name':{'descriptor':'MR-System-'" + new Date().getTime() + ",'concept':'Ingenia'},'marketingtext':{'header':'At the forefront of clinical excellence -- Diagnostic confidence, explore advanced applications, and generate the productivity required to meet today’s healthcare challenges with the Ingenia 3.0T. Through dStream, Ingenia delivers premium image quality with digital clarity and speed –and with iPatient, it provides patient-centric imaging, from patient set-up to image result.'},'features':{'feature':[{'dc.country':'AA','dc.language':'en','rank':'2','text':'FEA-100221'},{'dc.country':'AA','dc.language':'en','rank':'3','text':'FEA-100222'}]},'attributes':{'attribute':{'dc.identifier':'TypicalHomogeneityAt45CMDSV','dc.country':'AA','dc.language':'en','unit':'unece.unit.ppm','attributevalue':'≤ 1.8'}}}}";
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
