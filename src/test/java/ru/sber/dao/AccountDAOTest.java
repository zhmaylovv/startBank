package ru.sber.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sber.controllers.ControllerDB;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountDAOTest {

    @BeforeAll
    static void beforeAll() {
        try {
            ControllerDB.dbInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void compareIdWithGettingAccountByNumber() throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        assertEquals(1, accountDAO.getAccountByNumber(new BigDecimal("12345678900987654321")).getId());
    }


    @Test
    void fallIfCheckedBalanceWorkWrong() throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        assertEquals(new BigDecimal("100500.00"), accountDAO.checkBalanceById(3));
    }

    @Test
    void checkIncreaseBalanceByAccId() throws SQLException, IOException {
        AccountDAO accountDAO = new AccountDAO();
        BigDecimal currentBalance = accountDAO.checkBalanceById(2);
        BigDecimal income = new BigDecimal("555");
        BigDecimal expectedBalance = currentBalance.add(income);
        accountDAO.increaseBalance(2, income);
        BigDecimal newBalance = accountDAO.checkBalanceById(2);
        assertEquals(expectedBalance, newBalance);
    }

    @Test
    void checkBalanceByAccNumber() throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        assertEquals(new BigDecimal("100500.00"), accountDAO.checkBalanceByNumber(new BigDecimal("12345678900987654325")));

    }

    @Test
    void getAccountById() throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        assertEquals(accountDAO.getAccountByNumber(new BigDecimal("12345678900987654321")), accountDAO.getAccountById(1));
    }

    @Test
    void increaseBalanceByNumber() {
        AccountDAO accountDAO = new AccountDAO();
        BigDecimal income = new BigDecimal("-555");
        BigDecimal number = new BigDecimal("12345678900987654322");
        assertThrows(IOException.class, ()-> accountDAO.increaseBalanceByNumber(number, income));
    }

}