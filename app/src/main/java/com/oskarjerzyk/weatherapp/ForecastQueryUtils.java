package com.oskarjerzyk.weatherapp;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForecastQueryUtils extends QueryUtils {

    public ForecastQueryUtils() {
        super();
    }

    @Override
    public String createCurrentWeatherRequestUrl(double lat, double lon) {
        String requestUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=";
        requestUrl = requestUrl + Double.toString(lat) + "&lon=" + Double.toString(lon);
        requestUrl = requestUrl + "&appid=841c8cc735c2ac70e615bdb4b1bd3236";
        return requestUrl;
    }

    public ArrayList<Forecast> extractForecastFromJson(String json) throws JSONException {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        ArrayList<Forecast> forecasts = new ArrayList<>();

        JSONObject base = new JSONObject(json);
        JSONArray forecastArray = base.getJSONArray("list");

        for (int i = 0; i < forecastArray.length(); i++) {
            JSONObject currentForecast = forecastArray.getJSONObject(i);
            JSONObject main = currentForecast.getJSONObject("main");
            JSONArray weatherArray = currentForecast.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);


            long time = currentForecast.getLong("dt");
            double temp = main.getDouble("temp");
            String desc = weather.getString("description");
            String icon = weather.getString("icon");

            Forecast forecast = new Forecast(temp, time, icon, desc);
            forecasts.add(forecast);
        }

        return forecasts;
    }
}
