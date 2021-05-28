package ru.sber.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sber.dao.ClientDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerDBTest {

    @Test
    void getConnection() {
        Connection connection = ControllerDB.getConnection();
        assertEquals("org.h2.jdbc.JdbcConnection", connection.getClass().getName());
    }

    @Test
    void dbInit() throws SQLException {
        ClientDAO clientDAO = new ClientDAO();
        Assertions.assertEquals("Иванов Иван Иванович", clientDAO.getClientById(1).getFio());
    }

    @Test
    void getPreparedStatement() throws SQLException {
        ControllerDB.getConnection().prepareStatement("SELECT * FROM CLIENTS");

    }
}