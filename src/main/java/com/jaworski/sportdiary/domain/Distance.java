package com.jaworski.sportdiary.domain;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ToString
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Distance {

    @NotNull(message = "pole null")
    @NotBlank(message = "pole blank")
    @NotEmpty(message = "empty")
    private Double distanceOf;
    private Units units;
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
