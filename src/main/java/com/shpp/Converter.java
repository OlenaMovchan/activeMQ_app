package com.shpp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shpp.messages.MessageClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);
    private static ObjectMapper mapper = new JsonMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new JavaTimeModule());

    public static String serialize(MessageClass message){
        String serializedMessage = null;
        try {
            serializedMessage = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            LOGGER.error("Serialization error", e);
        }
        return serializedMessage;
    }

    public static MessageClass deserialize(String serializedMessage) {
        MessageClass message = null;
        try {
            message = mapper.readValue(serializedMessage, MessageClass.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Deserialization error", e.getMessage());
        }
        return message;
    }
}
