package com.jaworski.sportdiary.domain;

import lombok.Data;
import lombok.Value;

@Value
public class Distance {
    private Double distanceOf;
    private Units units;
}
