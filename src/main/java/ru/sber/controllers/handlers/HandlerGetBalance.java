package ru.sber.controllers.handlers;

import ru.sber.dao.AccountDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class HandlerGetBalance extends Handler {


    @Override
    public void handlerGetController() throws IOException, SQLException {
        AccountDAO accountDAO = new AccountDAO();
        BigDecimal balance = accountDAO.checkBalanceByNumber(new BigDecimal(params.get("account_number")));
        responseMap.put(params.get("account_number"), balance.toString());
        jsonResponse = mapper.writeValueAsString(responseMap);

    }

    @Override
    public void handlerPostController() {
    }
}
