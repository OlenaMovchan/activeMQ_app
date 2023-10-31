package com.shpp;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConnectorTest {

    private Connector connector;

    @Before
    public void setUp() {
        connector = new Connector();
    }

    @Test
    public void testConnectToQueue() {
        ActiveMQConnectionFactory factory = connector.connectToQueue();

        assertEquals("Expected user should match", "ElenaMovchan", factory.getUserName());
        assertEquals("Expected password should match", "yevshanzillya", factory.getPassword());
    }
}