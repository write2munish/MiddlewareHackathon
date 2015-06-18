package com.philips.serializer.web;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xpath.internal.XPathAPI;

public class XMLUtil {

	private Node productNode;
	
	public XMLUtil ( Node productNode ) {
		this.productNode = productNode;
		System.out.println("productnode" + productNode);
	}
	
	public String getCountry ( ) {
		return productNode.getAttributes().getNamedItem("Country").getTextContent();
	}
	
	public String getLocale ( ) {
		return productNode.getAttributes().getNamedItem("Locale").getTextContent();
	}

	public String getFamilyName ( ) {
		try {
			return XPathAPI.selectSingleNode(productNode, "NamingString/Family/FamilyName/text()").getTextContent();
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
	}

	public String getCTN ( ) {
		try {
			return XPathAPI.selectSingleNode(productNode, "CTN/text()").getTextContent();
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
	}

	public String getDTN ( ) {
		try {
			return XPathAPI.selectSingleNode(productNode, "DTN/text()").getTextContent();
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
	}

	public String getDescriptorName ( ) {
		try {
			return XPathAPI.selectSingleNode(productNode, "NamingString/Descriptor/DescriptorName/text()").getTextContent();
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getFeatures() {
		List<String> result = new ArrayList<String>();
		try {
			NodeList nl = XPathAPI.selectNodeList(productNode, "Feature");
			for ( int i = 0; i < nl.getLength(); i++ ) {
				String s1 = XPathAPI.selectSingleNode(nl.item(i), "FeatureLongDescription/text()").getTextContent();
				String s2 = XPathAPI.selectSingleNode(nl.item(i), "FeatureGlossary/text()").getTextContent();
				String feature = s1 + ": " + s2;
				result.add(feature);
			}
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public String getUniqueProductId ( ) {
		return getCTN() + "_" + getDTN() + "_" + getLocale();
	}
	
}
