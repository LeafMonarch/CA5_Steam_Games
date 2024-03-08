package com.dkit.oop.sd2.DTOs;

import java.time.LocalDate;

// Darragh & Raphael

public class Game {
    private int gameID;
    private String name;
    private String genre;
    private LocalDate releaseDate;
    private double rating;
    private double price;
    private boolean isLimited;
    private int stockLevel;

    // Default Constructor
    public Game() {
        this.gameID = 0;
        this.name = "";
        this.genre = "";
        this.releaseDate = LocalDate.now();
        this.rating = 0.0;
        this.price = 0.0;
        this.isLimited = false;
        this.stockLevel = 0;
    }

    public Game(int gameID, String name, String genre, LocalDate releaseDate, double rating, double price, boolean isLimited, int stockLevel) {
        this.gameID = gameID;
        this.name = name;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.price = price;
        this.isLimited = isLimited;
        this.stockLevel = stockLevel;
    }

    // Getters
    public int getGameID() {
        return gameID;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public boolean isLimited() {
        return isLimited;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    // Setters
    public void setGameID(int id) {
        this.gameID = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLimited(boolean limited) {
        isLimited = limited;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + gameID +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                ", price=" + price +
                ", isLimited=" + isLimited +
                ", stockLevel=" + stockLevel +
                '}';
    }
}
