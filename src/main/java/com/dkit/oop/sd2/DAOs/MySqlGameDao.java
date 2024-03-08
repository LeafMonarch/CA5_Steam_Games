package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Game;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
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
    public List<Game> displayAllGames() throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Game> gamessList = new ArrayList<>();

        try
        {
            //Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "SELECT * FROM games";
            preparedStatement = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = preparedStatement.executeQuery();
            // Print table headers
            System.out.println("\n=============================================================================================================================");
            System.out.printf("%-2s %-8s %-30s %-20s %-15s %-10s %-10s %-10s %-10s %-2s%n", "=","GameID", "Name", "Genre", "ReleaseDate", "Rating", "Price", "IsLimited", "StockLevel", "=");
            System.out.println("=============================================================================================================================");
            // Print table data
            while (resultSet.next())
            {
                int gameId = resultSet.getInt("gameID");
                String name = resultSet.getString("Name");
                String genre = resultSet.getString("Genre");
                Date releaseDate = resultSet.getDate("ReleaseDate");
                double rating = resultSet.getDouble("Rating");
                double price = resultSet.getDouble("Price");
                boolean isLimited = resultSet.getBoolean("IsLimited");
                int stockLevel = resultSet.getInt("StockLevel");
                Game g = new Game(gameId, name, genre, releaseDate.toLocalDate(), rating,price,isLimited,stockLevel);
                gamessList.add(g);
                System.out.printf("%-2s %-8d %-30s %-20s %-15s %-10.1f %-10.2f %-10s %-9d  %-1s%n","=", gameId, name, genre, releaseDate.toLocalDate(), rating, price, isLimited, stockLevel, "=");
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
            }
            System.out.println("=============================================================================================================================\n");

        } catch (SQLException e)
        {
            throw new DaoException("displayAllGames() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return gamessList;     // may be empty
    }

    @Override
    public void deleteByID(int gameIDToDelete) throws DaoException
    {
        System.out.println("Attempting deletion of gameID: " + gameIDToDelete);

        String url = "jdbc:mysql://localhost/";
        String dbName = "steam_games";
        String fullURL = url + dbName;
        String userName = "root";
        String password = "";

        String sql = "DELETE FROM games WHERE gameID = ?";

        try(Connection connection = DriverManager.getConnection(fullURL, userName, password);
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql)){

            preparedStatement1.setInt(1,gameIDToDelete);

            int rowAffected = preparedStatement1.executeUpdate(); // will DELETE a row

            if(rowAffected > 0){
                System.out.println("gameID: " + gameIDToDelete + " has been deleted.");
            }
            else{
                System.out.println("gameID: " + gameIDToDelete + " does not exist.");
            }

        }catch(SQLException ex){
            System.out.println("Failed to connect to database or execute delete operation.");
            ex.printStackTrace();
        }
    }

}

