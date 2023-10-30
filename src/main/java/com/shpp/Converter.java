package com.shpp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shpp.messages.MessageClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);
    private static ObjectMapper mapper = new ObjectMapper();

    static  {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    public static String toJson(MessageClass message){
        String msg = null;
        try {
            msg = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            LOGGER.error("Json error of toString",e);
        }
        return msg;
    }

    public static MessageClass toMessageClass(String json) {
        MessageClass message = null;
        try {
            message = mapper.readValue(json, MessageClass.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Json error of toMessage",e);
        }
        return message;
    }

}
