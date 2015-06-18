package com.philips.middleware.canonical.lambda;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import us.monoid.json.JSONException;

import com.google.gson.Gson;
import com.philips.middleware.canonical.lambda.ChangeSet.Type;

public class LogChangeTest {

	@Test
	public void testLogChange() throws IOException, JSONException {
		Gson gson = new Gson();
		ChangeSet set =  new ChangeSet("p123",Type.PRODUCT,"US","en",new Timestamp(new Date().getTime()),1l);
		System.out.println(gson.toJson(set));
		System.out.println(Type.PRODUCT.toString());
		//LogChange.logChange(set);
	}
}
