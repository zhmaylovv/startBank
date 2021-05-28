package ru.sber.controllers.handlers;

import ru.sber.dao.AccountDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class HandlerDeposit extends Handler {

    @Override
    public void handlerPostController() throws SQLException, IOException {
            AccountDAO accountDAO = new AccountDAO();
            BigDecimal number = new BigDecimal(String.valueOf(json.get("account_number")));
            BigDecimal size = new BigDecimal(String.valueOf(json.get("size")));
            accountDAO.increaseBalanceByNumber(number, size);
            responseMap.put("Result", "Balance increase completed successfully");
    }

    @Override
    public void handlerGetController() {

    }
}
