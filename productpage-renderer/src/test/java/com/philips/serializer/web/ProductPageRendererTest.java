package com.philips.serializer.web;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import com.sun.org.apache.xpath.internal.XPathAPI;

public class ProductPageRendererTest {

	private ProductPageRenderer productPageRenderer;
	
	@Before
	public void setUp ( ) {
		this.productPageRenderer = new ProductPageRenderer();
	}
	
	@Test
	public void testRendering ( ) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("example.xml"));
		String result = productPageRenderer.getTemplatedPage("test.vm", XPathAPI.selectNodeList(doc.getDocumentElement(), "Product").item(0));
		BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("test.expected.txt")));
		StringBuffer expected = new StringBuffer();
		boolean first = true;
		String line = br.readLine();
		while ( line != null ) { 
			if ( !first ) {
				expected.append("\n");
			}
			first = false;
			expected.append(line);
			line = br.readLine();
		}
		assertEquals(expected.toString(), result);
	}

	@Test
	public void testRenderingHTML ( ) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("example.xml"));
		String result = productPageRenderer.getProductPage(XPathAPI.selectNodeList(doc.getDocumentElement(), "Product").item(0));
		System.out.println(result);
	}
	

	@Test
	public void testRenderingHTMLNoFeatures ( ) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("example-without-features.xml"));
		String result = productPageRenderer.getProductPage(XPathAPI.selectNodeList(doc.getDocumentElement(), "Product").item(0));
		System.out.println(result);
	}
	
}
