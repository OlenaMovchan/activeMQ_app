package com.shpp.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shpp.Converter;
import com.shpp.LoadingProperties;
import com.shpp.Converter;
import com.shpp.messages.MessageClass;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageReceiver{

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);
    private LoadingProperties properties = new LoadingProperties();
    private String poisonPill = properties.getProperty("poison");
    private AtomicInteger countReceiveMessages = new AtomicInteger(0);
    private String validFile = "valid.csv";
    private String incorrectFile = "incorrect.csv";

    public void receivesMessage() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MessageClass>> errors;

        try (FileWriter fileWriter = new FileWriter(validFile);
             FileWriter writerError = new FileWriter(incorrectFile)) {
            MessageClass message;
            Consumer consumer = new Consumer();

            long startTime = System.currentTimeMillis();
            while (true) {
                try {
                    String consumerM = consumer.receiveMessage();
                    if (consumerM == null || consumerM.equals(poisonPill)) {
                        long elapsedTime = System.currentTimeMillis() - startTime;
                        double receivingngSpeed = countReceiveMessages.get() / (elapsedTime / 1000.0);
                        LOGGER.info("Total messages received   " + countReceiveMessages.get());
                        LOGGER.info("Receive speed: " + receivingngSpeed + " messages per second");
                        consumer.closeConnection();
                        break;
                    }
                    message = Converter.toMessageClass(consumerM);
                    countReceiveMessages.incrementAndGet();
                    errors = validator.validate(message);
                } catch (IllegalArgumentException e) {
                    consumer.closeConnection();
                    break;
                }
                if (errors.isEmpty()) fileWriter.append(stringCSVValid(message));
                else writerError.append(stringCSVIncorrect(message, errors));
                fileWriter.flush();
                writerError.flush();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String stringCSVValid(MessageClass messageClass) {
        return messageClass.getName() + "," + messageClass.getCount() + "\n";
    }

    public static String stringCSVIncorrect(MessageClass messageClass, Set<ConstraintViolation<MessageClass>> errors) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString("{errors:["
                    + errors.stream().map(e -> e.getPropertyPath().toString() + ":" + e.getMessage())
                    .reduce((a, b) -> a + "," + b).orElse("") + "]}");
        } catch (JsonProcessingException e) {
            LOGGER.warn("Don't write errors", e);
        }
        return messageClass.getName() + "," + messageClass.getCount() + ", " + json + "\n";
    }


}
