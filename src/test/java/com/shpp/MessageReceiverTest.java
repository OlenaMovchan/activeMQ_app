package com.shpp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shpp.consumer.MessageReceiver;
import com.shpp.messages.MessageClass;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageReceiverTest {
    private MessageReceiver messageReceiver;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private MessageClass messageClass;
    @Mock
    private Set<ConstraintViolation<MessageClass>> errors;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        messageReceiver = new MessageReceiver();
        objectMapper = new ObjectMapper();
    }
}

//    @Test
//    public void testStringIncorrect() throws JsonProcessingException {
//        when(errors.stream()).thenReturn(errors.stream());
//        when(errors.map(any())).thenReturn(errors);
//        when(errors.reduce(any())).thenReturn("Error1, Error2");
//        when(objectMapper.writeValueAsString(any())).thenReturn("{\"errors\":[\"Error1, Error2\"]");
//
//        String result = messageReceiver.stringIncorrect(messageClass, errors);
//
//        assertEquals("Name, 0, {\"errors\":[\"Error1, Error2\"]}\n", result);
//    }
//
//    @Test
//    public void testStringIncorrectWithJsonProcessingException() throws JsonProcessingException {
//        when(errors.stream()).thenReturn(errors.stream());
//        when(errors.map(any())).thenReturn(errors);
//        when(errors.reduce(any())).thenReturn("Error1, Error2");
//        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
//
//        String result = messageReceiver.stringIncorrect(messageClass, errors);
//
//        assertEquals("Name, 0, \n", result);
//    }
//}


//DataService dataServiceMock = Mockito.mock(DataService.class);
//instanceof DataService вернёт true, но и dataServiceMock.getClass() — именно DataService.class

//DataService dataServiceSpy = Mockito.spy(DataService.class);
//    // or
//    DataService dataService = new DataService();
//dataServiceSpy = Mockito.spy(dataService);

//List<String> data = new ArrayList<>();
//data.add("dataItem");
//Mockito.when(dataService.getAllData()).thenReturn(data);

//List<String> data = new ArrayList<>();
//data.add("dataItem");
//Mockito.doReturn(data).when(dataService).getData()
    //Простейший вариант, когда я проверяю факт однократного вызова метода
// на протяжении выполнения теста, выглядит так:
//
//
//Mockito.verify(dataService).getDataById(Mockito.any());

//Mockito.verify(dataService, Mockito.times(1))
//       .getDataById(Mockito.any());
//DataSearchRequest request = new DataSearchRequest("idValue", new Date(System.currentTimeMillis()), 50);
//dataService.getDataByRequest(request);
//
//ArgumentCaptor<DataSearchRequest> requestCaptor = ArgumentCaptor.forClass(DataSearchRequest.class);
//Mockito.verify(dataService, times(1)).getDataByRequest(requestCaptor.capture());
//
//assertThat(requestCaptor.getAllValues()).hasSize(1);
//DataSearchRequest capturedArgument = requestCaptor.getValue();
//assertThat(capturedArgument.getId()).isNotNull();
//assertThat(capturedArgument.getId()).isEqualTo("idValue");
//assertThat(capturedArgument.getUpdatedBefore()).isAfterYear(1970);
//assertThat(capturedArgument.getLength()).isBetween(0, 100);

//При использовании аннотаций доступен другой способ (пожалуй, самый популярный) —
// просто вызывать каждый раз MockitoAnnotations.initMocks(this);.
// Это позволит переинициализировать "начисто" все поля, помеченные аннотациями Mockito.
