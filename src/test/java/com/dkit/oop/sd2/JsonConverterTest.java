package com.dkit.oop.sd2;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.dkit.oop.sd2.DAOs.JsonConverter;
import com.dkit.oop.sd2.DTOs.Game;

//Darragh
// Created Test for GamesListToJson
// Create Test for GameToJson

public class JsonConverterTest {
    @Test
    public void testGamesListToJson() {
        // Create a list of Game objects
        List<Game> gamesList = new ArrayList<>();
        gamesList.add(new Game(1, "Game1", "Genre1", LocalDate.now(), 4.5, 19.99, false, 100));
        gamesList.add(new Game(2, "Game2", "Genre2", LocalDate.now(), 4.2, 14.99, true, 50));

        // Convert the list to JSON
        String jsonResult = JsonConverter.gamesListToJson(gamesList);

        // Type out the expected JSON string
        String expectedJson = "[{\"id\":1,\"name\":\"Game1\",\"genre\":\"Genre1\",\"releaseDate\":\""
                + LocalDate.now().toString() + "\",\"rating\":4.5,\"price\":19.99,\"isLimited\":false,\"stockLevel\":100}," +
                "{\"id\":2,\"name\":\"Game2\",\"genre\":\"Genre2\",\"releaseDate\":\"" + LocalDate.now().toString()
                + "\",\"rating\":4.2,\"price\":14.99,\"isLimited\":true,\"stockLevel\":50}]";

        // Check that the generated JSON matches the expected JSON
        assertEquals(expectedJson, jsonResult);
    }

    @Test
    public void testGameToJson() {
        // Create a Game object
        Game game = new Game(1, "Game1", "Genre1", LocalDate.now(), 4.5, 19.99, false, 100);

        // Convert the Game object to JSON
        String jsonResult = JsonConverter.gameToJson(game);

        // Type out the expected JSON string
        String expectedJson = "{\"id\":1,\"name\":\"Game1\",\"genre\":\"Genre1\",\"releaseDate\":\""
                + LocalDate.now().toString() + "\",\"rating\":4.5,\"price\":19.99,\"isLimited\":false,\"stockLevel\":100}";

        // Check that the generated JSON matches the expected JSON
        assertEquals(expectedJson, jsonResult);
    }
}