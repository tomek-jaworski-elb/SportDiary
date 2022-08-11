package com.jaworski.sportdiary.controller.rest;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.rest.IndexRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {IndexRestController.class})
//@WebMvcTest(excludeAutoConfiguration = {SecurityAutoConfiguration.class}, value = IndexRestController.class)
class IndexRestControllerTestIT {

    private static final String BASE_URL = "/api";
    @MockBean()
    private IndexRestController indexRestController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenContextLoads_thenServiceIsInjected() {
        assertThat(indexRestController).isNotNull();
    }

    @Test
    void getAllActivity_returns401Status_givenRequest() throws Exception {
        given(indexRestController.getAllActivities(any())).willReturn(ResponseEntity.ok(new ArrayList<>()));

        mockMvc.perform(get(BASE_URL + "/activities").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().is2xxSuccessful());
    }

    @Test
    void basicAuth() throws Exception {
        Base64.Encoder encoder = Base64.getEncoder();
        String encoded = encoder.encodeToString("admin:admin".getBytes());
        MockHttpServletRequest request = mock(MockHttpServletRequest.class);
        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity());
        given(indexRestController.getAllActivities(any())).willReturn(ResponseEntity.ok(activities));

        mockMvc.perform(get(BASE_URL + "/activities/").header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("admin:amin".getBytes())))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}