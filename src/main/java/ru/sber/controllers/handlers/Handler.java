package ru.sber.controllers.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static ru.sber.controllers.WebUtils.getQueryToMap;

public abstract class Handler implements HttpHandler {
    public String jsonResponse;
    public ObjectMapper mapper = new ObjectMapper();
    public Map<String, String> responseMap = new HashMap<>();
    public Map<String, String> params;
    public JsonNode json;
    public int responseCode = 200;

    /**
     * Основной хендлер обработки запросов
     * После получения запроса, Для GET - парсим парметры из него утилитным методом getQueryToMap-
     * передаем их в AccountDAO, формируем мапу c ответом из которой делаем JSON отправляемый в ответе
     * @param exchange запрос/ответ сервер-клиента
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            params = getQueryToMap(exchange.getRequestURI().getQuery());
            try {
                handlerGetController();
            } catch (Exception e) {
                responseCode = 400;
                responseMap.put("Result", "Error, something goes wrong. Please check correct of input");
                e.printStackTrace();
            }
        }else if (exchange.getRequestMethod().equals("POST")){
            json = mapper.readTree(exchange.getRequestBody());
            try {
                handlerPostController();
            } catch (Exception e) {
                responseCode = 400;
                responseMap.put("Result", "Error, something goes wrong. Please check correct of input");
                e.printStackTrace();
            }
        }
        if (jsonResponse == null){
            jsonResponse = mapper.writeValueAsString(responseMap);
        }
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset= \"UTF-8\"");
        exchange.getResponseHeaders().set("Accept-Ranges", "bytes");
        exchange.sendResponseHeaders(responseCode, jsonResponse.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();
        jsonResponse = null;
        responseMap.clear();
        params.clear();
        responseCode = 200;
        json = null;
    }

    public abstract void handlerPostController () throws SQLException, IOException;

    public abstract void handlerGetController() throws IOException, SQLException;
}
