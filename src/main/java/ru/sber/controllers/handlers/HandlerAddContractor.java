package ru.sber.controllers.handlers;

import ru.sber.dao.CardDAO;
import ru.sber.dao.ContractorsDAO;
import ru.sber.models.Contractor;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class HandlerAddContractor extends Handler{

    /**
     * Метод обрабатывающий POST запрос на добавление контрагента.
     * @throws SQLException в случае неудачи- пробласывает исключение в основной хендлер
     */
    @Override
    public void handlerPostController () throws SQLException {
        ContractorsDAO contractorsDAO = new ContractorsDAO();
        Contractor contractor = new Contractor();
        String name = String.valueOf(json.get("name"))
                .replaceFirst("\"", "")
                .replaceFirst(".$","");
        List<Contractor> contraList = contractorsDAO.getContractorList();
        for (Contractor contra : contraList) {
            if (contra.getName().equals(name)) {
                responseMap.put("Error", "Contractor already exist");
                return;
            }
        }
        contractorsDAO.contractorAdd(name);
        responseMap.put("Result", "Contractor " + name + " added successfully");
    }

    @Override
    public void handlerGetController() {

    }
}
