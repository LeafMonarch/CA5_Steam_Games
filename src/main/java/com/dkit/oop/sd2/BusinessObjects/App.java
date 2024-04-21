package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.JsonConverter;
import com.dkit.oop.sd2.DAOs.MySqlGameDao;
import com.dkit.oop.sd2.DAOs.GameDaoInterface;
import com.dkit.oop.sd2.DTOs.Game;
import com.dkit.oop.sd2.DTOs.GamesNameComparator;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// LOGS

//  Raphael:
//  Display All Games in a Table
//  Made MYSQL Database in XAMPP
//  Made GitHub Repo
//  Replaced old code following the new DAO Interface
//  Find Key and Display this particular game.
//  Get a list of matching entities by NameComparator
//  Display a single entity in JSON format

// Yee Chean
// DeleteByID & DeleteByIDValidation
// Made GitHub Repo
// Display list of entities in JSON format
// Find Key and Display this particular game.
//

// Darragh
// Insert a new Game Object
//  Made GitHub Repo
// JUNIT Methods
// Testing JUNIT Methods

public class App {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        GameDaoInterface IGameDao = new MySqlGameDao();

        try {
            System.out.println("=================================================");
            System.out.println("=====    Welcome to Steam Games Library     =====");
            System.out.println("=================================================");
            System.out.println("=       1. Display all steam games              =");
            System.out.println("=       2. Find an entity by key                =");
            System.out.println("=       3. Delete an entity by key              =");
            System.out.println("=       4. Insert an entity                     =");
            System.out.println("=       5. Update an entity                     =");
            System.out.println("=       6. Get list of entities matching filter =");
            System.out.println("=       7. Convert List entities as JSON string =");
            System.out.println("=       8. Convert entity as JSON String        =");
            System.out.println("=       0. Exit                                 =");
            System.out.println("=================================================\n");

            int findGameID;
            int deleteGameID;

            int option = -1;
            while (option != 0) {
                System.out.print("Please Enter your choice: ");
                option = kb.nextInt();

                switch (option) {
                    case 1:
                        // Raphael
                        displayAllGames(IGameDao);
                        break;
                    case 2:
                        // Raphael & Yee Chean
                        do {
                            System.out.println("Please Enter a gameID to Find: ");
                            while (!kb.hasNextInt()) {
                                System.out.println("That is not an ID, please Try Again...");
                                kb.next();
                            }
                            findGameID = kb.nextInt();
                            List<Game> gameList = IGameDao.getGameByID(findGameID);
                            printGamesTable(gameList);
                        } while (findGameID <= 0);
                        break;

                    case 3:
                        //Yee Chean
                        do {
                            System.out.println("Please Enter a gameID to Delete: ");
                            while (!kb.hasNextInt()) {
                                System.out.println("That is not an ID, please Try Again...");
                                kb.next();
                            }
                            deleteGameID = kb.nextInt();
                            IGameDao.deleteByID(deleteGameID);
                        } while (deleteGameID <= 0);
                        break;
                    case 4:
                        //Darragh
                        kb.nextLine();
                        System.out.print("Please enter game name: ");
                        String name = kb.nextLine();
                        System.out.print("Please enter game genre: ");
                        String genre = kb.nextLine();
                        System.out.print("Please enter game release date (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(kb.next());
                        System.out.print("Please enter game rating: ");
                        double rating = kb.nextDouble();
                        System.out.print("Please enter game price: ");
                        double price = kb.nextDouble();
                        System.out.print("Please state if game is limited (true/false): ");
                        boolean isLimited = kb.nextBoolean();
                        System.out.print("Please enter stock level: ");
                        int stockLevel = kb.nextInt();
                        int gameID = 0;
                        Game game = new Game(gameID, name, genre, date, rating, price, isLimited, stockLevel);
                        IGameDao.insertGame(game);
                        break;

                    case 5:
                        //Yee Chean
                        kb.nextLine();
                        System.out.println("Please select the gameID that you wish to update: ");
                        int gameIDu = kb.nextInt();
                        kb.nextLine();
                        System.out.print("Please enter game name: ");
                        String nameu = kb.nextLine();
                        System.out.print("Please enter game genre: ");
                        String genreu = kb.nextLine();
                        System.out.print("Please enter game release date (YYYY-MM-DD): ");
                        LocalDate dateu = LocalDate.parse(kb.next());
                        System.out.print("Please enter game rating: ");
                        double ratingu = kb.nextDouble();
                        System.out.print("Please enter game price: ");
                        double priceu = kb.nextDouble();
                        System.out.print("Please state if game is limited (true/false): ");
                        boolean isLimitedu = kb.nextBoolean();
                        System.out.print("Please enter stock level: ");
                        int stockLevelu = kb.nextInt();
                        Game gameu = new Game(gameIDu, nameu, genreu, dateu, ratingu, priceu, isLimitedu, stockLevelu);
                        IGameDao.updateExistingGame(gameIDu, gameu);
                        break;
                    case 6:
                        //Raphael
                        List<Game> filteredGames = IGameDao.findGamesUsingFilter(new GamesNameComparator());
                        printGamesTable(filteredGames);
                        break;
                    case 7:
                        //Yee Chean
                        try {
                            List<Game> allGames = IGameDao.displayAllGames();
                            String gamesJson = JsonConverter.gamesListToJson(allGames);
                            System.out.println("Games List converted to JSON:");
                            printJson(gamesJson);
                        } catch (DaoException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 8:
                        //Raphael
                        try {
                            System.out.println("Please enter the game ID to convert to JSON:");
                            int gameIDToConvert = kb.nextInt();
                            List<Game> gameList = IGameDao.getGameByID(gameIDToConvert);

                            if (!gameList.isEmpty()) {
                                Game gameToConvert = gameList.get(0); // Get the first game from the list
                                String gameJSON = JsonConverter.gameToJson(gameToConvert);
                                System.out.println("Game converted to JSON:");
                                printJson(gameJSON);
                            } else {
                                System.out.println("The game with that ID is not found");
                            }
                        } catch (DaoException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 0:
                        System.out.println("Exiting Steam Games Library. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a valid option.");
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    // Raphael displaying table to menu, Originally in DAO.
    // Calling the displayAllGames method
    public static void displayAllGames(GameDaoInterface iGameDao) throws DaoException {
        List<Game> gamesList = iGameDao.displayAllGames();
        printGamesTable(gamesList);
    }
    // Calling the getGameByID method
    private static void getGameByID(GameDaoInterface iGameDao, int gameIDToFind) throws DaoException {
        List<Game> gamesList = iGameDao.getGameByID(gameIDToFind);
        printGamesTable(gamesList);
    }
    // Displaying the table with set widths
    private static void printGamesTable(List<Game> gamesList) {
        // Print table headers
        System.out.println("\n=============================================================================================================================");
        System.out.printf("%-2s %-8s %-30s %-20s %-15s %-10s %-10s %-10s %-10s %-2s%n", "=", "GameID", "Name", "Genre", "ReleaseDate", "Rating", "Price", "IsLimited", "StockLevel", "=");
        System.out.println("=============================================================================================================================");
        // Print table data
        for (Game game : gamesList) {
            System.out.printf("%-2s %-8d %-30s %-20s %-15s %-10.1f %-10.2f %-10s %-9d  %-1s%n", "=", game.getID(), game.getName(), game.getGenre(), game.getReleaseDate(), game.getRating(), game.getPrice(), game.isLimited(), game.getStockLevel(), "=");
        }
        System.out.println("=============================================================================================================================\n");
    }
    // Display GSON in format of JSON
    private static void printJson(String jsonString) {
        Object jsonObject = gson.fromJson(jsonString, Object.class);
        String prettyJsonString = gson.toJson(jsonObject);
        System.out.println(prettyJsonString);
    }
}