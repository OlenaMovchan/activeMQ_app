package com.shpp.messages;

import com.shpp.Converter;
import com.shpp.LoadingProperties;
import com.shpp.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class GeneratorMessages {
    private Logger LOGGER = LoggerFactory.getLogger(GeneratorMessages.class);
    private LoadingProperties properties = new LoadingProperties();
    private String poisonPill = properties.getProperty("poisonPill");
    private String limitSeconds = properties.getProperty("limitSeconds");
    private AtomicInteger countSentMessages = new AtomicInteger(0);
    private Random random = new Random();

    public GeneratorMessages() {

    }

    public String generateRandomName(int length) {
            String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }
            return sb.toString();
    }

    public String generateRandomEddr(int length) {
        String numbers = "1234567890";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return sb.toString();
    }

    public LocalDateTime generateRandomDate() {
        return LocalDateTime.now();
    }

    public int generateRandomCount() {
        return random.nextInt(100);
    }

    public void generateMessages(Producer producer, Integer maxN) {
        LOGGER.info("Start of message generation");
        LOGGER.info("Limit seconds " + limitSeconds);
        long startTime = System.currentTimeMillis();

        Stream.generate(() -> new MessageClass(generateRandomName(random.nextInt(13)),
                        generateRandomEddr(13), generateRandomCount(),
                        generateRandomDate()))
                .limit(maxN)
                .takeWhile(p -> System.currentTimeMillis() < (Long.parseLong(limitSeconds) * 1000 + startTime))
                .forEach(message -> {
                    producer.sendMessage(Converter.serialize(message));
                    countSentMessages.getAndIncrement();
                });

        long elapsedTime = System.currentTimeMillis() - startTime;
        producer.sendMessage(poisonPill);
        LOGGER.info("Send poisonPill");
        LOGGER.info("Total messages sent: " + countSentMessages.get());
        double sendingSpeed = countSentMessages.get() / (elapsedTime / 1000.0);
        LOGGER.info("Sending speed: " + sendingSpeed + " messages per second");
        producer.closeConection();

    }
}
// //ForkJoinPool pool = new ForkJoinPool(4);
//        ForkJoinTask<Stream<MessageClass>> task = pool.submit(() -> Stream.generate(() -> new MessageClass(generateRandomName(random.nextInt(13),
//                generateRandomEddr(13), generateRandomCount(),
//                generateRandomDate()))
//                .limit(maxN)
//                .takeWhile(p -> System.currentTimeMillis() < (Long.parseLong(limitSeconds) * 1000 / countProducers + startTime))
//                .parallel() // Add parallel() here to parallelize the stream
//                .forEach(message -> {
//                    producer.sendMessage(Converter.serialize(message));
//                    countSentMessages.getAndIncrement();
//                });
//
//// Wait for the task to complete
//        Stream<MessageClass> generatedMessages = task.join();

//        Stream.generate(() -> new MessageClass(generateRandomName(random.nextInt(13)),
//                        generateRandomEddr(13),
//                        generateRandomCount(),
//                        generateRandomDate()))
//                .limit(maxN).takeWhile(p -> System.currentTimeMillis() < (Long.parseLong(limitSeconds)*1000/countProducers+ startTime))
//                .forEach(message -> {
//                    producer.sendMessage(Converter.serialize(message));
//                    countSentMessages.getAndIncrement();
//                });

//int numberOfThreads = Runtime.getRuntime().availableProcessors(); // Number of available processors
//
//    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
//
//    for (int i = 0; i < numberOfThreads; i++) {
//        executorService.execute(() -> {
//            Stream.generate(() -> new MessageClass(generateRandomName(random.nextInt(13)),
//                    generateRandomEddr(13),
//                    generateRandomCount(),
//                    generateRandomDate()))
//                    .limit(maxN / numberOfThreads) // Split the work among threads
//                    .takeWhile(p -> System.currentTimeMillis() < (Long.parseLong(limitSeconds) * 1000 + startTime))
//                    .forEach(msg -> {
//                        producer.send(Services.toJson(msg));
//                        countSentMessages.getAndIncrement();
//                    });
//        });
//    }
//
//    executorService.shutdown();
//    while (!executorService.isTerminated()) {
//
//    }