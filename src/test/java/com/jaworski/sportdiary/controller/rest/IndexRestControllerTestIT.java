package com.jaworski.sportdiary.controller.rest;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.rest.IndexRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.Base64;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {IndexRestController.class})
class IndexRestControllerTestIT {

    private static final String BASE_URL = "http://localhost:8080/api";
    @MockBean()
    private IndexRestController indexRestController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenContextLoads_thenServiceIsInjected() {
        Assertions.assertNotNull(indexRestController);
    }

    @Test
    void getAllActivity() throws Exception {
        given(indexRestController.getAllActivities(any())).willReturn(ResponseEntity.ok(new ArrayList<>()));

        mockMvc.perform(get(BASE_URL + "/activities").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().is4xxClientError());
    }

    @Test
    void getAllActivityByUser() throws Exception {
        // TODO: implement test
        Base64.Encoder encoder = Base64.getEncoder();
        String encoded = encoder.encodeToString("admin:admin".getBytes());

        given(indexRestController.getAllActivities(any())).willReturn(ResponseEntity.ok(new ArrayList<Activity>()));
        RequestBuilder requestBuilder = get(BASE_URL + "/activities/").accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + encoded);


        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(status().is2xxSuccessful());
    }
}