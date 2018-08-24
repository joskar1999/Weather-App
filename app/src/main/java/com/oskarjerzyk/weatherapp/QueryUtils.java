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

    public static Weather getCurrentData(double lat, double lon) throws IOException {
        Weather weather = null;
        URL url = createCurrentWeatherUrl(lat, lon);
        String response = makeHttpRequest(url);
        weather = extractDataFromJson(response);

        return weather;
    }

    private static URL createCurrentWeatherUrl(double lat, double lon) {
        URL url = null;

        String requestUrl = "api.openweathermap.org/data/2.5/weather?lat=";
        requestUrl = requestUrl + Double.toString(lat) + "&lon=" + Double.toString(lon);
        requestUrl = requestUrl + "&appid=841c8cc735c2ac70e615bdb4b1bd3236";

        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonresponse = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonresponse = readFromStream(inputStream);
            }
        } catch (IOException e) {

        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonresponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
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

    private static Weather extractDataFromJson(String json) {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(json);
            JSONObject coordJSON = baseJsonResponse.getJSONObject("coord");
            JSONArray weatherJSON = baseJsonResponse.getJSONArray("weather");
            JSONObject weatherJSONExtracted = (JSONObject) weatherJSON.get(0);
            JSONObject mainJSON = baseJsonResponse.getJSONObject("main");
            JSONObject windJSON = baseJsonResponse.getJSONObject("wind");
            JSONObject cloudsJSON = baseJsonResponse.getJSONObject("clouds");
            JSONObject sysJSON = baseJsonResponse.getJSONObject("sys");

            String lat = coordJSON.getString("lat");
            String lon = coordJSON.getString("lon");
            double temperature = mainJSON.getDouble("temp") - 273.15;
            int pressure = mainJSON.getInt("pressure");
            int humidity = mainJSON.getInt("humidity");
            double windSpeed = windJSON.getDouble("speed");
            int clouds = cloudsJSON.getInt("all");
            long sunrise = sysJSON.getLong("sunrise");
            long sunset = sysJSON.getLong("sunset");
            String city = baseJsonResponse.getString("name");
            String main = weatherJSONExtracted.getString("main");
            String icon = weatherJSONExtracted.getString("icon");

            return new Weather(lat, lon, temperature, pressure, humidity, windSpeed, clouds, sunrise, sunset, city, main, icon);

        } catch (JSONException e) {

        }

        return null;
    }
}
