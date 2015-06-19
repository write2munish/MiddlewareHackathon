package com.philips.glueteam.gui;

import org.apache.wicket.protocol.http.ContextParamWebApplicationFactory;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.protocol.http.WicketServlet;
import org.apache.wicket.util.time.Duration;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.naming.Context;

/**
 * Created by mikke on 17-06-2015.
 */
public class Launcher {
    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);
/* Setup server (port, etc.) */
        ServletContextHandler sch = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHolder sh = new ServletHolder(WicketServlet.class);
        sh.setInitParameter(ContextParamWebApplicationFactory.APP_CLASS_PARAM, PhilWicketApplication.class.getName());
        sh.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
/* Define a variable DEV_MODE and set to false
 * if wicket should be used in deployment mode
 */
            sh.setInitParameter("wicket.configuration", "deployment");
        sch.addServlet(sh, "/*");
        server.setHandler(sch);

        server.start();
        server.join();
    }
}
