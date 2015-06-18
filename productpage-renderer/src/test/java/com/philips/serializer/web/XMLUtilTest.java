package com.philips.serializer.web;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;

import com.sun.org.apache.xpath.internal.XPathAPI;

public class XMLUtilTest {
	
	@Test
	public void testMapping ( ) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("example.xml"));
		XMLUtil util = new XMLUtil(XPathAPI.selectNodeList(doc.getDocumentElement(), "Product").item(0));
			
		assertEquals("AA", util.getCountry());
		assertEquals("en_AA", util.getLocale());
		assertEquals("Ingenia", util.getFamilyName());
		assertEquals("MR system", util.getDescriptorName());
		assertEquals("HC781342", util.getCTN());
		assertEquals("781342", util.getDTN());
		assertEquals("HC781342_781342_en_AA", util.getUniqueProductId());
		List<String> expected = new ArrayList<String>();
		expected.add("dStream: dStream enables you to get information, consistently in the same time by delivering premium image quality with digital clarity and speed.*");
		expected.add("Advanced MR: From routine studies to emerging applications Ingenia's explorative tools and advanced diagnostic solutions increase the imaging capabilities of MR by addressing the major healthcare trends in neurology, oncology and cardiology.");
		assertEquals(expected, util.getFeatures());
	}
	
}
