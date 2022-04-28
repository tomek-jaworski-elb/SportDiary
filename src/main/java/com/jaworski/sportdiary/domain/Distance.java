package com.jaworski.sportdiary.domain;

import lombok.*;

@ToString
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Distance {
    private Double distanceOf;
    private Units units;
    private final static double RATE_KM_TO_MILE = 1.84;

    public Double getDistanceKM() {
        Integer result;
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
