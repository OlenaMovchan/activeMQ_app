package com.shpp.producer;

import com.shpp.messages.GeneratorMessages;

public class ProducerTask implements Runnable{
    private final Producer producer;
    private final int maxN;
    private final GeneratorMessages generator;

    public ProducerTask(Producer producer, int maxN, GeneratorMessages generator) {
        this.producer = producer;
        this.maxN = maxN;
        this.generator = generator;
    }

    @Override
    public void run() {
        generator.generateMessages(producer, maxN);
    }
}
