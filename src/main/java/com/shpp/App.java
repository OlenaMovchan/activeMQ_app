package com.shpp;

import com.shpp.consumer.ConsumerTask;
import com.shpp.consumer.MessageReceiver;
import com.shpp.messages.GeneratorMessages;
import com.shpp.producer.Producer;
import com.shpp.producer.ProducerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static final int NUM_PRODUCERS = 1;
    private static final int NUM_CONSUMERS = 1;

    public static void main(String[] args) {
        String n = System.getProperty("n", "100000");
        LOGGER.info("Number of messages: " + n);
        int numOfMessages = Integer.parseInt(n);
        ExecutorService executor = Executors.newFixedThreadPool(NUM_PRODUCERS + NUM_CONSUMERS);
        GeneratorMessages generator = new GeneratorMessages();
        long startProd = System.currentTimeMillis();

        Producer producer = new Producer();
        int maxN = numOfMessages;
        Runnable producerTask = new ProducerTask(producer, maxN, generator);
        executor.execute(producerTask);

        long startConsumer = System.currentTimeMillis();

        MessageReceiver receiver = new MessageReceiver();
        Runnable consumerTask = new ConsumerTask(receiver);
        executor.execute(consumerTask);

        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                LOGGER.error("Error");
            }
        }
        executor.shutdownNow();
        long tP = (System.currentTimeMillis() - startProd) / 1000;
        double rps = numOfMessages / ((System.currentTimeMillis() - startProd) / 1000);

        double rpsCon = numOfMessages / ((System.currentTimeMillis() - startConsumer) / 1000);

        LOGGER.info("timeProducer " + tP);
        LOGGER.info("rps producer NEW >>> = " + rps);

        LOGGER.info("timeConsumer " + (System.currentTimeMillis() - startConsumer) / 1000);
        LOGGER.info("rps Consumer NEW >>> = " + rpsCon);
        LocalDateTime localDateTime = LocalDateTime.now();
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy:MM-dd HH|mm/ss.ms"));
        System.out.println(format);
    }
}
// Instant before = Instant.now();
//        total = IntStream.of(3, 1, 4, 1, 5, 9)
//                .parallel()
//                .map(ParallelDemo::doubleIt)
//                .sum();
//        Instant after = Instant.now();
//        Duration duration = Duration.between(before, after);
//        System.out.println("Total of doubles = " + total);
//        System.out.println("time = " + duration.toMillis() + " ms");
