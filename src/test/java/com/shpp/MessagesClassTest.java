package com.shpp;

import com.shpp.messages.MessageClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MessageClassTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //   Mockito.when(producer.send(pojo));

    }
    LocalDateTime periodEnd = LocalDateTime.now();
    MessageClass pojoMessage=new MessageClass("user", "1234567897890",35,periodEnd);
    MessageClass pojoMessage1=new MessageClass("user","1234567867894",35,periodEnd);
    @Test
    void getCount() {
        assertEquals(pojoMessage.getCount(),35) ;
    }



    @Test
    void getName() {
        assertEquals(pojoMessage.getName(),"user") ;

    }



    @Test
    void getDateTime() {
        assertEquals(pojoMessage.getDateTime(),periodEnd) ;
    }



    @Test
    void testToString() {

       // assertEquals(pojoMessage.toString(), new StringBuilder().append("pojo{").append("name: ").append('"').append("user").append('"').append(", count: ").append(22).append(", created_at: ").append(periodEnd).append('}').toString());
    }
}
