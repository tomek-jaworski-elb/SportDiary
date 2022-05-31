package com.jaworski.sportdiary.entity;

import com.jaworski.sportdiary.domain.enums.Sport;
import com.jaworski.sportdiary.domain.enums.Unit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "activities")
@ToString
public class ActivityEntity {

    @Min(value = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "{valid.date.empty}")
    private LocalDateTime dateTime;

//    @Enumerated(EnumType.STRING)
//    @NotNull(message = "{valid.sport.empty}")
//    private Sport sport;

    @NotNull(message = "{valid.duration.empty}")
    @Positive(message = "{valid.duration.positive}")
    @NumberFormat()
    private Long duration;

    @NotNull
    @Min(value = 0)
    private Double distanceOf;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private String user;

    @Enumerated(EnumType.STRING)
    private Sport sport;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "unit_of")
//    @NotNull(message = "{valid.unit.empty}")
//    private Unit unitOf;
}
