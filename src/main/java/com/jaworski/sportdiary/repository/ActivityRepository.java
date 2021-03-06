package com.jaworski.sportdiary.repository;

import com.jaworski.sportdiary.domain.Activity;
import com.jaworski.sportdiary.entity.ActivityEntity;
import com.jaworski.sportdiary.mapper.ActivityMapper;
import com.jaworski.sportdiary.service.gson.GsonCreator;
import com.jaworski.sportdiary.service.gson.JsonReader;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ActivityRepository {
    private static final Logger logger = LogManager.getLogger(ActivityRepository.class);

    private static final Path PATH_TO_DB_FILE = Path.of("src", "main", "resources", "db.json");
    private final List<Activity> activities = getActivities();

    private final ActivityMapper activityMapper;


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
            logger.error(path.toString(), e);
            return "";
        }
    }

    public boolean saveToJson() {
        String json = getJson();
        try {
            Files.writeString(PATH_TO_DB_FILE, json);
        } catch (IOException e) {
            logger.error(e);
            return false;
        }
        return true;
    }
}
