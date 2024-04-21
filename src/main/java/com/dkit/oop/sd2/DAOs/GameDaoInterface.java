package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Game;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.Comparator;
import java.util.List;

public interface GameDaoInterface
{
    // Raphael Displaying tables
    public List<Game> displayAllGames() throws DaoException;

    //
    public List<Game> getGameByID(int gameIDToFind) throws DaoException;

    //Yee Chean DeleteByID
    public void deleteByID(int gameIDToDelete) throws DaoException;

    // Darragh
    public void insertGame(Game game) throws DaoException;

    // Yee Chean
    void updateExistingGame(int idToUpdate, Game game) throws DaoException;

    // Raphael Feature 6
    List<Game> findGamesUsingFilter(Comparator<Game> comparator) throws DaoException;

}