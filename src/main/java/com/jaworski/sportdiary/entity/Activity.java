package com.jaworski.sportdiary.entity;

import com.jaworski.sportdiary.domain.Distance;
import com.jaworski.sportdiary.domain.Sport;
import com.jaworski.sportdiary.domain.Units;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
public class Activity {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Min(value = 0)
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "{valid.date.empty}")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private Sport sport;

    @Getter()
    @Setter()
    @NotNull(message = "{valid.duration.empty}")
    @Positive(message = "{valid.duration.positive}")
    @NumberFormat()
    private Long duration;

    @NotNull
    @Min(value = 0)
    private Double distanceOf;

    @Enumerated(EnumType.STRING)
    private Units units;
}
