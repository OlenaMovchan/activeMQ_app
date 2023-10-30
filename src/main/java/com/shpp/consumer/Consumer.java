package com.shpp.consumer;
import javax.jms.*;

import com.shpp.Connector;
import com.shpp.LoadingProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {

    private final LoadingProperties properties = new LoadingProperties();
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    private String poisonPill = properties.getProperty("poison");
    private String queue =properties.getProperty("queue");
    private Connection connection;
    private Session session;
    private  MessageConsumer messageConsumer;
    private  Destination destination;
    private  ActiveMQConnectionFactory factory;

    public Consumer() throws Exception {
        try {
            factory = Connector.activeMQConnectionFactory();
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queue);
            messageConsumer = session.createConsumer(destination);
            LOGGER.debug("Consumer was started");
        } catch (JMSException e) {
            LOGGER.error("Error of Consumer", e);
            throw new Exception(e);
        }
    }

    public String receiveMessage() throws Exception {
        Message message = messageConsumer.receive();
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            if (((TextMessage) message).getText().equals(poisonPill)) {
                LOGGER.warn("Received poisonPill");
                return null;
            }
            return textMessage.getText();
        }
        return null;
    }

    public boolean closeConnection() throws Exception {

        try {
            messageConsumer.close();
            session.close();
            connection.close();
            LOGGER.info("Closed consumer connection");
        } catch (Exception e) {
            LOGGER.info("Closed consumer not connection");
            return true;
        }
        return false;
    }

}