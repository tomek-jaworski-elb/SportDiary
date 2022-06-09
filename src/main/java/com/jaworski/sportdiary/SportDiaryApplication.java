package com.jaworski.sportdiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
public class SportDiaryApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SportDiaryApplication.class, args);
//        final EntityManager entityManager = EntityManagerProvider.get();
//        EntityManagerProvider.startTransaction(entityManager);
//        EntityManagerProvider.commitTransaction(entityManager);
    }


}
