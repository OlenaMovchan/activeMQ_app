package com.shpp.consumer;

import javax.jms.*;

import com.shpp.Connector;
import com.shpp.LoadingProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    private LoadingProperties properties = new LoadingProperties();

    private String poisonPill = properties.getProperty("poisonPill");
    private String queue = properties.getProperty("queue");
    private Connection connection;
    private Session session;
    private MessageConsumer messageConsumer;
    private Destination destination;
    private ActiveMQConnectionFactory factory;

    public Consumer() {
        try {
            factory = Connector.activeMQConnectionFactory();
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
        } catch (Exception e) {
            LOGGER.info("Error closing consumer connection");
            return true;
        }
        return false;
    }
}