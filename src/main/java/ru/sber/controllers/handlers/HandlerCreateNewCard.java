package ru.sber.controllers.handlers;

import ru.sber.dao.CardDAO;

import java.math.BigDecimal;
import java.sql.SQLException;

public class HandlerCreateNewCard extends Handler {

    @Override
    public void handlerPostController () throws SQLException {
        CardDAO cardDAO = new CardDAO();
        BigDecimal number = new BigDecimal(String.valueOf(json.get("account_number")));
        cardDAO.cardAddByAccNumber(number);
        responseMap.put("Result", "Card added successfully");
        }

    @Override
    public void handlerGetController() {

    }
}
