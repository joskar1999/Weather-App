package com.oskarjerzyk.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

    private String latitude;
    private String longitude;

    public NowFragment() {
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
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
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
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
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
}
