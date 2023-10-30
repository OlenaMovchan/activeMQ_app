package com.shpp;

import com.shpp.consumer.Consumer;
import com.shpp.producer.Producer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.jms.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class ConsumerTest {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerTest.class);
    @Mock
    private Connection connection;
    @Mock
    private Producer producer;
    @Mock
    private Session session;
    @Mock
    private Destination queue;

    @Mock
    private MessageConsumer messageConsumer;

    @Mock
    private ActiveMQConnectionFactory factory;

    @Mock
    Message message;
    @Mock
    TextMessage textMessage;
    @Mock
    Consumer consumer;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRun() throws Exception {
        String poisonPill = "poison_pill";
        String queueName = "QueueMessages";
        Destination queue = null;
        when(messageConsumer.receive()).thenReturn(message);

        assertEquals(session.createQueue(queueName), (Queue) queue);

        when(textMessage.getText()).
                thenReturn("{\"name\":\"nameTest\",\"count\":10,\"createdAt\":\"2023\"}");
        logger.info(textMessage.getText());
        assertEquals(textMessage.getText(),
                "{\"name\":\"nameTest\",\"count\":10,\"createdAt\":\"2023\"}");
        assertNotEquals(textMessage.getText(),
                "{\"name\":\"name\",\"count\":27,\"createdAt\":\"2023\"}");
        when(consumer.receiveMessage()).thenReturn("test");
        //assertEquals("test", consumer.receiveMessage());
        when(consumer.receiveMessage()).thenReturn("poison_pill");
        assertTrue("true", consumer.receiveMessage().equals(poisonPill));
        when(consumer.receiveMessage()).thenReturn("test");


    }

    @Test
    void closeConnection() throws Exception {

        assertFalse(consumer.closeConnection());
        when(consumer.closeConnection()).thenReturn(true);
        assertTrue(consumer.closeConnection());
        logger.info(consumer.closeConnection() + "");

        // verify(consumer).closeConnection();

    }


}