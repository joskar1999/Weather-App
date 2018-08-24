package com.oskarjerzyk.weatherapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NowFragment extends Fragment {

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
}
