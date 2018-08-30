package com.oskarjerzyk.weatherapp;

public class Forecast {

    private double temp;
    private long time;
    private String icon;
    private String desc;

    public Forecast(double temp, long time, String icon, String desc) {
        this.temp = temp;
        this.time = time;
        this.icon = icon;
        this.desc = desc;
    }

    public double getTemp() {
        return temp;
    }

    public long getTime() {
        return time;
    }

    public String getIcon() {
        return icon;
    }

    public String getDesc() {
        return desc;
    }
}
