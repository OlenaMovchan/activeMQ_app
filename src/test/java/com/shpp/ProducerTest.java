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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

class ProducerTest {
    private static final Logger logger = LoggerFactory.getLogger(ProducerTest.class);
    @Mock
    private Connection connection;

    @Mock
    private Producer producer;
    @Mock
    private Session session;

    @Mock
    private Destination queue;


    @Mock
    MessageProducer messageProducer;

    @Mock
    private ActiveMQConnectionFactory factory;

    @Mock
    TextMessage message;

    @Mock
    Consumer consumer;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //   Mockito.when(producer.send(pojo));

    }
    @Test
    void testRun() throws Exception {
        String msg="test";
        assertEquals(session.createQueue("queue"), null);
        when(session.createTextMessage("test")).thenReturn((TextMessage) message);
        assertEquals(message,session.createTextMessage("test"));
        assertEquals(message,session.createTextMessage("test"));


    }
    //    @Test
//    void whenAddCalledVerified() {
//        MyList myList = mock(MyList.class);
//        myList.add(0, "");
//
//        verify(myList, times(1)).add(0, "");
//    }
    @Test
    void send() throws Exception {
        String msg="test";

        when(message.getText()).thenReturn(msg);
        assertEquals(msg,message.getText());
        // Producer producer1=mock(Producer.class);
        ActiveMQConnectionFactory factory=mock(ActiveMQConnectionFactory.class);

        Producer producer1=new Producer();
        producer1.send("test");

        //verify(producer1,times(1)).send("test");
    }

    @Test
    void stop() {

        assertFalse(producer.closeConectionProducer());
        when(producer.closeConectionProducer()).thenReturn(true);
        assertTrue(producer.closeConectionProducer());
        logger.info(producer.closeConectionProducer() + "");

    }
}