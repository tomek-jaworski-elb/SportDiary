package com.jaworski.sportdiary.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Activity {

    private Integer id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;
    private Sport sport;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Duration duration;
    private Distance distance;

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = Duration.ofSeconds(duration);
    }
}
