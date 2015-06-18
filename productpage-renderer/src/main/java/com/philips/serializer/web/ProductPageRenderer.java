package com.philips.serializer.web;

import java.io.StringWriter;

import org.apache.log4j.BasicConfigurator;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.w3c.dom.Node;

public class ProductPageRenderer {
	
	public String getProductPage ( Node node ) {
		return getTemplatedPage ( "product.vm", node );
	}
		
	public String getTemplatedPage ( String template, Node node ) {
		
		BasicConfigurator.configure();
		
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
        ve.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
        		"org.apache.velocity.runtime.log.NullLogSystem" );
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate( template );
        
        VelocityContext context = new VelocityContext();
        context.put("util", new XMLUtil(node));
        context.put("debug", "DeBuG!");
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
		
		return writer.toString();
	}

}
