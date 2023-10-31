package com.shpp;

import com.shpp.messages.MessageClass;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ConverterTest {
    LocalDateTime now = LocalDateTime.now();

    LocalDateTime specificTime = now
            .withHour(14)
            .withMinute(30)
            .withSecond(45)
            .withNano(0);
    @Test
    void testSerialize() {
        MessageClass message = new MessageClass("TestName", "1234567890123", 42, specificTime);
        System.out.println(specificTime);
        String json = Converter.serialize(message);
        System.out.println(json);
        assertNotNull(json);
    }

    @Test
    void testDeserialize() {
        String json = "{\"name\":\"TestName\",\"eddr\":\"1234567890123\",\"count\":42,\"createdAt\":\"2023-10-31T14:30:45\"}";
        MessageClass message = Converter.deserialize(json);
        assertNotNull(message);
        assertEquals("TestName", message.getName());
        assertEquals("1234567890123", message.getEddr());
        assertEquals(42, message.getCount());
        assertEquals(specificTime, message.getCreatedAt());
    }
}
