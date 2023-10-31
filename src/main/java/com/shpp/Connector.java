package com.shpp;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Connector {

    private LoadingProperties properties;
    private String connect;
    private String user;
    private String password;

    public Connector() {
        properties = new LoadingProperties();
        connect = properties.getProperty("connect");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
    }

    public ActiveMQConnectionFactory connectToQueue() {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connect);
        connectionFactory.setUserName(user);
        connectionFactory.setPassword(password);

        return connectionFactory;
    }
}
