package com.oskarjerzyk.weatherapp;

public class Weather {

    private String lat;
    private String lon;
    private double temperature;
    private int pressure;
    private int humidity;
    private double windSpeed;
    private int clouds;
    private long sunrise;
    private long sunset;
    private String city;
    private String description;
    private String icon;

    public Weather(String lat, String lon, double temperature, int pressure, int humidity, double windSpeed, int clouds, long sunrise, long sunset, String city, String description, String icon) {
        this.lat = lat;
        this.lon = lon;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.clouds = clouds;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.city = city;
        this.description = description;
        this.icon = icon;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public double getTemperature() {
        return temperature;
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

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
