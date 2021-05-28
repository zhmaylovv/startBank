package ru.sber.controllers.handlers;

import ru.sber.dao.CardDAO;
import ru.sber.models.Card;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HandlerGetCardList extends Handler {

    @Override
    public void handlerPostController() {
    }

    @Override
    public void handlerGetController() throws IOException, SQLException {
        CardDAO cardDAO = new CardDAO();
        List<Card> cardList = cardDAO.getCardListByClientId(Integer.parseInt(params.get("client_id")));
        jsonResponse = mapper.writeValueAsString(cardList);
    }
}