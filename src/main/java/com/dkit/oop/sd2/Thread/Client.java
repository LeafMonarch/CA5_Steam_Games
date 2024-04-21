package com.dkit.oop.sd2.Thread;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.io.*;
import java.net.Socket;
import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


import com.google.gson.*;

import com.dkit.oop.sd2.DAOs.JsonConverter;
import com.dkit.oop.sd2.DTOs.Game;

import static com.dkit.oop.sd2.Thread.Server.gson;

public class Client {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;


    private static final Gson gson = new Gson();
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 3222); // connect to server socket
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());

            System.out.println("Client message: The Client is running and has connected to the server");

            boolean end = false;
            while (!end) {
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
                if (command.startsWith("1")) {
                    String firstString = socketReader.nextLine();
                    System.out.println(firstString);
                    // Displaying the table
                    while (socketReader.hasNextLine()) {
                        String line = socketReader.nextLine();
                        System.out.println(line);
                    }
                }

                // Yee Chean
                else if (command.startsWith("2")) {
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
                else if (command.startsWith("3")) {
                    System.out.println("Please enter the game ID to delete:");
                    int gameId = in.nextInt();
                    socketWriter.println(gameId); // Send the game ID to the server
                    // Receive and display successful deletion from the server
                }

                // Yee Chean & Raphael
                else if (command.startsWith("4")) {
                    System.out.println("Please enter the game ID you want to update:");
                    int gameId = in.nextInt();
                    socketWriter.println(gameId); // Send the game ID to the server

                    updateEntity(socketWriter, socketReader, in);
//                    // Prompt the user for updated details
//                    in.nextLine();
//                    System.out.print("Enter the updated name: ");
//                    String name = in.nextLine();
//                    System.out.print("Enter the updated genre: ");
//                    String genre = in.nextLine();
//                    System.out.print("Enter the updated release date (YYYY-MM-DD):");
//                    LocalDate release = LocalDate.parse(in.next());
//                    System.out.print("Enter the updated rating: ");
//                    double rating = in.nextDouble();
//                    System.out.print("Enter the updated price: ");
//                    double price = in.nextDouble();
//                    System.out.print("Enter the updated limited (true/false): ");
//                    boolean limited = in.nextBoolean();
//                    in.nextLine();
//                    System.out.print("Enter the updated stock level: ");
//                    String stock = in.nextLine();
//
//                    // Send the updated details to the server
//                    socketWriter.println(name);
//                    socketWriter.println(genre);
//                    socketWriter.println(release);
//                    socketWriter.println(rating);
//                    socketWriter.println(price);
//                    socketWriter.println(limited);
//                    socketWriter.println(stock);
//                    // Send other updated details similarly
//
//                    // Receive and display the response from the server
//                    while (socketReader.hasNextLine()) {
//                        String line = socketReader.nextLine();
//                        System.out.println(line);
//                    }
                }

                // Raphael
                else if (command.startsWith("5")) {
                    String filteredGamesTable = socketReader.nextLine();
                    System.out.println(filteredGamesTable);
                    // Displaying the table
                    while (socketReader.hasNextLine()) {
                        String line = socketReader.nextLine();
                        System.out.println(line);
                    }
                }

                // Raphael Display List entities as JSON string
                else if (command.startsWith("6")) {
                    while (socketReader.hasNextLine()) {
                        String line = socketReader.nextLine();
                        printJson(line);
                    }
                }

                // Yee Chean Display Entity By ID as JSON string
                else if (command.startsWith("7")) {
                    System.out.println("Please enter a game ID");
                    int gameID = in.nextInt();
                    socketWriter.println(gameID);

                    String json = socketReader.nextLine();
                    printJson(json);
                }

                // Darragh Add/Insert an entity
                else if (command.startsWith("8")) {
                    addEntity(socketWriter, socketReader, in);
                }


                else if (command.startsWith("9")) {
                    String messageFromServer = socketReader.nextLine();
                    String[] pathsArray = gson.fromJson(messageFromServer, String[].class);
                    List<String> pathsList = Arrays.asList(pathsArray);

                    System.out.println("============================");
                    System.out.println(" Choose an Image to Receive");
                    System.out.println("============================");
                    for (int i = 0; i < pathsList.size(); i++) {
                        System.out.println((i + 1) + ". " + pathsList.get(i));
                    }
                    System.out.println("0. Cancel");
                    System.out.println("============================");
                    System.out.println("Enter the number of the image you want to receive:");

                    int selectedImage = in.nextInt();
                    boolean isValidNum = false;

                    while (isValidNum == false) {
                        if (selectedImage == 0) {
                            System.out.println("Cancelling...");
                            isValidNum = true;
                            break;
                        } else if (selectedImage > pathsList.size()) {
                            System.out.println("Invalid Number please try again :");
                            selectedImage = in.nextInt();
                        } else {
                            isValidNum = true;
                            socketWriter.println(selectedImage);
                            receiveFile("images/cat_received.jpg");
                        }
                    }
                    dataInputStream.close();
                    dataOutputStream.close();
                    // socket.close(); // may delete this

                } else if (command.startsWith("0")) {
                    end = true;
                    System.out.println("Farewell.");
                } else {
                    String input = socketReader.nextLine();
                    System.out.println("Client message: Response from server: \"" + input + "\"");
                }

                socketWriter.close();
                socketReader.close();
                socket.close();
            }

        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateEntity(PrintWriter socketWriter, Scanner socketReader, Scanner in)
            throws IOException {
        try {
            System.out.println("Please enter game ID to update:");
            int gameID = in.nextInt();
            in.nextLine();
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
                    System.out.println("Entity updated successfully:");
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

    private static void printJson(String jsonString) {
        Object jsonObject = gson.fromJson(jsonString, Object.class);
        String prettyJsonString = gson.toJson(jsonObject);
        System.out.println(prettyJsonString);
    }

    private static void receiveFile(String fileName)
            throws Exception {
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);


        // DataInputStream allows us to read Java primitive types from stream e.g. readLong()
        // read the size of the file in bytes (the file length)
        long size = dataInputStream.readLong();
        System.out.println("Server: file size in bytes = " + size);


        // create a buffer to receive the incoming bytes from the socket
        byte[] buffer = new byte[4 * 1024];         // 4 kilobyte buffer

        System.out.println("Server:  Bytes remaining to be read from socket: ");

        // next, read the raw bytes in chunks (buffer size) that make up the image file
        while (size > 0 &&
                (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {

            // above, we read a number of bytes from stream to fill the buffer (if there are enough remaining)
            // - the number of bytes we must read is the smallest (min) of: the buffer length and the remaining size of the file
            //- (remember that the last chunk of data read will usually not fill the buffer)

            // Here we write the buffer data into the local file
            fileOutputStream.write(buffer, 0, bytes);

            // reduce the 'size' by the number of bytes read in.
            // 'size' represents the number of bytes remaining to be read from the socket stream.
            // We repeat this until all the bytes are dealt with and the size is reduced to zero
            size = size - bytes;
            System.out.print(size + ", ");
        }

        System.out.println("File is Received");

        System.out.println("Look in the images folder to see the transferred file: cat_received.jpg");
        fileOutputStream.close();
    }

}

//  LocalTime time = LocalTime.parse(timeString); // Parse timeString -> convert to LocalTime object if required