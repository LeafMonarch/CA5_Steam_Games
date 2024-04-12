package com.dkit.oop.sd2.DTOs;

import java.time.LocalDate;

// Darragh & Raphael

public class Game {
    private int id;
    private String name;
    private String genre;
    private LocalDate releaseDate;
    private double rating;
    private double price;
    private boolean isLimited;
    private int stockLevel;

    // Default Constructor
    public Game() {
        this.id = 0;
        this.name = "";
        this.genre = "";
        this.releaseDate = LocalDate.now();
        this.rating = 0.0;
        this.price = 0.0;
        this.isLimited = false;
        this.stockLevel = 0;
    }

    public Game(int id, String name, String genre, LocalDate releaseDate, double rating, double price, boolean isLimited, int stockLevel) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.price = price;
        this.isLimited = isLimited;
        this.stockLevel = stockLevel;
    }

    // Getters
    public int getID() {
        return id;
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
    public void setID(int id) {
        this.id = id;
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
                "id=" + id +
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