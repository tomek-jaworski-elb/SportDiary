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
}
