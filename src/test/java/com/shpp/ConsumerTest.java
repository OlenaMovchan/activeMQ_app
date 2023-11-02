package com.shpp;

import com.shpp.consumer.Consumer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.jms.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsumerTest {
    private Consumer consumer;
    @Mock
    private ActiveMQConnectionFactory factory;
    @Mock
    private Connection connection;
    @Mock
    private Session session;
    @Mock
    private MessageConsumer messageConsumer;
    @Mock
    private TextMessage textMessage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        consumer = new Consumer();
        consumer.factory = factory;
        consumer.connection = connection;
        consumer.session = session;
        consumer.messageConsumer = messageConsumer;
    }

    @Test
    public void testReceiveValidMessage() throws JMSException {
        when(messageConsumer.receive()).thenReturn(textMessage);
        when(textMessage.getText()).thenReturn("ValidMessage");

        String receivedMessage = consumer.receiveMessage();

        assertEquals("ValidMessage", receivedMessage);
    }

    @Test
    public void testReceivePoisonPill() throws JMSException {
        when(messageConsumer.receive()).thenReturn(textMessage);
        when(textMessage.getText()).thenReturn("poisonPill");

        String receivedMessage = consumer.receiveMessage();

        assertEquals(null, receivedMessage);
    }

    @Test
    public void testReceiveNonTextMessage() throws JMSException {
        Message nonTextMessage = mock(Message.class);
        when(messageConsumer.receive()).thenReturn(nonTextMessage);

        String receivedMessage = consumer.receiveMessage();

        assertNull(receivedMessage);
    }

    @Test
    public void testCloseConnectionSuccess() {
        assertTrue(consumer.closeConnection());
    }

    @Test
    public void testCloseConnectionFailure() throws JMSException {
        doThrow(new JMSException("Failed to close")).when(connection).close();
        assertFalse(consumer.closeConnection());
    }
}




