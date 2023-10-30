package com.shpp;

import com.shpp.messages.GeneratorMessages;
import com.shpp.messages.MessageClass;
import com.shpp.producer.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GeneratorMessagesTest {
    @Mock
    Producer producer;
    @Mock
    MessageClass pojo;
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        //   Mockito.when(producer.send(pojo));

    }
    private static final Logger logger =
            LoggerFactory.getLogger(GeneratorMessagesTest.class);
    LocalDateTime periodEnd = LocalDateTime.now();
    MessageClass messageClass = new MessageClass();

    GeneratorMessages generatorMessages = new GeneratorMessages();

    @Test
    void randomName() {
        messageClass.setName("Www");
        assertNotEquals(messageClass.getName(), generatorMessages.generateRandomName(7));

    }

    @Test
    void randomDate() {
        messageClass.setDateTime(LocalDateTime.now());
        assertNotEquals(generatorMessages.generateRandomCount(), messageClass.getDateTime());

    }

    @Test
    void randomCount() {
        assertNotEquals(messageClass.getCount(), 21);
        assertNotEquals(generatorMessages.generateRandomCount(), messageClass.getCount());

    }

    @Test
    void generateMessages() {
        //String msg = null;
        MessageClass msg = null;
        Producer producer1=mock(Producer.class);
        producer1.send("hello");
        verify(producer1,times(1)).send("hello");
    }
}