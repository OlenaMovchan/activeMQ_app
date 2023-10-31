package com.shpp;

import com.shpp.messages.MessageClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageClassTest {

    LocalDateTime localDateTime = LocalDateTime.now();
    MessageClass messageClass = new MessageClass("user", "1234567897890", 100, localDateTime);

    @Test
    public void getName() {
        assertEquals(messageClass.getName(), "user");
    }

    @Test
    public void getEddr() {
        assertEquals(messageClass.getEddr(), "1234567897890");
    }

    @Test
    public void getCount() {
        assertEquals(messageClass.getCount(), 100);
    }

    @Test
    public void getDateTime() {
        assertEquals(messageClass.getCreatedAt(), localDateTime);
    }

}
