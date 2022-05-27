package com.jaworski.sportdiary.config.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleConfig {

    @Scheduled(cron = "*/60 * * * * *")
    @Async
    public void scheduleFixedRateTask() {
        System.out.println(
                "Run method every 1 minute: " +  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
