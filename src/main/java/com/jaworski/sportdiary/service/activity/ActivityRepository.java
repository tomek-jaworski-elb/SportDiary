package com.jaworski.sportdiary.service.activity;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.service.gson.GsonCreator;
import com.jaworski.sportdiary.service.gson.JsonReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Component
public class ActivityRepository {

    private static final Path PATH_TO_DB_FILE = Path.of("src", "main", "resources", "db.json");
    private final List<Activity> activities = getActivities();

    public List<Activity> getRepository() {
        return activities;
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

    public void saveToJson() {
        String json = getJson();
        try {
            Files.writeString(PATH_TO_DB_FILE, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        Iterator<Activity> iterator = getRepository().iterator();
        Activity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (activity.getId() == id) {
                iterator.remove();
            }
        }
    }

    public int maxId() {
        return getRepository().stream()
                .min((a1, a2) -> {
                    return a2.getId() - a1.getId();
                })
                .orElse(new Activity())
                .getId();
    }

    public List<Activity> sort(Comparator<Activity> comparator) {
        return getRepository().stream().sorted(comparator).toList();
    }
}
