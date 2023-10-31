package com.shpp.consumer;

public class ConsumerTask implements Runnable {
    private MessageReceiver messageReceiver;

    public ConsumerTask(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    @Override
    public void run() {
        messageReceiver.receivesMessage();
    }
}
