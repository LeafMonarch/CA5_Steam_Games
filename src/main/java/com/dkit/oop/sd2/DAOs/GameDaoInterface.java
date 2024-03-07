package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Game;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.util.List;

public interface GameDaoInterface
{
    public List<Game> findAllGames() throws DaoException;


}

