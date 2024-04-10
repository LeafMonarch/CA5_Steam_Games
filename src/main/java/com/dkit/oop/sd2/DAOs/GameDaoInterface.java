package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Game;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.Comparator;
import java.util.List;

public interface GameDaoInterface
{
    //Raphael
    public List<Game> displayAllGames() throws DaoException;
    public List<Game> getGameByID(int gameIDToFind) throws DaoException;
    public void deleteByID(int gameIDToDelete) throws DaoException;
    public void insertGame(Game game) throws DaoException;
    public void updateExistingGame(int gameIDToUpdate, Game game) throws DaoException;
    List<Game> findGamesUsingFilter(Comparator<Game> comparator) throws DaoException;
    public String gamesListToJson(List<Game> list) throws DaoException;
    public String gameToJson(Game game) throws DaoException;
}

