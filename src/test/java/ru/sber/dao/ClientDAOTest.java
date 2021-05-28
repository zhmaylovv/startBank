package ru.sber.dao;

import org.junit.jupiter.api.AfterAll;
import ru.sber.controllers.ControllerDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

class ClientDAOTest {

    @BeforeAll
    static void beforeAll() {
        try {
            ControllerDB.dbInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void afterAll() throws SQLException {
        ControllerDB.getConnection().close();
    }

    @Test
    void getConnection() throws SQLException {
        ClientDAO clientDAO = new ClientDAO();
        Assertions.assertEquals("Иванов Иван Иванович", clientDAO.getClientById(1).getFio());
    }
}