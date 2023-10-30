package com.shpp;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Connector {

    private static LoadingProperties properties = new LoadingProperties();
    private static String connect = properties.getProperty("connect");
    private static String user = properties.getProperty("user");
    private static String password = properties.getProperty("password");

    public static ActiveMQConnectionFactory activeMQConnectionFactory() {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connect);
        connectionFactory.setUserName(user);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }
}
