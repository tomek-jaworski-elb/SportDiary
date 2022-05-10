package com.jaworski.sportdiary.service.gson;

import com.google.gson.Gson;
import com.jaworski.sportdiary.domain.Activity;

import java.util.List;

public class JsonSaver {


    public JsonSaver(List<Activity> activities) {
        String json;
        Gson gson = GsonCreator.getGson();
        json = gson.toJson(activities);
        System.out.println(json);
        new JsonReader().get(json);
    }
}
