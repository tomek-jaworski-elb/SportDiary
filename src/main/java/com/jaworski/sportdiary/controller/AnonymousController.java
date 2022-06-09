package com.jaworski.sportdiary.controller;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/anonymous")
@RequiredArgsConstructor
public class AnonymousController {

    private static final Logger LOGGER = LogManager.getLogger(AnonymousController.class);

    private final ActivityService activityService;

    @GetMapping("")
    public String main() {
        Comparator<Activity> comparator = (Activity a1, Activity a2) -> {
            return (int) (a1.getDistance().getDistanceOf() - a2.getDistance().getDistanceOf());
        };
        Function<Activity, String> function = Activity::toString;
        return activityService.sort(comparator).stream().map(function).collect(Collectors.joining());
    }
}
