package com.dkit.oop.sd2.Thread;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;


import com.google.gson.*;

import com.dkit.oop.sd2.DAOs.JsonConverter;
import com.dkit.oop.sd2.DTOs.Game;

public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8888); // connect to server socket
            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());

            System.out.println("Client message: The Client is running and has connected to the server");

            boolean end = false;
            while(!end){
                System.out.println("=================================================");
                System.out.println("=====    Welcome to Steam Games Library     =====");
                System.out.println("=================================================");
                System.out.println("=       1. Display all steam games              =");
                System.out.println("=       2. Find an entity by key                =");
                System.out.println("=       3. Delete an entity by key              =");
                System.out.println("=       4. Update an Entity                     =");
                System.out.println("=       5. Get list of entities matching filter =");
                System.out.println("=       6. Display List entities as JSON string =");
                System.out.println("=       7. Display entity as JSON String        =");
                System.out.println("=       8. Add an Entity                        =");
                System.out.println("=       9. Get Images List                      =");
                System.out.println("=       0. Exit                                 =");
                System.out.println("=================================================\n");

                String command = in.nextLine();

                OutputStream os = socket.getOutputStream();
                PrintWriter socketWriter = new PrintWriter(os, true); // true => auto flush buffers

                socketWriter.println(command);

                Scanner socketReader = new Scanner(socket.getInputStream()); // wait for, and retrieve the reply

                // Raphael
                if (command.startsWith("1"))
                {
                    String firstString = socketReader.nextLine();
                    System.out.println(firstString);
                    // Displaying the table
                    while (socketReader.hasNextLine())
                    {
                        String line = socketReader.nextLine();
                        System.out.println(line);
                    }
                }

                // Yee Chean
                else if (command.startsWith("2"))
                {
                    System.out.println("Please enter the game ID to find:");
                    int gameId = in.nextInt();
                    socketWriter.println(gameId); // Send the game ID to the server
                    // Receive and display the game details from the server
                    while (socketReader.hasNextLine()) {
                        String line = socketReader.nextLine();
                        System.out.println(line);
                    }
                }

                // Yee Chean
                else if (command.startsWith("3"))
                {
                    System.out.println("Please enter the game ID to delete:");
                    int gameId = in.nextInt();
                    socketWriter.println(gameId); // Send the game ID to the server
                    // Receive and display successful deletion from the server

                }

                // Yee Chean & Raphael
                else if (command.startsWith("4"))
                {
                    System.out.println("Please enter the game ID you want to update:");
                    int gameId = in.nextInt();
                    socketWriter.println(gameId); // Send the game ID to the server

                    // Prompt the user for updated details
                    System.out.print("Enter the updated name: ");
                    String name = in.nextLine();
                    System.out.print("Enter the updated genre: ");
                    String genre = in.nextLine();
                    System.out.print("Enter the updated release date (YYYY-MM-DD):");
                    LocalDate release = LocalDate.parse(in.next());
                    System.out.print("Enter the updated rating: ");
                    double rating = in.nextDouble();
                    System.out.print("Enter the updated price: ");
                    double price = in.nextDouble();
                    System.out.print("Enter the updated limited (true/false): ");
                    boolean limited = in.nextBoolean();
                    System.out.print("Enter the updated stock level: ");
                    String stock = in.nextLine();


                    // Send the updated details to the server
                    socketWriter.println(name);
                    socketWriter.println(genre);
                    socketWriter.println(release);
                    socketWriter.println(rating);
                    socketWriter.println(price);
                    socketWriter.println(limited);
                    socketWriter.println(stock);
                    // Send other updated details similarly

                    // Receive and display the response from the server
                    while (socketReader.hasNextLine())
                    {
                        String line = socketReader.nextLine();
                        System.out.println(line);
                    }
                }

                // Raphael
                else if (command.startsWith("5"))
                {
                    String filteredGamesTable = socketReader.nextLine();
                    System.out.println(filteredGamesTable);
                    // Displaying the table
                    while (socketReader.hasNextLine())
                    {
                        String line = socketReader.nextLine();
                        System.out.println(line);
                    }
                }

                // Raphael Display List entities as JSON string
                else if (command.startsWith("6")) {
                    while (socketReader.hasNextLine()) {
                        String line = socketReader.nextLine();
                        System.out.println(line);
                    }
                }

                // Yee Chean Display Entity By ID as JSON string
                else if (command.startsWith("7")) {
                    System.out.println("Please enter a game ID");
                    int gameID = in.nextInt();
                    socketWriter.println(gameID);

                    String line = socketReader.nextLine();
                    System.out.println(line);
                }

                // Darragh Add/Insert an entity
                else if (command.startsWith("8"))
                {
                    addEntity(socketWriter, socketReader, in);
                }

                else if (command.startsWith("9"))
                {

                }

                else if (command.startsWith("0")){
                    end = true;
                }
                else
                {
                    String input = socketReader.nextLine();
                    System.out.println("Client message: Response from server: \"" + input + "\"");
                }
                socketWriter.close();
                socketReader.close();
            }
            socket.close();

        }
        catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }

    private void addEntity(PrintWriter socketWriter, Scanner socketReader, Scanner in)
            throws IOException {
        try {
            System.out.print("Please enter game name: ");
            String name = in.nextLine();
            System.out.print("Please enter game genre: ");
            String genre = in.nextLine();
            System.out.print("Please enter game release date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(in.next());
            System.out.print("Please enter game rating: ");
            double rating = in.nextDouble();
            System.out.print("Please enter game price: ");
            double price = in.nextDouble();
            System.out.print("Please state if game is limited (true/false): ");
            boolean isLimited = in.nextBoolean();
            System.out.print("Please enter stock level: ");
            int stockLevel = in.nextInt();
            int gameID = 0;
            Game newGame = new Game(gameID, name, genre, date, rating, price, isLimited, stockLevel);

            String jsonRequest = JsonConverter.gameToJson(newGame);

            socketWriter.println(jsonRequest); // Send JSON request to server
            socketWriter.println(); // Send this to act as delimiter/break the while loop

            String jsonResponse = socketReader.nextLine();
            System.out.println("Server response: " + jsonResponse);

            JsonObject responseJson = JsonParser.parseString(jsonResponse).getAsJsonObject();
            if (responseJson.has("success")) {
                if (responseJson.get("success").getAsBoolean()) {
                    // Confirm entity was added successfully and display the entity
                    System.out.println("Entity added successfully:");
                    System.out.println(responseJson.get("entity"));
                } else {
                    // Display an error if entity failed to be added
                    System.out.println("Error: " + responseJson.get("error").getAsString());
                }
            } else {
                System.out.println("Invalid response from server.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }

    }
}


//  LocalTime time = LocalTime.parse(timeString); // Parse timeString -> convert to LocalTime object if required