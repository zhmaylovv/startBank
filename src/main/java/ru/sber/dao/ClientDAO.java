package ru.sber.dao;

import ru.sber.controllers.ControllerDB;
import ru.sber.models.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс ClientDAO содержит в себе методы работы класса Client с БД.
 */
public class ClientDAO extends ControllerDB{

    /**
     * Создаем новое соединение к базе данных
     *
     * @return соединение
     */
    public ClientDAO() {
        Connection connection = ControllerDB.getConnection();
    }

    /**
     * Промежуточный метод подготовки запроса и вызова основного метода для
     * @param id id клиента
     * @return обькт Client
     */
    public Client getClientById(int id) throws SQLException {
         String query = "SELECT * FROM clients WHERE id = " + id;
         return getClient(query);
    }

    /**
     * Основной метод реализующий подключение к БД для получения клиента
     * @param query строка запроса в формате sql
     * @return обькт Client
     */
    private Client getClient(String query) throws SQLException {
        Client client = new Client();
        PreparedStatement preparedStatement = getPreparedStatement(query);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                client.setId(resultSet.getInt("id"));
                client.setFio(resultSet.getString("fio"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (preparedStatement != null){
            preparedStatement.close();
        }
        return client;
    }


}
