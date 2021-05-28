package ru.sber;

import ru.sber.controllers.ControllerDB;
import ru.sber.controllers.Server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            ControllerDB.dbInit();
            System.out.println("\n" + "Database is initiated!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n" + "\n" +"Type: exit for exit");
        Scanner scanner = new Scanner(System.in);
        String command;
        do {
            command = scanner.next();
        } while (!command.equals("exit"));
        Server.stop();
        try {
            ControllerDB.getConnection().close();
        } catch (SQLException e) {
        }
        scanner.close();
    }
}
