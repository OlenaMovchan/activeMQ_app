package com.shpp;

import com.shpp.consumer.ConsumerTask;
import com.shpp.consumer.MessageReceiver;
import com.shpp.messages.GeneratorMessages;
import com.shpp.producer.Producer;
import com.shpp.producer.ProducerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static final int NUM_PRODUCERS = 2; // Number of producer tasks
    private static final int NUM_CONSUMERS = 2; // Number of consumer tasks

    public static void main(String[] args) throws Exception {
        String n = System.getProperty("n", "100000");
        LOGGER.info("Number of messages: " + n);
        int numOfMessages = Integer.parseInt(n);
        ExecutorService executor = Executors.newFixedThreadPool(NUM_PRODUCERS + NUM_CONSUMERS);
        LOGGER.info(">>>>>");
        LOGGER.info(">>>>>");
        GeneratorMessages generator = new GeneratorMessages(NUM_PRODUCERS);

        long startProd = System.currentTimeMillis();

        for (int i = 0; i < NUM_PRODUCERS; i++) {
            Producer producer = new Producer();
            int maxN = numOfMessages / NUM_PRODUCERS;
            Runnable producerTask = new ProducerTask(producer, maxN, generator);
            executor.execute(producerTask);
        }

        long startConsumer = System.currentTimeMillis();
        LOGGER.info("start cons ");
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            MessageReceiver receiver = new MessageReceiver();
            Runnable consumerTask = new ConsumerTask(receiver);
            executor.execute(consumerTask);
        }


        // Shutdown the executor and wait for all threads to complete
        executor.shutdown();
//        if (!executor.isTerminated()) {
//            executor.awaitTermination(5L, TimeUnit.SECONDS);
//            executor.shutdownNow();
//        }
        while (!executor.isTerminated()) {
            //System.out.println("Thread is still processing.");
            // loop until executor service is terminated
            try {
                // continuous ping on executor service might effect performance
                // so we set main thread to sleep for AWAIT_TIME
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }
        executor.shutdownNow();
        long tP = (System.currentTimeMillis() - startProd) / 1000;
        LOGGER.info("timeProducer " + tP);
        double rps = numOfMessages / ((System.currentTimeMillis() - startProd) / 1000);

        double rpsCon = numOfMessages / ((System.currentTimeMillis() - startConsumer) / 1000);

        LOGGER.info("timeProducer " + tP);
        LOGGER.info("rps producer NEW >>> = " + rps);

        LOGGER.info("timeConsumer " + (System.currentTimeMillis() - startConsumer) / 1000);
        LOGGER.info("rps Consumer NEW >>> = " + rpsCon);

    }
}

//        numOfMessages = Integer.parseInt(n);
//
//        GeneratorMessages messages = new GeneratorMessages();
//        Producer producer = new Producer();
//        Producer producer1 = new Producer();
//        //int numberOfThreads = Runtime.getRuntime().availableProcessors();
//        //ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
//long start = System.currentTimeMillis();
//        messages.generateMessages(producer, numOfMessages/2);
//        messages.generateMessages(producer1, numOfMessages/2);
//LOGGER.info("time " +(System.currentTimeMillis() - start)/1000);
//double rps = numOfMessages/((System.currentTimeMillis() - start)/1000);
//LOGGER.info("rps = "+ rps);
//        MessageReceiver read = new MessageReceiver();
//
//        read.receivesMessage();


///private static final int NUM_PRODUCERS = 5; // Number of producer tasks
//    private static final int NUM_CONSUMERS = 5; // Number of consumer tasks
//
//    public static void main(String[] args) throws Exception {
//        ExecutorService executor = Executors.newFixedThreadPool(NUM_PRODUCERS + NUM_CONSUMERS);
//
//        GeneratorMessages generator = new GeneratorMessages();
//
//        // Create and start multiple producer tasks
//        for (int i = 0; i < NUM_PRODUCERS; i++) {
//            Producer producer = new Producer();
//            int maxN = Integer.parseInt(System.getProperty("n", "200000")) / NUM_PRODUCERS;
//            Runnable producerTask = new ProducerTask(producer, maxN, generator);
//            executor.execute(producerTask);
//        }
//
//        // Create and start multiple consumer tasks
//        // ...
//
//        // Shutdown the executor and wait for all threads to complete
//        executor.shutdown();
//        while (!executor.isTerminated()) {
//            // Wait for all threads to complete
//        }
//    }