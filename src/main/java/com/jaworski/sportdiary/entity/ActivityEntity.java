package com.jaworski.sportdiary.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jaworski.sportdiary.domain.enums.Sport;
import com.jaworski.sportdiary.domain.enums.Unit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@JsonIgnoreProperties(value = {"password"}, allowGetters = true)
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
    @NotNull
    private Unit unit;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Sport sport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private UserEntity userEntity;

}
