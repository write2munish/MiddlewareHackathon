package com.philips.xucdm;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.philips.jaxb.ObjectFactory;
import com.philips.jaxb.Products;
import com.philips.jaxb.Products.Product.Feature;

public class XUCDMSerializer implements Serializer {

  String dirtyBaseUri;
  String canonicalBaseUri;
  ObjectFactory factory;
  Resty resty;
  AmazonS3 s3client;

  public XUCDMSerializer(String dirtyBaseUri, String canonicalBaseUri) {
    this.dirtyBaseUri = dirtyBaseUri;
    this.canonicalBaseUri = canonicalBaseUri;
    factory = new ObjectFactory();
    resty = new Resty();
    s3client = new AmazonS3Client(new ProfileCredentialsProvider());
  }

  JSONObject getObject(JSONObject root, String path) throws JSONException {
    JSONObject out = root;
    for ( String p : path.split("\\.") ) {
      out = out.getJSONObject(p);
    }
    return out;
  }
  String findText(JSONArray arr, String text) throws JSONException {
    for ( int i = 0; i < arr.length(); i++ ) {
      if ( text.equals(arr.getJSONObject(i).getString("type")) ) {
        return arr.getJSONObject(i).getString("text");
      }
    }
    return "";
  }
  protected void addProduct(Products products, String productId, String queryString) throws Exception {
    JSONObject root = resty.json(canonicalBaseUri + "/product/" + productId + queryString).toObject();
    products.setDocTimeStamp(new Date(System.currentTimeMillis()));
    products.setDocStatus("draft");
    Products.Product product = factory.createProductsProduct();
    products.setProduct(product);
    JSONObject obj = getObject(root, "product.attributes.attribute");
    product.setCountry(obj.getString("dc.country"));
    String locale = obj.getString("dc.language") + "_" + obj.getString("dc.country");
    product.setLocale(locale);
    JSONArray arr = getObject(root, "product.codes").getJSONArray("code");
    product.setDTN(findText(arr, "PH-Code-DTN"));
    product.setCTN(findText(arr, "PH-Code-CTN"));

    product.setNamingString(factory.createProductsProductNamingString());
    product.getNamingString().setDescriptor(factory.createProductsProductNamingStringDescriptor());
    product.getNamingString().getDescriptor().setDescriptorName(getObject(root, "product.name").getString("descriptor"));
    product.getNamingString().setFamily(factory.createProductsProductNamingStringFamily());
    product.getNamingString().getFamily().setFamilyName(getObject(root, "product.name").getString("concept"));

    arr = getObject(root, "product.features").getJSONArray("feature");
    for ( int i = 0; i < arr.length(); i++ ) {
      obj = arr.getJSONObject(i);
      Feature feature = factory.createProductsProductFeature();
      String featureId = obj.getString("text");
      feature.setFeatureRank(obj.getString("rank"));
      feature.setFeatureCode(featureId);
      obj = resty.json(canonicalBaseUri + "/feature/" + featureId + queryString).object().getJSONObject("feature");
      feature.setFeatureName(obj.getString("name"));
      feature.setFeatureReferenceName(feature.getFeatureName());
      feature.setFeatureLongDescription(obj.getString("description"));
      feature.setFeatureGlossary(obj.getString("glossary"));
      feature.setFeatureTopRank(obj.getString("rank"));
      product.getFeature().add(feature);
    }
  }

  @Override
  public String serialize(Long since, String country, String language)  throws Exception {
    if ( since < 0 || country == null || language == null ) {
      throw new IllegalArgumentException("since/country/language are null or negative");
    }
    JSONArray productArr = resty.json(dirtyBaseUri + "/list/" + since).array();
    Products products = factory.createProducts();
    String queryString = String.format("?country=%s&language=%s", country, language);
    int count = 0;
    for ( int i = 0; i < productArr.length(); i++ ) {
      JSONObject obj = productArr.getJSONObject(i);
      if ( "product".equals(obj.getString("type")) && language.equalsIgnoreCase(obj.getString("language")) && country.equalsIgnoreCase(obj.getString("country")) ) {
        addProduct(products, obj.getString("id"), queryString);
        count++;
      }
    }

    System.out.printf("Processed %d products\n", count);

    JAXBContext ctx = JAXBContext.newInstance(Products.class);
    Marshaller m = ctx.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    StringWriter writer = new StringWriter();
    m.marshal(products, writer);
    return writer.toString();
  }

  void writeToS3(String bucket, String keyName, String content) {
    ObjectMetadata omd = new ObjectMetadata();
    omd.setContentType("application/xml");
    omd.setContentLength(content.length());
    omd.setHeader("filename", keyName);
    PutObjectResult result = s3client.putObject(new PutObjectRequest(bucket, "serializer-output/" + keyName, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), omd));
    System.out.printf("uploaded %s (MD5: %s) to S3 bucket (%s)\n", keyName, result.getContentMd5(), bucket);
  }

  public static void main(String[] args) throws Exception {
    if ( args.length < 5 ) {
      System.err.println("Usage: XUCDMSerializer <dirty-db-api-base-uri> <canonical-api-base-uri> <changes-since-ms> <country> <language>");
    }
    while ( true ) {
      XUCDMSerializer serializer = new XUCDMSerializer(args[0], args[1]);
      String out = serializer.serialize(System.currentTimeMillis() - (60 * 1000L), args[3], args[4]);
      if ( null != out && !"".equals(out.trim()) ) {
        serializer.writeToS3("philips-cloud-tst", String.format("%d.xml", System.currentTimeMillis()), out);
      }
      Thread.sleep(1000L);
    }
  }
}
