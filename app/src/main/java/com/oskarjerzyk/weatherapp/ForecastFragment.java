package com.oskarjerzyk.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ForecastFragment extends Fragment implements LocationListener {

    private ArrayList<Forecast> weatherForecast;

    private LocationManager locationManager;

    private View view;
    private RecyclerView recyclerView;
    private ForecastArrayAdapter forecastArrayAdapter;

    private double lat;
    private double lon;

    public ForecastFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_forecast, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.forecast_recyclerview);
        forecastArrayAdapter = new ForecastArrayAdapter(getContext(), weatherForecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(forecastArrayAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        weatherForecast = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();

        ForecastAsyncTask forecastAsyncTask = new ForecastAsyncTask();
        forecastAsyncTask.execute();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class ForecastAsyncTask extends AsyncTask<URL, Void, ArrayList<Forecast>> {

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

        @Override
        protected void onPostExecute(ArrayList<Forecast> forecasts) {

            weatherForecast.clear();
            weatherForecast.addAll(forecasts);
            forecastArrayAdapter.notifyDataSetChanged();
        }
    }
}
