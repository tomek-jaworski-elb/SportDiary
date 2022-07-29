package com.jaworski.sportdiary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class InitApp implements CommandLineRunner {
    private static final Logger LOGGER = LogManager.getLogger(InitApp.class);
    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("{} has started!", applicationName);
    }
}
