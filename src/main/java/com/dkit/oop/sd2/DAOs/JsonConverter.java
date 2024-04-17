package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.List;

public class
JsonConverter {
    private static final Gson gsonParser = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public static String gamesListToJson(List<Game> list) {
        return gsonParser.toJson(list);
    }

    public static String gameToJson(Game game) {
        return gsonParser.toJson(game);
    }
    public static String imagesListToJson(List<String> list) {
        return gsonParser.toJson(list);
    }
}