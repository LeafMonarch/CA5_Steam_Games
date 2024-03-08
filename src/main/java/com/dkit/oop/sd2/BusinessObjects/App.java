package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.MySqlGameDao;
import com.dkit.oop.sd2.DAOs.GameDaoInterface;
import com.dkit.oop.sd2.DTOs.Game;
import com.dkit.oop.sd2.Exceptions.DaoException;
import java.util.List;
import java.util.Scanner;

// LOGS

//  Raphael:
//  Display All Games in a Table
//  Made MYSQL Database in XAMPP
//  Made GitHub Repo
//  Replaced old code following the new DAO Interface
//  To Do: Find Key and Display this particular game.

// Yee Chean
// DeleteByID & DeleteByIDValidation
// Made GitHub Repo
// To Do: -

// Darragh

public class App
{
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
            System.out.println("=       0. Exit                                 =");
            System.out.println("=================================================\n");

            int deleteGameID;

            int option = -1;
            while (option != 0) {
                System.out.print("Please Enter your choice: ");
                option = kb.nextInt();


                switch (option) {
                    case 1:
                        //Raphael
                        IGameDao.displayAllGames();
                        break;
                    case 2:
                        // Implement find operation
                        break;
                    case 3:
                        //Yee Chean
                        do{
                            System.out.println("Please Enter a gameID to Delete: ");
                            while(!kb.hasNextInt()){
                                System.out.println("That is not an ID, please Try Again...");
                                kb.next();
                            }
                            deleteGameID = kb.nextInt();
                            IGameDao.deleteByID(deleteGameID);
                        }while(deleteGameID <= 0);
                        break;
                    case 4:
                        //Darragh
                        // Implement insert operation
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
}
