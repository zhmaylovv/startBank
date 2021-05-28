package ru.sber.controllers.handlers;

import ru.sber.dao.CardDAO;
import ru.sber.dao.ContractorsDAO;
import ru.sber.models.Card;
import ru.sber.models.Contractor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HandlerGetContList extends Handler{

    @Override
    public void handlerPostController() {
    }

    @Override
    public void handlerGetController() throws IOException, SQLException {
        ContractorsDAO contractorsDAO = new ContractorsDAO();
        List<Contractor> contractorList = contractorsDAO.getContractorList();
        jsonResponse = mapper.writeValueAsString(contractorList);
    }
}
