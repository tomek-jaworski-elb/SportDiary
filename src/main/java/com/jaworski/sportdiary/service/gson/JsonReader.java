package com.jaworski.sportdiary.service.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaworski.sportdiary.domain.Activity;

import java.lang.reflect.Type;
import java.util.List;

public class JsonReader {

    public List<Activity> get(String str) {
        Gson gson = GsonCreator.getGson();
        Type typeToken = new TypeToken<List<Activity>>() {
        }.getType();
        return gson.fromJson(str, typeToken);

    }
}
