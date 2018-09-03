package com.oskarjerzyk.weatherapp;

import java.text.SimpleDateFormat;

public class ConvertUtils {

    public static int convertKelvinToCelsius(double k) {
        k -= 273.15;
        int c = (int) k;
        return c;
    }

    public static String convertUnixTimeToHour(long t) {
        t *= 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(t);
    }

    public static String convertUnixTimeToDate(long t) {
        t *= 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.format(t);
    }
}
