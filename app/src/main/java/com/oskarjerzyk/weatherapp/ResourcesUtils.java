package com.oskarjerzyk.weatherapp;

public class ResourcesUtils {

    /**
     * @param icon - icon identifier serialized from json
     * @return - appropriate resource id
     */
    public static int selectIconPath(String icon) {
        switch (icon) {
            case "01d":
                return R.drawable.d1;
            case "02d":
                return R.drawable.d2;
            case "03d":
                return R.drawable.d3;
            case "04d":
                return R.drawable.d4;
            case "09d":
                return R.drawable.d9;
            case "10d":
                return R.drawable.d10;
            case "11d":
                return R.drawable.d11;
            case "13d":
                return R.drawable.d13;
            case "50d":
                return R.drawable.d50;
            case "01n":
                return R.drawable.n1;
            case "02n":
                return R.drawable.n2;
            case "03n":
                return R.drawable.d3;
            case "04n":
                return R.drawable.d4;
            case "09n":
                return R.drawable.d9;
            case "10n":
                return R.drawable.n10;
            case "11n":
                return R.drawable.d11;
            case "13n":
                return R.drawable.d13;
            case "50n":
                return R.drawable.d50;
        }
        return 0;
    }

    /**
     * @param desc - description retrieved from json response
     * @return - appropriate string resource
     */
    public static int selectWeatherDescriptionPath(String desc) {
        switch (desc) {
            case "clear sky":
                return R.string.clear_sky;
            case "few clouds":
                return R.string.few_clouds;
            case "scattered clouds":
                return R.string.scattered_clouds;
            case "broken clouds":
                return R.string.broken_clouds;
            case "shower rain":
                return R.string.shower_rain;
            case "rain":
                return R.string.rain;
            case "light rain":
                return R.string.light_rain;
            case "thunderstorm":
                return R.string.thunderstorm;
            case "snow":
                return R.string.snow;
            case "mist":
                return R.string.mist;
            default:
                return R.string.undefined;
        }
    }
}
