package com.jaworski.sportdiary.controller.rest;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.service.activity.ActivityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(RestTemplate.class)
@WebMvcTest
class IndexRestControllerTest {

    private static final String BASE_URL = "/proxy/learners";
    @MockBean
    private IndexRestController indexRestController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllActivity() {
        given(indexRestController.getAllActivities()).willReturn(ResponseEntity.ok(new ArrayList<Activity>()));
    }
}