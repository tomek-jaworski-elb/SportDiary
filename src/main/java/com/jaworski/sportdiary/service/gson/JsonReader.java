package com.jaworski.sportdiary.service.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaworski.sportdiary.domain.Activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonReader {

    public List<Activity> get(String str) {
        if ((str==null) || (str.isBlank())) {
            return new ArrayList<>();
        }
        Gson gson = GsonCreator.getGson();
        Type typeToken = new TypeToken<List<Activity>>() {
        }.getType();
        return gson.fromJson(str, typeToken);

    }
}
