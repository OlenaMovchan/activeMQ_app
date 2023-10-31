package com.shpp.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.shpp.Converter;
import com.shpp.LoadingProperties;
import com.shpp.messages.MessageClass;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);
    private LoadingProperties properties = new LoadingProperties();
    private String poisonPill = properties.getProperty("poisonPill");
    private AtomicInteger countReceiveMessages = new AtomicInteger(0);
    private String validFile = "valid.csv";
    private String incorrectFile = "incorrect.csv";

    public void receivesMessage() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MessageClass>> errors;

        try (FileWriter valid = new FileWriter(validFile);
             FileWriter incorrect = new FileWriter(incorrectFile)) {
        MessageClass message;
        Consumer consumer = new Consumer();
        long startTime = System.currentTimeMillis();

        while (true) {
            String messageReceived = consumer.receiveMessage();
            if (messageReceived == null || messageReceived.equals(poisonPill)) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                double receivingngSpeed = countReceiveMessages.get() / (elapsedTime / 1000.0);
                LOGGER.info("Total messages received   " + countReceiveMessages.get());
                LOGGER.info("Receive speed: " + receivingngSpeed + " messages per second");
                consumer.closeConnection();
                break;
            }
            message = Converter.deserialize(messageReceived);
            countReceiveMessages.incrementAndGet();
            errors = validator.validate(message);
                if (errors.isEmpty()) {
                    valid.write(message.getName() + "," + message.getCount() + "\n");
                    valid.flush();
                } else {
                    incorrect.write(stringIncorrect(message, errors));
                    incorrect.flush();
                }
            }
        }catch (IOException e) {
            LOGGER.error("Error write", e.getMessage());
            LOGGER.info("");
        }
    }

    public String stringIncorrect(MessageClass messageClass, Set<ConstraintViolation<MessageClass>> errors) {
        ObjectMapper mapper = new JsonMapper();
        String error = "";
        try {
            error = mapper.writeValueAsString("{errors:["
                    + errors.stream().map(e -> e.getMessage())
                    .reduce((field1, field2) -> field1 + ", " + field2).orElse("") + "]}");
        } catch (JsonProcessingException e) {
            LOGGER.warn("error", e);
        }
        return messageClass.getName() + "," + messageClass.getCount() + "," + error + "\n";
    }
}
