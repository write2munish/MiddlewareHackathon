package com.philips.middleware.canonical;

import static spark.Spark.*;

public class App {
	public static void main(String[] args) {

		port(8080);
		get("/product/:product",
				"application/json",
				(req, res) -> {
					String productId = req.params(":product");
					String country = req.queryParams("country");
					String lang = req.queryParams("language");
					switch (productId.toUpperCase()) {
					case "PRD-HC781342":
						switch (lang.toLowerCase()) {
						case "de":
							res.body("{'product':{'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance','dc.identifier':'PRD-HC781342','metadata':{'dc.country':'DE','dc.language':'de'},'codes':{'code':[{'type':'PH-Code-CTN','text':'HC781342'},{'type':'PH-Code-DTN','text':'781342'}]},'name':{'descriptor':'MR-System','concept':'Ingenia'},'marketingtext':{'header':'Mit Ingenia 3.0T können Sie dank hoher Diagnosesicherheit, fortschrittlicher Anwendungen und effizienter Abläufe den Herausforderungen von heute begegnen. dStream sorgt zudem für eine erstklassige Bildqualität in kürzester Zeit, und dank iPatient bietet das Ingenia 3.0T patientenzentrierte Bildgebung – von der Patientenvorbereitung bis zum fertigen Bild.'},'features':{'feature':[{'dc.country':'DE','dc.language':'de','rank':'2','text':'FEA-100221'},{'dc.country':'DE','dc.language':'de','rank':'3','text':'FEA-100222'}]},'attributes':{'attribute':{'dc.identifier':'TypicalHomogeneityAt45CMDSV','dc.country':'DE','dc.language':'de','unit':'unece.unit.ppm','attributevalue':'≤ 1.8'}}}}");
							return res.body();

						case "en":
							res.body("{'product':{'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance','dc.identifier':'PRD-HC781342','metadata':{'dc.country':'AA','dc.language':'en'},'codes':{'code':[{'type':'PH-Code-CTN','text':'HC781342'},{'type':'PH-Code-DTN','text':'781342'}]},'name':{'descriptor':'MR-System','concept':'Ingenia'},'marketingtext':{'header':'At the forefront of clinical excellence -- Diagnostic confidence, explore advanced applications, and generate the productivity required to meet today’s healthcare challenges with the Ingenia 3.0T. Through dStream, Ingenia delivers premium image quality with digital clarity and speed –and with iPatient, it provides patient-centric imaging, from patient set-up to image result.'},'features':{'feature':[{'dc.country':'AA','dc.language':'en','rank':'2','text':'FEA-100221'},{'dc.country':'AA','dc.language':'en','rank':'3','text':'FEA-100222'}]},'attributes':{'attribute':{'dc.identifier':'TypicalHomogeneityAt45CMDSV','dc.country':'AA','dc.language':'en','unit':'unece.unit.ppm','attributevalue':'≤ 1.8'}}}}");
							return res.body();

						}

					case "PRD-HC795080":
						switch (lang.toLowerCase()) {
						case "en":
							res.body("{'product':{'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance','dc.identifier':'PRD-HC795080','metadata':{'dc.country':'AA','dc.language':'en'},'codes':{'code':[{'type':'PH-Code-CTN','text':'HC795080'},{'type':'PH-Code-DTN','text':'795080'}]},'name':{'descriptor':'Ultrasound system','concept':'ClearVue'},'marketingtext':{'header':'With Philips quality imaging through and through, the ClearVue 350 with Active Array technology offers image quality designed to enhance diagnostic confidence, sophisticated yet simple features, and advances in ease of use and reliability.'},'features':{'feature':{'dc.country':'AA','dc.language':'en','rank':'3','text':'FEA-100222'}}}}");
							return res.body();
						case "de":
							res.body("{'product':{'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance','dc.identifier':'PRD-HC795080','metadata':{'dc.country':'DE','dc.language':'de'},'codes':{'code':[{'type':'PH-Code-CTN','text':'HC795080'},{'type':'PH-Code-DTN','text':'795080'}]},'name':{'descriptor':'Ultraschallsystem','concept':'ClearVue'},'marketingtext':{'header':'Das ClearVue 350 mit Active Array Technologie bietet Philips Qualität auf ganzer Linie und sorgt mit einer hohen Bildqualität und ausgereiften, einfach anzuwendenden Funktionen für eine Erhöhung der Diagnosesicherheit und für Verbesserungen bei der Benutzerfreundlichkeit und Zuverlässigkeit.'},'features':{'feature':{'dc.country':'DE','dc.language':'de','rank':'3','text':'FEA-100222'}}}}");
							return res.body();
						}
					default:
						res.status(401);
						res.body("Wrong ID, Try /PRD-HC781342?country=DE&language=DE");
						return res.body();
					}

				});

		get("/feature/:feature",
				"application/json",
				(req, res) -> {
					String productId = req.params(":feature");
					String country = req.queryParams("country");
					String lang = req.queryParams("language");
					switch (productId.toUpperCase()) {
					case "FEA-100221":
						switch (lang.toLowerCase()) {
						case "de":
							res.body("{'feature':{'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance','dc.identifier':'FEA-100221','metadata':{'dc.country':'DE','dc.language':'de'},'name':'dStream','description':'Schnell und zuverlässig','glossary':'Mit dStream erhalten Sie in kürzester Zeit erstklassige digitale MR-Bilder mit gleichbleibend hoher Bildqualität und diagnostischer Aussagekraft.','rank':'1'}}");
							return res.body();
						case "en":
							res.body("{'feature':{'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance','dc.identifier':'FEA-100221','metadata':{'dc.country':'AA','dc.language':'en'},'name':'dStream','description':'dStream','glossary':'dStream enables you to get information, consistently in the same time by delivering premium image quality with digital clarity and speed.*','rank':'1'}}");
							return res.body();
						}
					case "FEA-100222":
						switch (lang.toLowerCase()) {
						case "de":
							res.body("{'feature':{'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance','dc.identifier':'FEA-100222','metadata':{'dc.country':'DE','dc.language':'de'},'name':'dStream','description':'Schnell und zuverlässig','glossary':'Mit dStream erhalten Sie in kürzester Zeit erstklassige digitale MR-Bilder mit gleichbleibend hoher Bildqualität und diagnostischer Aussagekraft.','rank':'1'}}");
							return res.body();
						case "en":
							res.body("{'feature':{'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance','dc.identifier':'FEA-100222','metadata':{'dc.country':'AA','dc.language':'en'},'name':'dStream','description':'dStream','glossary':'dStream enables you to get information, consistently in the same time by delivering premium image quality with digital clarity and speed.*','rank':'1'}}");
							return res.body();
						}	
					default:
						res.status(401);
						res.body("Wrong ID, Try - /FEA-100221?country=DE&language=DE");
						return res.body();
					}

				});
	}
}

