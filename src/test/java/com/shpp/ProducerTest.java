package com.shpp;

import com.shpp.producer.Producer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.jms.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProducerTest {
    private Producer producer;
    @Mock
    private ActiveMQConnectionFactory factory;
    @Mock
    private Connection connection;
    @Mock
    private Session session;
    @Mock
    private MessageProducer messageProducer;
    @Mock
    private TextMessage textMessage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        producer = new Producer();

        producer.factory = factory;
        producer.connection = connection;
        producer.session = session;
        producer.messageProducer = messageProducer;
        producer.message = textMessage;
    }

    @Test
    public void testSendMessageSuccess() throws JMSException {
        String messageText = "TestMessage";
        doNothing().when(messageProducer).send(any(TextMessage.class));
        when(textMessage.getText()).thenReturn(messageText);

        producer.sendMessage(messageText);

        verify(textMessage, times(1)).setText(messageText);
        verify(messageProducer, times(1)).send(textMessage);
    }

    @Test
    public void testSendMessageFailure() throws JMSException {
        String messageText = "TestMessage";
        doThrow(new JMSException("Failed to send")).when(messageProducer).send(any(TextMessage.class));
        when(textMessage.getText()).thenReturn(messageText);

        producer.sendMessage(messageText);

        verify(textMessage, times(1)).setText(messageText);
        verify(messageProducer, times(1)).send(textMessage);
    }

    @Test
    void testCloseConnectionSuccess() {
        assertTrue(producer.closeConection());
    }

    @Test
    void testCloseConnectionFailure() throws JMSException {
        doThrow(new JMSException("Failed to close")).when(connection).close();
        assertFalse(producer.closeConection());
    }
}
