package com.shpp.producer;

import javax.jms.*;

import com.shpp.Connector;
import com.shpp.LoadingProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private LoadingProperties properties = new LoadingProperties();
    private String queue = properties.getProperty("queue");
    public MessageProducer messageProducer;
    public TextMessage message;
    public Connection connection;
    public Session session;
    public ActiveMQConnectionFactory factory;
    private Destination destination;

    public Producer() {
        try {
            factory = new Connector().connectToQueue();
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(this.queue);
            messageProducer = session.createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            message = session.createTextMessage();
            LOGGER.info("Producer start working");
        } catch (JMSException e) {
            LOGGER.error("Error of Producer", e);
        }
    }

    public void sendMessage(String msg) {
        try {
            message.setText(msg);
            messageProducer.send(message);
        } catch (JMSException e) {
            LOGGER.error("Error sending message", e);
        }
    }

    public boolean closeConection() {
        try {
            messageProducer.close();
            session.close();
            connection.close();
            LOGGER.info("Closed producer connection");
            return true;
        } catch (JMSException e) {
            LOGGER.error("Error closing producer connection", e);
            return false;
        }
    }
}
