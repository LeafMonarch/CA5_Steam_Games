package com.dkit.oop.sd2.Thread;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
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

            System.out.println("=================================================");
            System.out.println("=====    Welcome to Steam Games Library     =====");
            System.out.println("=================================================");
            System.out.println("=       1. Display all Entities”                =");
            System.out.println("=       2. “Display Entity by Id                =");
            System.out.println("=       3. Add an Entity                        =");
            System.out.println("=================================================\n");
            String command = in.nextLine();

            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true); // true => auto flush buffers

            socketWriter.println(command);

            Scanner socketReader = new Scanner(socket.getInputStream()); // wait for, and retrieve the reply


            // OLD MENU FROM APP, COULD POSSIBLY BE USED IF WE NEED TO MOVE EVERYTHING FROM APP TO CLIENT
//            if (command.startsWith("1"))
//            {
//                String firstString = socketReader.nextLine();
//                System.out.println(firstString);
//                // Displaying the table
//                while (socketReader.hasNextLine())
//                {
//                    String line = socketReader.nextLine();
//                    System.out.println(line);
//                }
//            }
//
//            else if (command.startsWith("2"))
//            {
//                System.out.println("Please enter the game ID to find:");
//                int gameId = in.nextInt();
//                socketWriter.println(gameId); // Send the game ID to the server
//                // Receive and display the game details from the server
//                while (socketReader.hasNextLine()) {
//                    String line = socketReader.nextLine();
//                    System.out.println(line);
//                }
//            }
//            else if (command.startsWith("5"))
//            {
//                System.out.println("Please enter the game ID you want to update:");
//                int gameId = in.nextInt();
//                socketWriter.println(gameId); // Send the game ID to the server
//
//                // Prompt the user for updated details
//                System.out.print("Enter the updated name: ");
//                String name = in.nextLine();
//                System.out.print("Enter the updated genre: ");
//                String genre = in.nextLine();
//                System.out.print("Enter the updated release date (YYYY-MM-DD):");
//                LocalDate release = LocalDate.parse(in.next());
//                System.out.print("Enter the updated rating: ");
//                double rating = in.nextDouble();
//                System.out.print("Enter the updated price: ");
//                double price = in.nextDouble();
//                System.out.print("Enter the updated limited (true/false): ");
//                boolean limited = in.nextBoolean();
//                System.out.print("Enter the updated stock level: ");
//                String stock = in.nextLine();
//
//
//                // Send the updated details to the server
//                socketWriter.println(name);
//                socketWriter.println(genre);
//                socketWriter.println(release);
//                socketWriter.println(rating);
//                socketWriter.println(price);
//                socketWriter.println(limited);
//                socketWriter.println(stock);
//                // Send other updated details similarly
//
//                // Receive and display the response from the server
//                while (socketReader.hasNextLine())
//                {
//                    String line = socketReader.nextLine();
//                    System.out.println(line);
//                }
//            }

            // Raphael
            if (command.startsWith("1")) {
                while (socketReader.hasNextLine()) {
                    String line = socketReader.nextLine();
                    System.out.println(line);
                }
            }

            else if (command.startsWith("2")) {
                System.out.println("Please enter a game ID");
                int gameID = in.nextInt();
                socketWriter.println(gameID);

                String line = socketReader.nextLine();
                System.out.println(line);
            }
            else
            {
                String input = socketReader.nextLine();
                System.out.println("Client message: Response from server: \"" + input + "\"");
            }

            socketWriter.close();
            socketReader.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }
}


//  LocalTime time = LocalTime.parse(timeString); // Parse timeString -> convert to LocalTime object if required