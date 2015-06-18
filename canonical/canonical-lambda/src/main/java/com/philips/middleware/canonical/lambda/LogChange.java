package com.philips.middleware.canonical.lambda;

import java.io.IOException;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;

import com.google.gson.Gson;
import com.philips.middleware.canonical.lambda.ChangeSet;

public class LogChange {

	private static String url = "http://ec2-54-154-230-236.eu-west-1.compute.amazonaws.com/logchange";

	static Gson gson = new Gson();
	 
	public static void logChange(ChangeSet set) throws IOException, JSONException{
		logChange(url,set);
	}

	public static void logChange(String url, ChangeSet set) throws IOException, JSONException{
		
		JSONObject someJson = new JSONObject(gson.toJson(set));
		new Resty().json(url,Resty.content(someJson ));
	}
}
