package com.shpp;

import com.shpp.consumer.ConsumerTask;
import com.shpp.consumer.MessageReceiver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ConsumerTaskTest {

    @Test
    public void testRunMethodIsCalled() {

        MessageReceiver messageReceiver = Mockito.mock(MessageReceiver.class);
        ConsumerTask consumerTask = new ConsumerTask(messageReceiver);
        consumerTask.run();
        Mockito.verify(messageReceiver, Mockito.times(1)).receivesMessage();
    }
}
