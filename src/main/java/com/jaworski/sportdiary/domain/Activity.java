package com.jaworski.sportdiary.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Activity {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Min(value = 0)
    @NotNull
    private Integer id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "{valid.date.empty}")
    private LocalDateTime dateTime;
    private Sport sport;

    @Getter()
    @Setter()
    @NotNull(message = "{valid.duration.empty}")
    @Positive(message = "{valid.duration.positive}")
    @NumberFormat()
    private Long duration;

    @Valid
    private Distance distance;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
