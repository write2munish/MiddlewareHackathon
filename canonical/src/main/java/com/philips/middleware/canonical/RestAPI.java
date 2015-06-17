package com.philips.middleware.canonical;

import static spark.Spark.*;
import com.philips.middleware.canonical.CanonicalDAO;

public class RestAPI {
	public static void main(String[] args) {

		final CanonicalDAO canDAO = new CanonicalDAO();

		get("/product/:product", "application/json", (req, res) -> {
			String productId = req.params(":product");
			res.body(canDAO.getProductForId(productId));
			return res.body();
		});

		get("/feature/:feature", "application/json", (req, res) -> {
			String featureId = req.params(":feature");
			res.body(canDAO.getFeatureForId(featureId));
			return res.body();
		});
	}
}