package com.dkit.oop.sd2.Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.dkit.oop.sd2.DAOs.GameDaoInterface;
import com.dkit.oop.sd2.DAOs.LocalDateAdapter;
import com.dkit.oop.sd2.DAOs.MySqlGameDao;
import com.dkit.oop.sd2.DTOs.Game;
import com.dkit.oop.sd2.Exceptions.DaoException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static com.dkit.oop.sd2.Thread.Server.ClientHandler.createGson;


// Yee Chean
// Created 2 files Server and Client
// Transferred
// To Do: Implement Feature 9

// Raphael
// Implemented Feature 10
// Introduced the LocalDateAdapter to call it as it is now in Server class "createGson"
// To Do: Make the GSON display in JSON format

// Darragh
// To Do: Implement Feature 11

public class Server {
    private static final Gson gson = createGson();;
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try (ServerSocket ss = new ServerSocket(8888)) {
            System.out.println("Server: Server started. Listening for connections on port 8888...");
            int clientNumber = 0;

            while (true) {
                Socket socket = ss.accept();
                clientNumber++;
                System.out.println("Server: Client " + clientNumber + " has connected.");
                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());
                Thread t = new Thread(new ClientHandler(socket, clientNumber));
                t.start();
                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    public static class ClientHandler implements Runnable {
        private BufferedReader socketReader;
        private PrintWriter socketWriter;
        private Socket socket;
        private int clientNumber;

        public ClientHandler(Socket clientSocket, int clientNumber) {
            try {
                this.socket = clientSocket;
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                this.socketReader = new BufferedReader(isReader);
                OutputStream os = socket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // auto flush
                this.clientNumber = clientNumber;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            GameDaoInterface gameDao = new MySqlGameDao();
            try {
                while ((message = socketReader.readLine()) != null) {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);

                    // OLD MENU FROM APP, COULD POSSIBLY BE USED IF WE NEED TO MOVE EVERYTHING FROM DAO METHODS TO SERVER
//                    if (message.startsWith("1"))
//                    {
//                        String gameTable = displayAllGames(gameDao);
//                        socketWriter.println(gameTable); // Send game table to client
//                    }
//                    else if (message.startsWith("2"))
//                    {
//                        socketWriter.println("Please Enter a gameID to Find: ");
//                        int gameId = Integer.parseInt(socketReader.readLine()); // Read game ID from client
//                        List<Game> gameList = gameDao.getGameByID(gameId);
//                        if (!gameList.isEmpty())
//                        {
//                            String gameTable = generateGameTableByID(gameList);
//                            socketWriter.println(gameTable); // Send game table to client
//                        } else {
//                            socketWriter.println("Game with ID " + gameId + " not found.");
//                        }
//                    }
//                    else if (message.startsWith("5")) {
//                        int gameId = Integer.parseInt(socketReader.readLine()); // Read game ID from client
//                        String name = socketReader.readLine();
//                        String genre = socketReader.readLine();
//                        LocalDate releaseDate = LocalDate.parse(socketReader.readLine());
//                        double rating = Double.parseDouble(socketReader.readLine());
//                        double price = Double.parseDouble(socketReader.readLine());
//                        boolean isLimited = Boolean.parseBoolean(socketReader.readLine());
//                        int stockLevel = Integer.parseInt(socketReader.readLine());
//
//                        Game updatedGame = new Game(gameId, name, genre, releaseDate, rating, price, isLimited, stockLevel);
//                        gameDao.updateExistingGame(gameId, updatedGame);
//                        socketWriter.println("Game updated successfully.");
//                    }

                    // Raphael
                    if (message.startsWith("1")) {
                        try {
                            List<Game> allGames = gameDao.displayAllGames();
                            String gamesJson = gson.toJson(allGames);
                            socketWriter.println(gamesJson);
                        } catch (DaoException e) {
                            socketWriter.println("Error occurred while retrieving games.");
                            e.printStackTrace();
                        }
                    }
                    else if (message.startsWith("2")) {
                        try {
                            int oneGameID = Integer.parseInt(socketReader.readLine());
//                            int oneGameID = socketReader.read();
                            List<Game> oneGame = gameDao.getGameByID(oneGameID);
                            String gamesJson = gson.toJson(oneGame);
                            socketWriter.println(gamesJson);
                        } catch (DaoException e) {
                            socketWriter.println("Error occurred while retrieving games.");
                            e.printStackTrace();
                        }
                    }

                }
            } catch (IOException ex) {
                System.out.println("Server: IOException: " + ex);
            }
//            catch (DaoException e) {
//                System.out.println("Server: DaoException: " + e);
//            }
            finally {
                try {
                    socket.close();
                } catch (IOException ex) {
                    System.out.println("Server: Error closing socket: " + ex);
                }
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating...");
        }

//        private String displayAllGames(GameDaoInterface gameDao) throws DaoException {
//            List<Game> gamesList = gameDao.displayAllGames();
//            return generateGamesTable(gamesList);
//        }
//
//        private String generateGamesTable(List<Game> gamesList) {
//            StringBuilder table = new StringBuilder();
//            table.append("\n=============================================================================================================================\n");
//            table.append(String.format("%-2s %-8s %-30s %-20s %-15s %-10s %-10s %-10s %-10s %-2s%n", "=", "GameID", "Name", "Genre", "ReleaseDate", "Rating", "Price", "IsLimited", "StockLevel", "="));
//            table.append("=============================================================================================================================\n");
//            for (Game game : gamesList) {
//                table.append(String.format("%-2s %-8d %-30s %-20s %-15s %-10.1f %-10.2f %-10s %-9d  %-1s%n", "=", game.getID(), game.getName(), game.getGenre(), game.getReleaseDate(), game.getRating(), game.getPrice(), game.isLimited(), game.getStockLevel(), "="));
//            }
//            table.append("=============================================================================================================================\n\n");
//            return table.toString();
//        }
//
//        private String generateGameTableByID(List<Game> gameList) {
//            StringBuilder table = new StringBuilder();
//            table.append("\n=============================================================================================================================\n");
//            table.append(String.format("%-8s %-30s %-20s %-15s %-10s %-10s %-10s %-10s%n", "GameID", "Name", "Genre", "ReleaseDate", "Rating", "Price", "IsLimited", "StockLevel"));
//            table.append("=============================================================================================================================\n");
//            for (Game game : gameList) {
//                table.append(String.format("%-8d %-30s %-20s %-15s %-10.1f %-10.2f %-10s %-9d%n", game.getID(), game.getName(), game.getGenre(), game.getReleaseDate(), game.getRating(), game.getPrice(), game.isLimited(), game.getStockLevel()));
//            }
//            table.append("=============================================================================================================================\n\n");
//            return table.toString();
//        }

        // Raphael
        // Helper method to create Gson instance with LocalDateAdapter
        static Gson createGson()
        {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
            return gsonBuilder.create();
        }
    }
}