package ru.sber.dao;

import org.junit.jupiter.api.AfterAll;
import ru.sber.controllers.ControllerDB;
import ru.sber.models.Card;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardDAOTest {

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
    void checkGetCardById() throws SQLException {
        CardDAO cardDAO = new CardDAO();
        assertEquals(1111222233334441L, cardDAO.getCardById(1).getCardNumber());
    }

    @Test
    void queryGetCardList() throws SQLException {
        CardDAO cardDAO = new CardDAO();
        List<Card> cardList = cardDAO.getCardListByClientId(1);
        assertEquals(1111222233334441L, cardList.get(0).getCardNumber());
        assertEquals(1111222233334442L, cardList.get(1).getCardNumber());
        assertEquals(1111222233334447L, cardList.get(2).getCardNumber());
    }


    @Test
    void getMaxCardNumber() throws SQLException {
        CardDAO cardDAO = new CardDAO();
        List<Card> allCardsList = cardDAO.getAllCardList();
        Long expectedNumber = allCardsList.get(allCardsList.size()-1).getCardNumber();
        try {
            assertEquals(expectedNumber, cardDAO.getMaxCardNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    void cardAddByAccNumber() throws SQLException {
        CardDAO cardDAO = new CardDAO();
        int oldSize = cardDAO.getAllCardList().size();
        cardDAO.cardAddByAccNumber(new BigDecimal("12345678900987654321"));
        assertEquals(oldSize + 1, cardDAO.getAllCardList().size());
    }
}