package com.pizzashack.client.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PizzaShackContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String serverURL = System.getenv("API_SERVER_URL");
                if (serverURL == null || serverURL.isEmpty()) {
                    serverURL = event.getServletContext().getInitParameter("serverURL");
                }
		System.out.println("Setting server URL to: " + serverURL);
		PizzaShackWebConfiguration.getInstance().setServerURL(serverURL);

		String loginURL = System.getenv("API_LOGIN_URL");
                if (loginURL == null || loginURL.isEmpty()) {
                    loginURL = event.getServletContext().getInitParameter("loginURL");
                }
        //String loginURL = "https://10.150.3.80:9443/_WSO2AMLoginAPI_";
        PizzaShackWebConfiguration.getInstance().setLoginURL(loginURL);

		String consumerKey = System.getenv("API_CONSUMER_KEY");
                if (consumerKey == null || consumerKey.isEmpty()) {
                    consumerKey = event.getServletContext().getInitParameter("consumerKey");
                }
		PizzaShackWebConfiguration.getInstance().setConsumerKey(consumerKey);

		String consumerSecret = System.getenv("API_CONSUMER_SECRET");
                if (consumerSecret == null || consumerSecret.isEmpty()) {
                    consumerSecret = event.getServletContext().getInitParameter("consumerSecret");
                }
		PizzaShackWebConfiguration.getInstance().setConsumerSecret(consumerSecret);
	}

}
