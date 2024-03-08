package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Game;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.util.List;

public interface GameDaoInterface
{
    //Raphael
    public List<Game> displayAllGames() throws DaoException;
    public List<Game> getGameByID(int gameIDToFind) throws DaoException;
    public void deleteByID(int gameIDToDelete) throws DaoException;

    public void insertGame(Game game) throws DaoException;

}

