package com.jaworski.sportdiary.domain.enums;

public enum Sport {
    RUNNING("Running"),
    ULTRA_RUNNING("Ultra running"),
    CYCLING("Cycling"),
    GYM("Gym"),
    JOGA("Joga"),
    SWIMMING("Swimming");

    private final String name;

    public String getName() {
        return name;
    }

    Sport(String name) {
        this.name = name;
    }
}
