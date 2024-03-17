package com.dkit.oop.sd2.DTOs;

import java.util.Comparator;


// Raphael sorting by game name
public class GamesNameComparator implements Comparator<Game> {
    @Override
    public int compare(Game game1, Game game2) {
        return game1.getName().compareTo(game2.getName());
    }
}
