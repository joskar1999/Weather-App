package com.oskarjerzyk.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ForecastFragment extends Fragment {

    private double lat;
    private double lon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    private class ForecastAscyncTask extends AsyncTask<URL, Void, ArrayList<Forecast>> {

        @Override
        protected ArrayList<Forecast> doInBackground(URL... urls) {

            ForecastQueryUtils forecastQueryUtils = new ForecastQueryUtils();
            ArrayList<Forecast> forecasts = null;
            String jsonResponse = "";
            String requestUrl;
            requestUrl = forecastQueryUtils.createCurrentWeatherRequestUrl(lat, lon);
            try {
                jsonResponse = forecastQueryUtils.makeHttpRequest(requestUrl);
            } catch (IOException e) {
                Toast.makeText(getContext(), "HTTP request error!", Toast.LENGTH_SHORT).show();
            }
            try {
                forecasts = forecastQueryUtils.extractForecastFromJson(jsonResponse);
            } catch (JSONException e) {
                Toast.makeText(getContext(), "JSON processing error!", Toast.LENGTH_SHORT).show();
            }
            return forecasts;
        }
    }
}
