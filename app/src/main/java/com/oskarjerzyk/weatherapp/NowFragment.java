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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class NowFragment extends Fragment implements LocationListener {

    private TextView cityTextView;
    private TextView coordTextView;
    private TextView temperatureTextView;
    private TextView dateTextView;
    private TextView descriptionTextView;
    private TextView pressureTextView;
    private TextView humidityTextView;
    private TextView windSpeedTextView;
    private TextView cloudsTextView;
    private TextView sunriseTextView;
    private TextView sunsetTextView;
    private ImageView iconImageView;

    private LocationManager locationManager;

    private double lat;
    private double lon;

    public NowFragment() {
        //
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cityTextView = (TextView) getView().findViewById(R.id.city_textview);
        coordTextView = (TextView) getView().findViewById(R.id.coord_textview);
        temperatureTextView = (TextView) getView().findViewById(R.id.temperature_textview);
        dateTextView = (TextView) getView().findViewById(R.id.date_textview);
        descriptionTextView = (TextView) getView().findViewById(R.id.description_textview);
        pressureTextView = (TextView) getView().findViewById(R.id.pressure_textview);
        humidityTextView = (TextView) getView().findViewById(R.id.humidity_textview);
        windSpeedTextView = (TextView) getView().findViewById(R.id.wind_speed_textview);
        cloudsTextView = (TextView) getView().findViewById(R.id.clouds_textview);
        sunriseTextView = (TextView) getView().findViewById(R.id.sunrise_textview);
        sunsetTextView = (TextView) getView().findViewById(R.id.sunset_textview);
        iconImageView = (ImageView) getView().findViewById(R.id.icon_imageview);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Checking if GPS permission is already granted,
     * if not app will ask for it.
     * Requesting GPS location data
     */
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

    /**
     * When location is changed this method will be invoked
     * Getting users location and executing AsyncTask
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();

        WeatherAsyncTask task = new WeatherAsyncTask();
        task.execute();
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //
    }

    @Override
    public void onProviderEnabled(String s) {
        //
    }

    @Override
    public void onProviderDisabled(String s) {
        //
    }

    /**
     * Executing http request and updating UI with new weather data
     */
    private class WeatherAsyncTask extends AsyncTask<URL, Void, Weather> {

        @Override
        protected Weather doInBackground(URL... urls) {

            QueryUtils queryUtils = new QueryUtils();
            Weather weather = null;
            String jsonResponse = "";
            String requestUrl = "";
            requestUrl = queryUtils.createCurrentWeatherRequestUrl(lat, lon);
            try {
                jsonResponse = queryUtils.makeHttpRequest(requestUrl);
            } catch (IOException e) {
                Toast.makeText(getContext(), "HTTP request error!", Toast.LENGTH_SHORT).show();
            }
            try {
                weather = queryUtils.extractDataFromJson(jsonResponse);
            } catch (JSONException e) {
                Toast.makeText(getContext(), "JSON processing error!", Toast.LENGTH_SHORT).show();
            }

            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {

            if (weather == null) {
                return;
            }
            updateUi(weather);
        }
    }

    private void updateUi(Weather weather) {
        cityTextView.setText(weather.getCity());
        coordTextView.setText("coord: " + Double.toString(weather.getLat()) + ", " + Double.toString(weather.getLon()));
        temperatureTextView.setText(Integer.toString(ConvertUtils.convertKelvinToCelsius(weather.getTemp())) + (char) 0x00B0 + "C");
        setWeatherDescription(weather.getDesc());
        dateTextView.setText(ConvertUtils.convertUnixTimeToDate(weather.getDate()));
        pressureTextView.setText(Integer.toString(weather.getPressure()) + " hPa");
        humidityTextView.setText(Integer.toString(weather.getHumidity()) + " %");
        windSpeedTextView.setText(Double.toString(weather.getWindSpeed()) + " km/h");
        cloudsTextView.setText(Integer.toString(weather.getClouds()) + " %");
        sunriseTextView.setText(ConvertUtils.convertUnixTimeToHour(weather.getSunrise()));
        sunsetTextView.setText(ConvertUtils.convertUnixTimeToHour(weather.getSunset()));
        setWeatherIcon(weather.getIcon());
    }

    private void setWeatherDescription(String desc) {
        switch (desc) {
            case "clear sky":
                descriptionTextView.setText(R.string.clear_sky);
                break;
            case "few clouds":
                descriptionTextView.setText(R.string.few_clouds);
                break;
            case "scattered clouds":
                descriptionTextView.setText(R.string.scattered_clouds);
                break;
            case "broken clouds":
                descriptionTextView.setText(R.string.broken_clouds);
                break;
            case "shower rain":
                descriptionTextView.setText(R.string.shower_rain);
                break;
            case "rain":
                descriptionTextView.setText(R.string.rain);
                break;
            case "thunderstorm":
                descriptionTextView.setText(R.string.thunderstorm);
                break;
            case "snow":
                descriptionTextView.setText(R.string.snow);
                break;
            case "mist":
                descriptionTextView.setText(R.string.mist);
                break;
        }
    }

    private void setWeatherIcon(String icon) {
        switch (icon) {
            case "01d":
                iconImageView.setImageResource(R.drawable.d1);
                break;
            case "02d":
                iconImageView.setImageResource(R.drawable.d2);
                break;
            case "03d":
                iconImageView.setImageResource(R.drawable.d3);
                break;
            case "04d":
                iconImageView.setImageResource(R.drawable.d4);
                break;
            case "09d":
                iconImageView.setImageResource(R.drawable.d9);
                break;
            case "10d":
                iconImageView.setImageResource(R.drawable.d10);
                break;
            case "11d":
                iconImageView.setImageResource(R.drawable.d11);
                break;
            case "13d":
                iconImageView.setImageResource(R.drawable.d13);
                break;
            case "50d":
                iconImageView.setImageResource(R.drawable.d50);
                break;
            case "01n":
                iconImageView.setImageResource(R.drawable.n1);
                break;
            case "02n":
                iconImageView.setImageResource(R.drawable.n2);
                break;
            case "03n":
                iconImageView.setImageResource(R.drawable.d3);
                break;
            case "04n":
                iconImageView.setImageResource(R.drawable.d4);
                break;
            case "09n":
                iconImageView.setImageResource(R.drawable.d9);
                break;
            case "10n":
                iconImageView.setImageResource(R.drawable.n10);
                break;
            case "11n":
                iconImageView.setImageResource(R.drawable.d11);
                break;
            case "13n":
                iconImageView.setImageResource(R.drawable.d13);
                break;
            case "50n":
                iconImageView.setImageResource(R.drawable.d50);
                break;
        }
    }
}
