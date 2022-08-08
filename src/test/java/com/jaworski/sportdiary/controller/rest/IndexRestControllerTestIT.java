package com.jaworski.sportdiary.controller.rest;

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
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(RestTemplate.class)
@WebMvcTest
@ContextConfiguration(classes = {IndexRestController.class})
class IndexRestControllerTestIT {

    private static final String BASE_URL = "/api";
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

        mockMvc.perform(get(BASE_URL + "/acts").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().is4xxClientError());
    }
}