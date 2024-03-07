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
    @Override
    public List<Game> findAllGames() throws DaoException
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
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllUseresultSet() " + e.getMessage());
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

}

