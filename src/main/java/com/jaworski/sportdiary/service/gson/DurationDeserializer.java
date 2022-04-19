package com.jaworski.sportdiary.service.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.Duration;

public class DurationDeserializer implements JsonDeserializer<Duration> {
    @Override
    public Duration deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Duration durationInSeconds;
        try {
            durationInSeconds = Duration.ofSeconds(jsonElement.getAsJsonPrimitive().getAsInt());
        } catch (Exception e) {
            durationInSeconds = Duration.ZERO;
        }
        return durationInSeconds;
    }
}
