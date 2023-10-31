package com.shpp;

import com.shpp.messages.GeneratorMessages;
import com.shpp.producer.Producer;
import com.shpp.producer.ProducerTask;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProducerTaskTest {

    @Test
    public void testRunMethodIsCalled() {
        Producer producer = Mockito.mock(Producer.class);
        GeneratorMessages generator = Mockito.mock(GeneratorMessages.class);
        int maxN = 10;
        ProducerTask producerTask = new ProducerTask(producer, maxN, generator);
        producerTask.run();
        Mockito.verify(generator, Mockito.times(1)).generateMessages(producer, maxN);
    }
}
