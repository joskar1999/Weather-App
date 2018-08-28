package com.oskarjerzyk.weatherapp;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class QueryUtilsTest {

    private static final String FAKE_REQUEST_URL = "https://api.openweathermap.org/data/2.5/weather?lat=52.16&lon=16.72&appid=841c8cc735c2ac70e615bdb4b1bd3236";
    private static final String FAKE_JSON_RESPONSE = "{\"coord\":{\"lon\":16.5,\"lat\":52.5},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":296.15,\"pressure\":1013,\"humidity\":27,\"temp_min\":296.15,\"temp_max\":296.15},\"visibility\":10000,\"wind\":{\"speed\":5.1,\"deg\":190},\"clouds\":{\"all\":0},\"dt\":1535380200,\"sys\":{\"type\":1,\"id\":5364,\"message\":0.4352,\"country\":\"PL\",\"sunrise\":1535342237,\"sunset\":1535392339},\"id\":3096392,\"name\":\"Kazmierz\",\"cod\":200}";
    private static final double DELTA = 0.0000001;

    private QueryUtils queryUtils;

    @Before
    public void initialize() {
        queryUtils = new QueryUtils();
    }

    @Test
    public void shouldCreateCurrentWeatherRequestUrl() {
        assertEquals(FAKE_REQUEST_URL, queryUtils.createCurrentWeatherRequestUrl(52.16, 16.72));
    }

    @Test
    public void shouldMakeHttpRequestAndReturnJson() throws IOException {
        assertNotEquals(null, queryUtils.makeHttpRequest(FAKE_REQUEST_URL));
    }

    @Test
    public void shouldReadFromStream() {
        InputStream inputStream = new ByteArrayInputStream(FAKE_JSON_RESPONSE.getBytes(StandardCharsets.UTF_8));
        try {
            assertEquals(queryUtils.readFromStream(inputStream), FAKE_JSON_RESPONSE);
        } catch (IOException e) {
            //
        }
    }

    @Test
    public void shouldCreateUrl() {
        URL url = null;

        try {
            url = new URL(FAKE_REQUEST_URL);
        } catch (MalformedURLException e) {
            //
        }
        assertEquals(url, queryUtils.createUrl(FAKE_REQUEST_URL));
    }

    @Test
    public void shouldExtractDataFromJson() throws JSONException {
        Weather weather = new Weather(16.5, 52.5, "clear sky", "01d", 296.15, 1013, 27, 5.1, 0, 1535342237, 1535392339, "Kazmierz");
        assertEquals(weather.getLat(), queryUtils.extractDataFromJson(FAKE_JSON_RESPONSE).getLat(),DELTA);
        assertEquals(weather.getDesc(), queryUtils.extractDataFromJson(FAKE_JSON_RESPONSE).getDesc());
        assertEquals(weather.getHumidity(), queryUtils.extractDataFromJson(FAKE_JSON_RESPONSE).getHumidity());
        assertEquals(weather.getWindSpeed(), queryUtils.extractDataFromJson(FAKE_JSON_RESPONSE).getWindSpeed(),DELTA);
        assertEquals(weather.getClouds(), queryUtils.extractDataFromJson(FAKE_JSON_RESPONSE).getClouds());
        assertEquals(weather.getSunrise(), queryUtils.extractDataFromJson(FAKE_JSON_RESPONSE).getSunrise());
        assertEquals(weather.getCity(), queryUtils.extractDataFromJson(FAKE_JSON_RESPONSE).getCity());
    }
}
