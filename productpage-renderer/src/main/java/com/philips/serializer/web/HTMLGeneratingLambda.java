package com.philips.serializer.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.sun.org.apache.xpath.internal.XPathAPI;

public class HTMLGeneratingLambda implements RequestHandler<S3Event, String> {

    AmazonS3 s3Client;

    LambdaLogger logger;

    public HTMLGeneratingLambda() {
        AWSClientProvider clientProvider = new AWSClientProvider();
        s3Client = clientProvider.getS3Client();
    }

    public String handleRequest(S3Event s3Event, Context context) {
        logger = context.getLogger();

        logger.log ( s3Event.getRecords().size() + " s3 event notification records found");
        for (S3EventNotification.S3EventNotificationRecord s3EventNotificationRecord : s3Event.getRecords()) {
            String id = s3EventNotificationRecord.getS3().getObject().getKey();
    		logger.log ( "found file " + id);
            Document xml = getXmlDocument(s3EventNotificationRecord);
    		logger.log ( "parsed xml " + id);
            
            NodeList nl = null;
			try {
				nl = XPathAPI.selectNodeList(xml.getDocumentElement(), "Product" );
			} catch (TransformerException e) {
				throw new RuntimeException(e);
			}
    		logger.log ( "found " + nl.getLength() + " Products in file " + id);
            for ( int i = 0; i < nl.getLength(); i++ ) {
            	Node productNode = nl.item(i);
            	ProductPageRenderer ppr = new ProductPageRenderer();
            	String html = ppr.getProductPage(productNode);
            	XMLUtil xmlutil = new XMLUtil(productNode);
            	String outputFile = xmlutil.getUniqueProductId() + ".html";
            	logger.log("output file : " + outputFile);
            	
            	ObjectMetadata metadata = new ObjectMetadata();
            	
            	
            	byte[] output = null;
				try {
					output = html.getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
            	metadata.setContentLength(output.length);
            	metadata.setContentType("text/html");
            	// create empty content
            	InputStream stream = new ByteArrayInputStream(output);
            	// create a PutObjectRequest passing the folder name suffixed by /
            	PutObjectRequest putObjectRequest = new PutObjectRequest("published-products",outputFile,stream, metadata).withCannedAcl(CannedAccessControlList.PublicRead);
            	// send request to S3 to create folder
            	s3Client.putObject(putObjectRequest);

            	logger.log("wrote html file in s3: " + outputFile);
            }
        }
        return "ok";
    }

    protected Document getXmlDocument(S3EventNotification.S3EventNotificationRecord s3Notification){
        String bucket = s3Notification.getS3().getBucket().getName();
        String key = s3Notification.getS3().getObject().getKey();

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);

        S3Object xmlObject = s3Client.getObject(getObjectRequest);

        try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlObject.getObjectContent());
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
    }

}
