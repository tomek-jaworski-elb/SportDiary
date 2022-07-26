package com.jaworski.sportdiary.domain;

import com.jaworski.sportdiary.domain.enums.Sport;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Activity {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "{valid.date.empty}")
    private LocalDateTime dateTime;
    private Sport sport;

    @NotNull(message = "{valid.duration.empty}")
    @Positive(message = "{valid.duration.positive}")
    @NumberFormat()
    private Long duration;

    @Valid
    private Distance distance;

    private User user;

    private LocalDateTime lastModifiedAt;
    private LocalDateTime addedAt;

    private boolean isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
