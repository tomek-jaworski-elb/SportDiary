package com.jaworski.sportdiary.domain;

import com.jaworski.sportdiary.domain.enums.Unit;
import lombok.*;

import javax.validation.constraints.*;

@ToString
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Distance {

    @NotNull(message = "{valid.distance.empty}")
    @PositiveOrZero(message = "{valid.distance.positive}")
    private Double distanceOf;
    private Unit units;
    private static final double RATE_KM_TO_MILE = 1.84;

    public Double getDistanceKM() {
        switch (this.units) {
            case KM -> {
                return distanceOf;
            }
            case NM, MILE -> {
                return distanceOf / RATE_KM_TO_MILE;
            }
            case METERS -> {
                return distanceOf / 1_000;
            }
            default -> {
                return 1d;
            }
        }
    }
}
