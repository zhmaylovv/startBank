package ru.sber.dao;

import ru.sber.controllers.ControllerDB;
import ru.sber.models.Card;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс CardDAO содержит в себе методы работы класса Card с БД.
 */
public class CardDAO {

    /**
     * Создаем экземпляр класса и поднимаем в нем соединение через запрос к контроллеру
     */
    public CardDAO() {
        Connection connection = ControllerDB.getConnection();
    }

    /**
     * Промежуточный метод подготовки строки запроса и обработки данных полученных от основного метода
     * Получение карты по ID из базы данных
     *
     * @param id id карты
     * @return обьект класса Card
     */
    public Card getCardById(int id) throws SQLException {
        String query = "SELECT * FROM cards WHERE id = " + id;
        return getCard(query);
    }

    /**
     * Вспомогательный метод вычисления максимального номера карты в Б
     *
     * @return максимальное значение номера карты
     * @throws SQLException
     */
    public Long getMaxCardNumber() throws SQLException {
        Long maxCardNumber = null;
        String query = "SELECT MAX(CARD_NUMBER) FROM CARDS";
        PreparedStatement preparedStatement = ControllerDB.getPreparedStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        maxCardNumber = resultSet.getLong(1);
        return maxCardNumber;
    }

    /**
     * Промежуточный метод подготовки строки запроса и обработки данных,
     * при необходимости, полученных от основного метода
     * Получение всех карт клиента по его Id
     *
     * @param clientId id клиента по которому мы получаем карты
     * @return список всех карт клиента
     */
    public List<Card> getCardListByClientId(int clientId) throws SQLException {
        String query = "SELECT * FROM cards, accounts WHERE " +
                "cards.account_id = accounts.id and accounts.client_id = " + clientId;
        return getCardList(query);
    }


    /**
     * Метод реализующий функционал записи в БД информации от обьекта Card
     *
     * @param card  Записываемый обьект
     * @param query запрос в который нужно подставить данные от бьекта
     * @throws SQLException
     */
    private void editCardsDb(Card card, String query) throws SQLException {
        PreparedStatement preparedStatement = ControllerDB.getPreparedStatement(query);
        preparedStatement.setLong(1, card.getCardNumber());
        preparedStatement.setInt(2, card.getAccountId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Основной метод реализующий функционал подключения к БД и запроса данных.
     *
     * @param query строка запроса в формате SQL
     * @return Обьект типа Card
     */
    private Card getCard(String query) throws SQLException {
        Card card = new Card();
        PreparedStatement preparedStatement = ControllerDB.getPreparedStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            card.setId(resultSet.getInt("ID"));
            card.setCardNumber(resultSet.getLong("CARD_NUMBER"));
            card.setId(resultSet.getInt("ACCOUNT_ID"));
        }

        if (preparedStatement != null){
            preparedStatement.close();
        }
        return card;
    }

    /**
     * Основной метод реализующий подключение к БД для получения списка карт
     *
     * @param query строка запроса к БД
     * @return список карт
     */
    private List<Card>getCardList(String query) throws SQLException {
        List<Card> cardList = new ArrayList<>();
        PreparedStatement preparedStatement = ControllerDB.getPreparedStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Card card = new Card();
            card.setId(resultSet.getInt("ID"));
            card.setCardNumber(resultSet.getLong("CARD_NUMBER"));
            card.setAccountId(resultSet.getInt("ACCOUNT_ID"));
            cardList.add(card);
        }
        if (preparedStatement != null){
            preparedStatement.close();
        }

        return cardList;
    }

    /**
     * Промежуточный метод подготовки строки запроса.
     * Получение всех карт в БД
     *
     * @return список карт
     */
    public List<Card> getAllCardList() throws SQLException {
        String query = "SELECT * FROM cards";
        return getCardList(query);
    }

    /**
     * Добавление карты по номеру аккаунта
     *
     * @param number номер аккаунта по которому выпускается карта
     * @return true- если карта добвлена
     * @throws SQLException если во время добавления произошла ошибка
     */
    public boolean cardAddByAccNumber(BigDecimal number) throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        int accountId = accountDAO.getAccountByNumber(number).getId();
        String query = "INSERT INTO cards (card_number, account_id) VALUES(?, ?)";
        Card newCard = new Card();
        newCard.setCardNumber(getMaxCardNumber() + 1);
        newCard.setAccountId(accountId);
        editCardsDb(newCard, query);
        return true;
    }
}
