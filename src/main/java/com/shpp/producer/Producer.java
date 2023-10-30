package com.shpp.producer;

import javax.jms.*;

import com.shpp.Connector;
import com.shpp.LoadingProperties;
import com.shpp.Connector;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private LoadingProperties properties = new LoadingProperties();
    private String queue = properties.getProperty("queue");
    private MessageProducer messageProducer;
    private TextMessage message;
    private Connection connection;
    private Session session;

    public Producer() throws Exception {
        try {
            ActiveMQConnectionFactory factory = Connector.activeMQConnectionFactory();
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(this.queue);

            messageProducer = session.createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            message = session.createTextMessage();
            LOGGER.debug("Producer was started");
        } catch (JMSException e) {
            LOGGER.error("Error of Producer", e);
            throw new Exception(e);
        }
        LOGGER.info("Producer was started");
    }

    public void send(String msg) {
        try {
            message.setText(msg);
            messageProducer.send(message);
        } catch (JMSException e) {
            LOGGER.error("Message don't send", e);
        }
    }

    public boolean closeConectionProducer() {
        try {
            session.close();
            connection.close();
            LOGGER.info("close connection producer");
        } catch (JMSException e) {
            LOGGER.error("error producer  stopped", e);
            return true;
        }
        return false;
    }
}
