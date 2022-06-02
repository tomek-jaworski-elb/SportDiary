package com.jaworski.sportdiary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
public class SportDiaryApplication {

    static final Logger LOGGER = LoggerFactory.getLogger(SportDiaryApplication.class);


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SportDiaryApplication.class, args);
//        final EntityManager entityManager = EntityManagerProvider.get();
//        EntityManagerProvider.startTransaction(entityManager);
//        EntityManagerProvider.commitTransaction(entityManager);

    }


}
