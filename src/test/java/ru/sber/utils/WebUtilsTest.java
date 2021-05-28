package ru.sber.utils;

import org.junit.jupiter.api.Test;
import ru.sber.controllers.WebUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebUtilsTest {

    @Test
    void getQueryToMap() {
        Map<String, String> expect = new HashMap<>();
        expect.put("key", "1");
        Map<String, String> mapa = WebUtils.getQueryToMap("key=1");
        assertEquals(expect, mapa);
    }
}