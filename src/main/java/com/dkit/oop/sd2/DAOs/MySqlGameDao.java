package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Game;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MySqlGameDao extends MySqlDao implements GameDaoInterface
{
    /**
     * Will access and return a List of all users in User database table
     * @return List of User objects
     * @throws DaoException
     */
    //Raphael
    @Override
    public List<Game> displayAllGames() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Game> gamesList = new ArrayList<>();

        try {
            connection = this.getConnection();

            String query = "SELECT * FROM games";
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int gameId = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String genre = resultSet.getString("Genre");
                Date releaseDate = resultSet.getDate("ReleaseDate");
                double rating = resultSet.getDouble("Rating");
                double price = resultSet.getDouble("Price");
                boolean isLimited = resultSet.getBoolean("IsLimited");
                int stockLevel = resultSet.getInt("StockLevel");
                Game g = new Game(gameId, name, genre, releaseDate.toLocalDate(), rating, price, isLimited, stockLevel);
                gamesList.add(g);
            }

        } catch (SQLException e) {
            throw new DaoException("displayAllGames() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return gamesList;
    }

    // Yee Chean
    public List<Game> getGameByID(int gameIDToFind) throws DaoException {
        System.out.println("Attempting finding of gameID: " + gameIDToFind);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Game> gamesList = new ArrayList<>();

        if (gameIDToFind <= 0) {
            throw new IllegalArgumentException("Invalid game ID");
        }

        try {
            // Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "SELECT * FROM games WHERE ID = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, gameIDToFind);

            // Using a PreparedStatement to execute SQL...
            resultSet = preparedStatement.executeQuery();
            // Print table data
            while (resultSet.next()) {
                int gameId = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String genre = resultSet.getString("Genre");
                Date releaseDate = resultSet.getDate("ReleaseDate");
                double rating = resultSet.getDouble("Rating");
                double price = resultSet.getDouble("Price");
                boolean isLimited = resultSet.getBoolean("IsLimited");
                int stockLevel = resultSet.getInt("StockLevel");
                Game game = new Game(gameId, name, genre, releaseDate.toLocalDate(), rating, price, isLimited, stockLevel);
                gamesList.add(game);
            }

        } catch (SQLException e) {
            throw new DaoException("Error accessing database: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("Error closing resources: " + e.getMessage());
            }
        }
        return gamesList; // may be empty
    }
    // Yee Chean
    @Override
    public void deleteByID(int idToDelete) throws DaoException
    {
        System.out.println("Attempting deletion of gameID: " + idToDelete);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            //Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "DELETE FROM games WHERE ID = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1,idToDelete);

            int rowAffected = preparedStatement.executeUpdate(); // will DELETE a row

            if(rowAffected > 0){
                System.out.println("gameID: " + idToDelete + " has been deleted.");
            }
            else{
                System.out.println("gameID: " + idToDelete + " does not exist.");
            }

        } catch (SQLException e)
        {
            throw new DaoException("Error accessing database: " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null){
                    resultSet.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                if (connection != null){
                    freeConnection(connection);
                }
            } catch (SQLException e){
                throw new DaoException("Error closing resources: " + e.getMessage());
            }
        }
    }

    //Darragh
    @Override
    public void insertGame(Game game) throws DaoException {
        String url = "jdbc:mysql://localhost/";
        String dbName = "steam_games";
        String fullURL = url + dbName;
        String userName = "root";
        String password = "";

        String sql = "INSERT INTO games (Name, Genre, ReleaseDate, Rating, Price, IsLimited, StockLevel) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(fullURL, userName, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            // Include Statement.RETURN_GENERATED_KEYS so we can set the game object to
            // match the id of the table

            preparedStatement.setString(1, game.getName());
            preparedStatement.setString(2, game.getGenre());
            preparedStatement.setDate(3, Date.valueOf(game.getReleaseDate()));
            preparedStatement.setDouble(4, game.getRating());
            preparedStatement.setDouble(5, game.getPrice());
            preparedStatement.setBoolean(6, game.isLimited());
            preparedStatement.setInt(7, game.getStockLevel());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys(); // getGeneratedKeys returns the primary
                // keys in the
                // order they were generated e.g
                // (0,1,2,3)
                if (generatedKeys.next()) { // goes through all of the primary keys until theres none left
                    int id = generatedKeys.getInt(1); // gets the last id from the results set which is the one that we
                    // just added.
                    game.setID(id); // set the games id to match the id that it has inside the database just to stay
                    // consistent.
                    System.out.println("Game has been successfully added to the database.");
                }
            } else {
                throw new SQLException("Failed to insert game. No rows affected.");
            }
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database or execute insert operation.");
            ex.printStackTrace();
        }

    }


    // Yee Chean
    @Override
    public void updateExistingGame(int idToUpdate, Game game) throws DaoException
    {
        System.out.println("Attempting update of gameID: " + idToUpdate);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            //Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "UPDATE games SET Name = ?, Genre = ?, ReleaseDate = ?, Rating = ?, Price = ?, IsLimited = ?, StockLevel = ?  WHERE ID = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, game.getName());
            preparedStatement.setString(2, game.getGenre());
            preparedStatement.setDate(3, Date.valueOf(game.getReleaseDate()));
            preparedStatement.setDouble(4, game.getRating());
            preparedStatement.setDouble(5, game.getPrice());
            preparedStatement.setBoolean(6, game.isLimited());
            preparedStatement.setInt(7, game.getStockLevel());
            preparedStatement.setInt(8, idToUpdate);

            int rowsAffected = preparedStatement.executeUpdate();
//            if (rowsAffected == 1) {
//                ResultSet generatedKeys = preparedStatement.getGeneratedKeys(); // getGeneratedKeys returns the primary
//                // keys in the
//                // order they were generated e.g
//                // (0,1,2,3)
//                if (generatedKeys.next()) { // goes through all of the primary keys until theres none left
//                    int id = generatedKeys.getInt(1); // gets the last id from the results set which is the one that we
//                    // just added.
//                    game.setID(id); // set the games id to match the id that it has inside the database just to stay
//                    // consistent.
//                    System.out.println("Game has been successfully updated on the database.");
//                }
//            } else {
//                throw new SQLException("Failed to update game. No rows affected.");
//            }

        } catch (SQLException e)
        {
            throw new DaoException("Error accessing database: " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null){
                    resultSet.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                if (connection != null){
                    freeConnection(connection);
                }
            } catch (SQLException e){
                throw new DaoException("Error closing resources: " + e.getMessage());
            }
        }
    }
    @Override
    public List<Game> findGamesUsingFilter(Comparator<Game> comparator) throws DaoException {
        List<Game> gamesList = displayAllGames(); // Retrieve all games
        gamesList.sort(comparator); // Sort the games list based on the provided comparator
        return gamesList;
    }
}

