package ru.sber.controllers.handlers;

import ru.sber.dao.AccountContractorsDAO;
import ru.sber.dao.AccountDAO;
import ru.sber.dao.ContractorsDAO;
import ru.sber.models.Account;
import ru.sber.models.Contractor;

import java.math.BigDecimal;
import java.sql.SQLException;

public class HandlerTransaction extends Handler{

    @Override
    public void handlerPostController () throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        AccountContractorsDAO accountContractorsDAO = new AccountContractorsDAO();
        BigDecimal clientAccountNumber = new BigDecimal(String.valueOf(json.get("clientAccountNumber")));
        BigDecimal contractorAccountNumber = new BigDecimal(String.valueOf(json.get("contractorAccountNumber")));
        BigDecimal size = new BigDecimal(String.valueOf(json.get("size")));
        Account clientAccount = accountDAO.getAccountByNumber(clientAccountNumber);
        if (clientAccount.getBalance().compareTo(size) < 0){
            responseMap.put("Result", "Transaction aborted- no enough money");
        }else {
            accountContractorsDAO.transaction(clientAccountNumber, contractorAccountNumber, size);
            responseMap.put("Result", "Transaction success");
        }

    }




    @Override
    public void handlerGetController() {
    }

}
