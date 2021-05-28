package ru.sber.dao;

import ru.sber.controllers.ControllerDB;
import ru.sber.models.Account;
import ru.sber.models.ContractorAccount;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Класс CardDAO содержит в себе методы работы класса AccountContractors с БД.
 */
public class AccountContractorsDAO {
    public Connection connection = ControllerDB.getConnection();
    public AccountContractorsDAO() throws SQLException {
    }

    /**
     * Метод перевода средств от клиента контрагенту. Реализация через "транзакции" к БД.
     * @param clientAccountNumber Номер счета клиента
     * @param contractorAccountNumber Номер счета
     * @param sum
     * @throws SQLException
     */
    public void transaction (BigDecimal clientAccountNumber, BigDecimal contractorAccountNumber, BigDecimal sum) throws SQLException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatementSub = null;

            try {
                AccountDAO accountDAO = new AccountDAO();
                Account account = accountDAO.getAccountByNumber(clientAccountNumber);
                account.setBalance(account.getBalance().subtract(sum));
                String query = "UPDATE accounts SET account_number = ?, balance = ?, " +
                        "client_id = ? WHERE account_number = " + clientAccountNumber;
                preparedStatementSub = connection.prepareStatement(query);
                preparedStatementSub.setBigDecimal(1, account.getNumber());
                preparedStatementSub.setBigDecimal(2, account.getBalance());
                preparedStatementSub.setInt(3, account.getClientId());
                preparedStatementSub.executeUpdate();

            } finally {
                if (preparedStatementSub != null){
                    preparedStatementSub.close();
                }

            }

            PreparedStatement preparedStatementSum = null;

            try {

                ContractorAccount contractorAccount = getContractorAccByNumber(contractorAccountNumber);
                contractorAccount.setBalance(contractorAccount.getBalance().add(sum));
                String query = "UPDATE contractor_accounts SET account_number = ?, balance = ?, " +
                        "contractor_id = ? WHERE account_number = " + contractorAccountNumber;
                preparedStatementSum = connection.prepareStatement(query);
                preparedStatementSum.setBigDecimal(1, contractorAccount.getNumber());
                preparedStatementSum.setBigDecimal(2, contractorAccount.getBalance());
                preparedStatementSum.setInt(3, contractorAccount.getContractorId());
                preparedStatementSum.executeUpdate();

            } finally {
                if (preparedStatementSum != null){
                    preparedStatementSum.close();
                }

            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();

        }finally {
            if (connection != null){
                connection.close();
            }
        }


    }


    public ContractorAccount getContractorAccByNumber (BigDecimal number) throws SQLException {
        ContractorAccount contractorAccount = new ContractorAccount();
        PreparedStatement preparedStatement = ControllerDB.getPreparedStatement("SELECT * FROM contractor_accounts WHERE account_number = " + number);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            contractorAccount.setId(resultSet.getInt("ID"));
            contractorAccount.setNumber(resultSet.getBigDecimal("ACCOUNT_NUMBER"));
            contractorAccount.setBalance(resultSet.getBigDecimal("BALANCE"));
            contractorAccount.setContractorId(resultSet.getInt("contractor_id"));
        }
        preparedStatement.close();
        return contractorAccount;
    }
}
