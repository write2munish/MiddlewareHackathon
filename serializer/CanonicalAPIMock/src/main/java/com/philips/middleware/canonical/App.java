package com.philips.middleware.canonical;

import static spark.Spark.*;

public class App {
	public static void main(String[] args) {

		get("/product/:product", "application/json", (req, res) -> {
			String productId = req.params(":product");
			switch (productId) {
			case "PRD-HC781342":
				res.body("{'product':{'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance','dc.identifier':'PRD-HC781342','codes':{'code':[{'type':'PH-Code-CTN','text':'HC781342'},{'type':'PH-Code-DTN','text':'781342'}]},'name':{'name':'Ingenia 3.0T MR system','concept':'Ingenia'},'marketingtext':{'header':'At the forefront of clinical excellence -- Diagnostic confidence, explore advanced applications, and generate the productivity required to meet today’s healthcare challenges with the Ingenia 3.0T. Through dStream, Ingenia delivers premium image quality with digital clarity and speed –and with iPatient, it provides patient-centric imaging, from patient set-up to image result.'},'features':{'feature':[{'dc.country':'AA','dc.language':'en','rank':'2','text':'FEA-100221'},{'dc.country':'AA','dc.language':'en','rank':'3','text':'FEA-100222'}]},'attributes':{'attribute':{'dc.identifier':'TypicalHomogeneityAt45CMDSV','dc.country':'AA','dc.language':'en','unit':'unece.unit.ppm','attributevalue':'≤ 1.1'}}}}");
				return res.body();
				
			default:
				res.status(401);
				res.body("Wrong ID, Try /PRD-HC781342");
				return res.body();
			}

		});

		get("/feature/:feature", "application/json", (req, res) -> {
			String productId = req.params(":feature");
			switch (productId) {
			case "FEA-100221":
				res.body("{'feature':{'-xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance','-dc.identifier':'FEA-100221','name':'dStream','description':'dStream','glossary':'dStream enables you to get information, consistently in the same time by delivering premium image quality with digital clarity and speed.*','rank':'1'}}");
				return res.body();
				
			default:
				res.status(401);
				res.body("Wrong ID, Try - /FEA-100221");
				return res.body();
			}

		});
	}
}
