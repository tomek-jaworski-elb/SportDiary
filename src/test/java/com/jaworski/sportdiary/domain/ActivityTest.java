package com.jaworski.sportdiary.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getDuration_returnsNull_setNull() {
        Activity activity = new Activity();
        assertThat(activity.getDuration()).isNull();
    }

    @Test
    void getDuration_returnsValue_setSameValue() {
        Activity activity = new Activity();
        activity.setDuration(-5L);
        Long expectedValue = -5L;
        assertThat(activity.getDuration()).isEqualTo(expectedValue);
    }

    @Test
    void getDateTime_returnsValue_setSameValue() {
        Activity activity = new Activity();
        activity.setDateTime(LocalDateTime.of(2022, 1, 1, 10, 10, 10));
        String actualValue = activity.getDateTime().toString();
        assertThat(actualValue).isEqualTo("2022-01-01T10:10:10");
    }
}