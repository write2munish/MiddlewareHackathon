package com.philips.middleware.canonical;

import com.google.gson.Gson;
import spark.servlet.SparkApplication;

import static spark.Spark.get;

public class RestInterface implements SparkApplication {


    private final CanonicalService canonicalService = new CanonicalService();
    private final Gson gson = new Gson();


    @Override
    public void init() {
        get("health-check",
                "application/json",
                (req, res) -> {
                    res.body("Healthy");
                    return res.body();
                });

        get("/product/:productid",
                "application/json",
                (req, res) -> {
                    String productId = req.params(":productid");
                    String language = req.queryParams("language");
                    String country = req.queryParams("country");
                    System.out.println("You're requesting product " + productId
                            + " for country " + country + " in language "
                            + language);
                    res.body(canonicalService.getProductForId(productId));
                    return res.body();
                });

        get("/feature/:feature",
                "application/json",
                (req, res) -> {
                    String featureId = req.params(":feature");
                    String language = req.queryParams("language");
                    String country = req.queryParams("country");
                    System.out.println("You're requesting feature " + featureId
                            + " for country " + country + " in language "
                            + language);
                    res.body(canonicalService.getFeatureForId(featureId));
                    return res.body();
                });

        get("productfeatures/:productid", "application/json", (req, res) -> {
            String productId = req.params(":productid");
            String language = req.queryParams("language");
            String country = req.queryParams("country");
            System.out.println("You're requesting all featureids for product "
                    + productId + " for country " + country + " in language "
                    + language);
            res.body(gson.toJson(canonicalService.getFeatureIdsForProductId(productId, language, country)));
            return res.body();
        });

        get("featureproducts/:featureid", "application/json", (req, res) -> {
            String featureId = req.params(":featureid");
            String language = req.queryParams("language");
            String country = req.queryParams("country");
            System.out.println("You're requesting all productids for feature "
                    + featureId + " for country " + country + " in language "
                    + language);
            res.body(gson.toJson(canonicalService.getProductIdsForFeatureId(featureId, language, country)));
            return res.body();
        });
    }
}