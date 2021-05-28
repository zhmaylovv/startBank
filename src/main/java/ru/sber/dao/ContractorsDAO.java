package ru.sber.dao;

import ru.sber.controllers.ControllerDB;
import ru.sber.models.Account;
import ru.sber.models.Card;
import ru.sber.models.Contractor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContractorsDAO {
    public ContractorsDAO() {
        Connection connection = ControllerDB.getConnection();
    }

    public boolean contractorAdd(String name) throws SQLException {
        String query = "INSERT INTO contractors (name) VALUES(?)";
        Contractor contractor = new Contractor();
        contractor.setName(name);
        editContractorDb(contractor, query);
        return true;
    }


    private void editContractorDb(Contractor contractor, String query) throws SQLException {
        PreparedStatement preparedStatement = ControllerDB.getPreparedStatement(query);
        preparedStatement.setString(1, contractor.getName());
        preparedStatement.executeUpdate();
        if (preparedStatement != null){
            preparedStatement.close();
        }
    }

    public List<Contractor> getContractorList() throws SQLException {
        List<Contractor> contractorsList = new ArrayList<>();
        PreparedStatement preparedStatement = ControllerDB.getPreparedStatement("SELECT * FROM contractors");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Contractor contractor = new Contractor();
            contractor.setId(resultSet.getInt("ID"));
            contractor.setName(resultSet.getString("name"));
            contractorsList.add(contractor);
        }

        if (preparedStatement != null){
            preparedStatement.close();
        }
        return contractorsList;
    }
}
