package ru.sber.dao;

import ru.sber.controllers.ControllerDB;
import ru.sber.models.Account;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс AccountDAO содержит в себе методы работы класса Account с БД.
 */
public class AccountDAO {

    /**
     * При создании эеземпляра класса поднимаем соединение,
     * запришиая его у контроллера ДБ
     */
    public AccountDAO() {
        Connection connection = ControllerDB.getConnection();
    }

    /**
     * Промежуточный метод подготовки строки запроса и обработки данных полученных от основного метода
     * Проверка баланса аккаунта по ID
     *
     * @param id - идентификатор аккаунта
     * @return Баланс указанного аккаунта в формате BigDecimal
     */
    public BigDecimal checkBalanceById(int id) throws SQLException {
        String query = "SELECT * FROM accounts WHERE id = " + id;
        return getAccount(query).getBalance();
    }

    /**
     * Промежуточный метод подготовки строки запроса и обработки данных полученных от основного метода
     * Проверка баланса аккаунта по номеру аккаунта
     *
     * @param account_number - номер аккаунта
     * @return Баланс указанного аккаунта в формате BigDecimal
     */
    public BigDecimal checkBalanceByNumber(BigDecimal account_number) throws SQLException {
        String query = "SELECT * FROM accounts WHERE account_number = " + account_number;
        return getAccount(query).getBalance();
    }

    /**
     * Промежуточный метод подготовки строки запроса и обработки данных полученных от основного метода
     * Получение аккаунта по его Id
     *
     * @param id id аккаунта
     * @return экземпляр класса Account
     */
    public Account getAccountById(int id) throws SQLException {
        String query = "SELECT * FROM accounts WHERE id = " + id;
        return getAccount(query);
    }

    /**
     * Промежуточный метод подготовки строки запроса и обработки данных полученных от основного метода
     * Получение аккаунта по его номеру
     *
     * @param number номер аккаунта
     * @return экземпляр класса Account
     */
    public Account getAccountByNumber(BigDecimal number) throws SQLException {
        String query = "SELECT * FROM accounts WHERE account_number = " + number;
        return getAccount(query);
    }

    /**
     * Основной метод реализующий функционал подключения
     * к базе и выполнения запроса подготовленного в других методах
     * для получения данных
     *
     * @param query запрос в формате SQL
     * @return Обьект класса Account
     */
    private Account getAccount(String query) throws SQLException {
        Account account = new Account();
        PreparedStatement preparedStatement = ControllerDB.getPreparedStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            account.setId(resultSet.getInt("ID"));
            account.setNumber(resultSet.getBigDecimal("ACCOUNT_NUMBER"));
            account.setBalance(resultSet.getBigDecimal("BALANCE"));
            account.setClientId(resultSet.getInt("CLIENT_ID"));
        }
        if (preparedStatement != null){
            preparedStatement.close();
        }
        return account;
    }

    /**
     * Метод реализующий функционал подключения
     * к базе и изменения данных в нейю
     *
     * @param account Аккаунт данные из которого нужно изменить для подстановки в строку запроса.
     * @param query   строка запроса
        * @throws IOException если пришло отрицательное значение
     */
    private void editAccountsDb(Account account, String query) throws SQLException {
        PreparedStatement preparedStatement = ControllerDB.getPreparedStatement(query);
        preparedStatement.setBigDecimal(1, account.getNumber());
        preparedStatement.setBigDecimal(2, account.getBalance());
        preparedStatement.setInt(3, account.getClientId());
        preparedStatement.executeUpdate();
        if (preparedStatement != null){
            preparedStatement.close();
        }
    }

    /**
     * Метод внесения средств на счет
     *
     * @param AccountId id счета для пополнения
     * @param sum       сумма пополнения
     * @return true - если запись в БД не вызвала ошибок. false - если запись не удалась.
     * @throws IOException если пришло отрицательное значение
     */
    public boolean increaseBalance(int AccountId, BigDecimal sum) throws SQLException, IOException {
        if (sum.compareTo(new BigDecimal(0))<0){
            throw new IOException();
        }
        Account account = getAccountById(AccountId);
        account.setBalance(account.getBalance().add(sum));
        String query = "UPDATE accounts SET account_number = ?, balance = ?, " +
                "client_id = ? WHERE id = " + AccountId;
        editAccountsDb(account, query);
        return true;
    }


    /**
     * Метод снятия средств на счет
     *
     * @param AccountId id счета для снятия
     * @param sum       сумма снятия
     * @return true - если запись в БД не вызвала ошибок. false - если запись не удалась.
     * @throws IOException если пришло отрицательное значение
     */
    public boolean substractionBalance(int AccountId, BigDecimal sum) throws SQLException, IOException {
        if (sum.compareTo(new BigDecimal(0))>0){
            throw new IOException();
        }
        Account account = getAccountById(AccountId);
        account.setBalance(account.getBalance().subtract(sum));
        String query = "UPDATE accounts SET account_number = ?, balance = ?, " +
                "client_id = ? WHERE id = " + AccountId;
        editAccountsDb(account, query);
        return true;

    }

    /**
     * Метод внесения средств на счет,
     *
     * @param number номер счета для пополнения
     * @param sum    сумма пополнения
     * @return true - если запись в БД не вызвала ошибок. false - если запись не удалась.
     *
     */
    public boolean increaseBalanceByNumber(BigDecimal number, BigDecimal sum) throws SQLException, IOException {
        if (sum.compareTo(new BigDecimal(0))<0){
            throw new IOException();
        }
        Account account = getAccountByNumber(number);
        account.setBalance(account.getBalance().add(sum));
        String query = "UPDATE accounts SET account_number = ?, balance = ?, " +
                "client_id = ? WHERE account_number = " + account.getNumber();
        editAccountsDb(account, query);
        return true;
    }
}
