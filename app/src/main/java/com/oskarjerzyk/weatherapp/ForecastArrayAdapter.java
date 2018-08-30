package com.oskarjerzyk.weatherapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ForecastArrayAdapter extends ArrayAdapter<Forecast> {

    private ArrayList<Forecast> forecasts = new ArrayList<>();

    public ForecastArrayAdapter(Activity context, ArrayList<Forecast> forecasts) {
        super(context, 0, forecasts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View cardItemView = convertView;
        if (cardItemView == null) {
            cardItemView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.forecast_card, parent, false);
        }

        Forecast currentForecast = getItem(position);
        double temp = currentForecast.getTemp();
        long time = currentForecast.getTime();
        String icon = currentForecast.getIcon();
        String desc = currentForecast.getDesc();

        TextView temperatureTextView = (TextView) cardItemView.findViewById(R.id.tempetature_card_textview);
        TextView timeTextView = (TextView) cardItemView.findViewById(R.id.hour_card_textview);
        TextView dateTextView = (TextView) cardItemView.findViewById(R.id.date_card_textview);
        TextView descTextView = (TextView) cardItemView.findViewById(R.id.description_card_textview);
        ImageView iconImageView = (ImageView) cardItemView.findViewById(R.id.icon_card_imageview);

        temperatureTextView.setText(Integer.toString(convertKelvinToCelsius(temp)));
        timeTextView.setText(convertUnixTimeToHour(time));
        dateTextView.setText(convertUnixTimeToDate(time));
        descTextView.setText(desc);

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

        return cardItemView;
    }

    private String convertUnixTimeToDate(long t) {
        t *= 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.format(t);
    }

    private String convertUnixTimeToHour(long t) {
        t *= 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(t);
    }

    private int convertKelvinToCelsius(double k) {
        k -= 273.15;
        int c = (int) k;
        return c;
    }
}
