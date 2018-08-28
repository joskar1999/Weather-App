package com.oskarjerzyk.weatherapp;

public class Weather {

    private double lon;
    private double lat;
    private String desc;
    private String icon;
    private double temp;
    private int pressure;
    private int humidity;
    private double windSpeed;
    private int clouds;
    private long date;
    private long sunrise;
    private long sunset;
    private String city;

    public Weather(double lon, double lat, String desc, String icon, double temp, int pressure, int humidity, double windSpeed, int clouds, long date, long sunrise, long sunset, String city) {
        this.lon = lon;
        this.lat = lat;
        this.desc = desc;
        this.icon = icon;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.clouds = clouds;
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.city = city;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public String getDesc() {
        return desc;
    }

    public String getIcon() {
        return icon;
    }

    public double getTemp() {
        return temp;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getClouds() {
        return clouds;
    }

    public long getDate() {
        return date;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public String getCity() {
        return city;
    }
}
