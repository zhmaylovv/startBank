package ru.sber.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

/**
 * Класс ControllerDB отвечает за взаимодействие компонентов с базой данных.
 */
public class ControllerDB {
    private static final String jdbcURL = "jdbc:h2:mem:test:DB_CLOSE_DELAY=-1"; //"jdbc:h2:file:/Users/u19208134/IdeaProjects/BankAPI/src/main/resources/DB/DBBankAPI;MV_STORE=false";
    private static final String jdbcUsername = "sa";//"admin";
    private static final String jdbcPassword = "";//"admin";
    private static final String schemaPath = "/Users/u19208134/IdeaProjects/BankAPI/src/main/resources/schema.sql";
    private static final String insertPath = "/Users/u19208134/IdeaProjects/BankAPI/src/main/resources/insert.sql";
    private static Connection connection;
    private static boolean dbInitFlag = false;


    /**
     * Создаем новое соединение к базе данных
     *
     * @return соединение
     */
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Метод инициализации базы данных, создание таблиц из schema.sql,
     * заполнение таблиц insert.sql
     *
     * @throws IOException в случае неудачно иннициализации БД
     */
    public static void dbInit() throws IOException {
        if (!dbInitFlag) {
            String schema = new String(Files.readAllBytes(Paths.get(schemaPath)));
            String insert = new String(Files.readAllBytes(Paths.get(insertPath)));
            Connection connection = getConnection();
            try {
                Statement statement = connection.createStatement();
                statement.execute(schema);
                statement.execute(insert);
                statement.close();
                dbInitFlag = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Готовим запрос в БД, используя "статичное" соединение
     * @param query Строка sql запроса.
     * @return подготовленное выражение
     */
    public static PreparedStatement getPreparedStatement(String query) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }
}
