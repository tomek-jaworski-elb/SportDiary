package com.jaworski.sportdiary.entity;

import com.jaworski.sportdiary.domain.enums.Sport;
import com.jaworski.sportdiary.domain.enums.Unit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = ActivityEntity.TABLE_NAME)
@ToString
public class ActivityEntity {

    public static final String TABLE_NAME = "activity";
    public static final String COLUMN_PREFIX = "a_";

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = COLUMN_PREFIX + "id")
    private UUID id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "{valid.date.empty}")
    @Column(name = COLUMN_PREFIX + "date_time_at")
    private LocalDateTime dateTime;

    @NotNull(message = "{valid.distance.empty}")
    @PastOrPresent(message = "{valid.distance.future}")
    @Column(name = COLUMN_PREFIX + "added_at")
    private LocalDateTime addedAt;

    @NotNull(message = "{valid.distance.empty}")
    @PastOrPresent(message = "{valid.distance.future}")
    @Column(name = COLUMN_PREFIX + "modified_at")
    private LocalDateTime lastModifiedAt;

    @NotNull(message = "{valid.duration.empty}")
    @Positive(message = "{valid.duration.positive}")
    @NumberFormat()
    @Column(name = COLUMN_PREFIX + "duration")
    private Long duration;

    @NotNull
    @Min(value = 0)
    @Column(name = COLUMN_PREFIX + "distance")
    private Double distanceOf;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Unit unit;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = COLUMN_PREFIX + "sport")
    private Sport sport;

    @ManyToOne
    @JoinColumn(name = UserEntity.COLUMN_PREFIX + "id")
    private UserEntity userEntity;
}
