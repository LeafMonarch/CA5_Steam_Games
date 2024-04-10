package com.dkit.oop.sd2;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.*;
import org.junit.Test;

import com.dkit.oop.sd2.DAOs.MySqlGameDao;
import com.dkit.oop.sd2.DTOs.Game;
import com.dkit.oop.sd2.DTOs.GamesNameComparator;
import com.dkit.oop.sd2.Exceptions.DaoException;

//Darragh
// Added a test for each method

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void testDisplayAllGames() throws DaoException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/steam_games", "root", "");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM games");
        ResultSet resultSet = preparedStatement.executeQuery();

        MySqlGameDao dao = new MySqlGameDao();

        List<Game> gamesList = dao.displayAllGames();

        assertEquals(16, gamesList.size()); // I had 15 games in my Games table, change the expected value to match your
        // table.
    }

    @Test
    public void testGetGameByID() throws DaoException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/steam_games", "root", "");
        MySqlGameDao dao = new MySqlGameDao();

        List<Game> gamesList = dao.getGameByID(1);

        assertEquals(1, gamesList.size()); // getGameByID called once so size should equal 1
        Game retrievedGame = gamesList.get(0);
        assertEquals("Cyberpunk 2077", retrievedGame.getName()); // testing if it returns the correct game and not just
        // any random game.
    }

    @Test
    public void testInsertGameAndDeleteByID() throws DaoException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/steam_games", "root", "");
        MySqlGameDao dao = new MySqlGameDao();

        Game game = new Game();

        // Insert a test game into the database
        dao.insertGame(game);

        String sql = "SELECT COUNT(*) FROM games WHERE ID = ?";
        // Check if the game has been added by checking count of games with the ID that
        // matches the newly inserted Game.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, game.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            assertEquals(1, count); // Count should be 1 if it found a game with an ID that matches the newly
            // inserted Game.
            assertEquals("", game.getName()); // name should be "" because the game was initialized using default
            // constructor
        }

        // delete the test game
        dao.deleteByID(game.getID());

        // Check if the game has been deleted by checking count of games with the ID
        // that matches the newly inserted Game.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, game.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            assertEquals(0, count); // Count should be zero if game was successfully deleted.
        }
    }

    @Test
    public void testUpdateExistingGame() throws DaoException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/steam_games", "root", "");
        MySqlGameDao dao = new MySqlGameDao();

        Game game = new Game();

        // Insert a test game into the database
        dao.insertGame(game);

        // Create a new game with updated attributes
        Game updatedGame = new Game();
        updatedGame.setName("Updated Game");

        // Call the method under test to update the game
        dao.updateExistingGame(game.getID(), updatedGame);

        Game retrievedGame;

        String sql = "SELECT * FROM games WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, game.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            retrievedGame = new Game(
                    resultSet.getInt("ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Genre"),
                    resultSet.getDate("ReleaseDate").toLocalDate(),
                    resultSet.getDouble("Rating"),
                    resultSet.getDouble("Price"),
                    resultSet.getBoolean("IsLimited"),
                    resultSet.getInt("StockLevel"));

        }

        // Verify that the game attributes have been updated correctly
        assertEquals(updatedGame.getName(), retrievedGame.getName());
        /*
         * If you want to test elements other than the name you have to set their value
         * eg.UpdatedGame.setGenre()
         * assertEquals(updatedGame.getGenre(), retrievedGame.getGenre());
         * assertEquals(updatedGame.getReleaseDate(), retrievedGame.getReleaseDate());
         * assertEquals(updatedGame.getRating(), retrievedGame.getRating(), 0.01); //
         * Delta is used for double comparison
         * assertEquals(updatedGame.getPrice(), retrievedGame.getPrice(), 0.01);
         * assertEquals(updatedGame.isLimited(), retrievedGame.isLimited());
         * assertEquals(updatedGame.getStockLevel(), retrievedGame.getStockLevel());
         */

    }

    @Test
    public void testFindGamesUsingFilter() throws DaoException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/steam_games", "root", "");
        MySqlGameDao dao = new MySqlGameDao();
        GamesNameComparator nameComparator = new GamesNameComparator();

        List<Game> sortedGames = dao.findGamesUsingFilter(nameComparator);

        // Check if the games are sorted correctly by name
        assertEquals("Among Us", sortedGames.get(0).getName()); // Among Us should be first game
        assertEquals("Apex Legends", sortedGames.get(1).getName());// Apex Legends should be second
        // Could add more assertEquals but I thought two was enough
    }

}