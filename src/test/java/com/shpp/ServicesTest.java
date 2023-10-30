package com.shpp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shpp.messages.GeneratorMessages;
import com.shpp.messages.MessageClass;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ServicesTest {
    static Properties properties = new Properties();
    private static String connect = properties.getProperty("connect");
    private static String user = properties.getProperty("user");
    private static String password = properties.getProperty("password");
    private static final Logger logger = LoggerFactory.getLogger(ServicesTest.class);
    @Mock
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }
    @Mock
    ActiveMQConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory(connect);
    @Mock
    MessageClass messageClass;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //   Mockito.when(producer.send(pojo));

    }
//    @Test
//    void toJson() throws JsonProcessingException {
//        String msg = "{\"name\":\"test\",\"count\":10,\"createdAt\":\"2023\"}";
//        when(mapper.writeValueAsString(messageClass)).thenReturn(msg);
//        assertEquals(msg,mapper.writeValueAsString(messageClass));
//    }
//
//    @Test
//    void toPojo() throws JsonProcessingException {
//        String json = null;
//        MessageClass pojo = null;
//        when(mapper.readValue(json,MessageClass.class)).thenReturn(pojo);
//        assertEquals(pojo, mapper.readValue(json,MessageClass.class));
//
//    }
//
//    @Test
//    void activeMQConnectionFactory() {
//        ActiveMQConnectionFactory connectionFactory =
//                mock(ActiveMQConnectionFactory.class);
//        connectionFactory.setUserName(user);
//        verify(connectionFactory,times(1)).setUserName("ElenaMovchan");
//        connectionFactory.setPassword(password);
//        verify(connectionFactory,times(1)).setPassword("yevshanzillya");
//    }
}