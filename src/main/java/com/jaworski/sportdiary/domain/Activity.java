package com.jaworski.sportdiary.domain;

import lombok.Value;

import java.time.Duration;
import java.time.LocalDateTime;

@Value
public class Activity {

    private LocalDateTime dateTime;
    private Sport sport;
    private Duration duration;
    private Distance distance;
}
