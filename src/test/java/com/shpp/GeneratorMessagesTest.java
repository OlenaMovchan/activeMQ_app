package com.shpp;

import com.shpp.messages.GeneratorMessages;
import com.shpp.messages.MessageClass;
import com.shpp.producer.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GeneratorMessagesTest {

    LocalDateTime now = LocalDateTime.now();

    LocalDateTime specificTime = now
            .withHour(14)
            .withMinute(30)
            .withSecond(45)
            .withNano(0);
    MessageClass messageClass = new MessageClass();

    GeneratorMessages generatorMessages = new GeneratorMessages(2);

    @Test
    public void generateRandomNameTest() {
        MessageClass messageClass = new MessageClass();
        messageClass.setName("Www");
        GeneratorMessages generatorMessages = new GeneratorMessages(2);
        assertNotEquals(messageClass.getName(), generatorMessages.generateRandomName(7));
    }

    @Test
    public void generateRandomDateTest() {
        messageClass.setCreatedAt(specificTime);
        assertNotEquals(generatorMessages.generateRandomDate(), messageClass.getCreatedAt());
    }

    @Test
    public void generateRandomCountTest() {
        messageClass.setCount(10);
        assertNotEquals(generatorMessages.generateRandomCount(), messageClass.getCount());
    }

    @Test
    public void sendMessageTest() {
        Producer producer = mock(Producer.class);
        producer.sendMessage("test");
        verify(producer, times(1)).sendMessage("test");
    }

//    @Test
//    void testGenerateMessages() {
//        Producer producer = mock(Producer.class);
//        GeneratorMessages generator = new GeneratorMessages(2);
//        //when(generator.getCurrentTimeMillis()).thenReturn(0L);
//        generator.generateMessages(producer, 10);
//        verify(producer, times(10)).sendMessage(anyString());
//    }
}
