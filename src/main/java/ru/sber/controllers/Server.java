package ru.sber.controllers;

import com.sun.net.httpserver.HttpServer;
import ru.sber.controllers.ControllerDB;
import ru.sber.controllers.handlers.HandlerAddContractor;
import ru.sber.controllers.handlers.HandlerGetContList;
import ru.sber.controllers.handlers.HandlerTransaction;
import ru.sber.controllers.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Класс Server запускает HTTP server, распределяет роутинги
 */
public class Server {
    private static HttpServer server = null;
    public static void run () throws IOException {
        server = HttpServer.create();
        server.bind(new InetSocketAddress(8765), 0);
        server.createContext("/api/get_card_list", new HandlerGetCardList());
        server.createContext("/api/get_balance", new HandlerGetBalance());
        server.createContext("/api/deposit", new HandlerDeposit());
        server.createContext("/api/create_new_card", new HandlerCreateNewCard());
        server.createContext("/api/add_contractor", new HandlerAddContractor());
        server.createContext("/api/get_contractors_list", new HandlerGetContList());
        server.createContext("/api/transaction", new HandlerTransaction());
        server.start();
        System.out.println("Server started!");
    }
    public static void stop(){
        server.stop(0);
    }
}