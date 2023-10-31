package com.shpp.consumer;



import com.shpp.Connector;
import com.shpp.LoadingProperties;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class Consumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    private LoadingProperties properties = new LoadingProperties();

    private String poisonPill = properties.getProperty("poisonPill");
    private String queue = properties.getProperty("queue");
    public Connection connection;
    public Session session;
    public MessageConsumer messageConsumer;
    private Destination destination;
    public ActiveMQConnectionFactory factory;

    public Consumer() {
        try {
            factory = new Connector().connectToQueue();
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queue);
            messageConsumer = session.createConsumer(destination);
            LOGGER.info("Consumer start working");
        } catch (JMSException e) {
            LOGGER.error("Error connect consumer", e);
        }
    }

    public String receiveMessage() {
        try {
            Message message = messageConsumer.receive();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                if (((TextMessage) message).getText().equals(poisonPill)) {
                    LOGGER.info("Received poisonPill");
                    return null;
                }
                return textMessage.getText();
            }
        } catch (JMSException e) {
            LOGGER.error("JMSException", e.getMessage());
        }
        return null;
    }

    public boolean closeConnection() {
        try {
            messageConsumer.close();
            session.close();
            connection.close();
            LOGGER.info("Closed consumer connection");
            return true;
        } catch (Exception e) {
            LOGGER.info("Error closing consumer connection");
            return false;
        }
    }
}