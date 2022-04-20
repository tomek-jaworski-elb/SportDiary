package com.jaworski.sportdiary.service;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.domain.Distance;
import com.jaworski.sportdiary.domain.Sport;
import com.jaworski.sportdiary.domain.Units;
import com.jaworski.sportdiary.service.gson.GsonCreator;
import com.jaworski.sportdiary.service.gson.JsonReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

@Component
public class ActivityRepository {

    private List<Activity> activities = getActivities();

    public ActivityRepository() {
//        getActivities();
        //     fillRepository();
    }

    public List<Activity> getRepository() {
        return activities;
//        return getActivities();
    }

    private void fillRepository() {
        Activity activity1 = new Activity(LocalDateTime.of(2021, 1, 12, 12, 00), Sport.CYCLING, Duration.of(1, HOURS), new Distance(20d, Units.KM));
        Activity activity2 = new Activity(LocalDateTime.of(2021, 2, 12, 14, 00), Sport.RUNNING, Duration.of(1, HOURS), new Distance(12., Units.KM));
        Activity activity3 = new Activity(LocalDateTime.of(2021, 3, 12, 12, 15), Sport.ULTRA_RUNNING, Duration.of(1, HOURS), new Distance(10d, Units.KM));
        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    private String getJson() {
        return GsonCreator.getGson().toJson(activities);
    }

    public List<Activity> getActivities() {
        return new JsonReader().get(getFromFile());
    }

    private String getFromFile() {
        Path path = Path.of("src", "main", "resources", "db.json");
        try {
            return Files.readString(path);
        } catch (IOException e) {
            return "";
        }
    }
}
