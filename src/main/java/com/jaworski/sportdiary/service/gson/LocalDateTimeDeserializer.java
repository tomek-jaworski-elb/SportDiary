package com.jaworski.sportdiary.service.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

    private static final Logger LOGGER = Logger.getLogger(LocalDateTimeDeserializer.class.getName());

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (DateTimeException e) {
            localDateTime = LocalDateTime.of(0, 1, 1, 0, 0);
            LOGGER.log(Level.INFO, "DateTimeFormat Exception", e);
        }
        return localDateTime;
    }
}
