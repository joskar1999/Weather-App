package com.oskarjerzyk.weatherapp;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class QueryUtils {

    public QueryUtils() {
        //
    }

    public String createCurrentWeatherRequestUrl(double lat, double lon) {
        String requestUrl = "https://api.openweathermap.org/data/2.5/weather?lat=";
        requestUrl = requestUrl + Double.toString(lat) + "&lon=" + Double.toString(lon);
        requestUrl = requestUrl + "&appid=841c8cc735c2ac70e615bdb4b1bd3236";
        return requestUrl;
    }

    public String makeHttpRequest(String requestUrl) throws IOException {

        URL url = new URL(requestUrl);
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String jsonResponse = "";

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            //
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            return null;
        }
        return url;
    }

    public String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    public Weather extractDataFromJson(String json) throws JSONException {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        JSONObject base = new JSONObject(json);
        JSONObject coordJsonObject = base.getJSONObject("coord");
        JSONArray weatherJsonArray = base.getJSONArray("weather");
        JSONObject weatherJsonObject = weatherJsonArray.getJSONObject(0);
        JSONObject mainJsonObject = base.getJSONObject("main");
        JSONObject windJsonObject = base.getJSONObject("wind");
        JSONObject cloudsJsonObject = base.getJSONObject("clouds");
        JSONObject sysJsonObject = base.getJSONObject("sys");

        double lon = coordJsonObject.getDouble("lon");
        double lat = coordJsonObject.getDouble("lat");
        String desc = weatherJsonObject.getString("description");
        String icon = weatherJsonObject.getString("icon");
        double temp = mainJsonObject.getDouble("temp");
        int pressure = mainJsonObject.getInt("pressure");
        int humidity = mainJsonObject.getInt("humidity");
        double windSpeed = windJsonObject.getDouble("speed");
        int clouds = cloudsJsonObject.getInt("all");
        long sunrise = sysJsonObject.getLong("sunrise");
        long sunset = sysJsonObject.getLong("sunset");
        String city = base.getString("name");

        Weather weather = new Weather(lon, lat, desc, icon, temp, pressure, humidity, windSpeed, clouds, sunrise, sunset, city);
        return weather;
    }

    public int convertKelvinToCelsius(double k) {
        k -= 273.15;
        int c = (int) k;
        return c;
    }
}
