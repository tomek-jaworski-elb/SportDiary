package com.jaworski.sportdiary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class InitApp implements CommandLineRunner {

    @Value("${spring.application.name}")
    private String activeProfile;


    @Override
    public void run(String... args) throws Exception {
        System.out.println(activeProfile + " has just started");
    }
}
