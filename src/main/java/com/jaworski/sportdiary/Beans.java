package com.jaworski.sportdiary;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class Beans {

    @Bean
    public final RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

}
