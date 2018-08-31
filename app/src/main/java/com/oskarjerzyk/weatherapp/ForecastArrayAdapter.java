package com.oskarjerzyk.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ForecastArrayAdapter extends RecyclerView.Adapter<ForecastArrayAdapter.ForecastViewHolder> {

    Context context;
    ArrayList<Forecast> data;

    public ForecastArrayAdapter(Context context, ArrayList<Forecast> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(context).inflate(R.layout.forecast_card, parent, false);
        ForecastViewHolder viewHolder = new ForecastViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {

        holder.tempTextView.setText(Integer.toString(convertKelvinToCelsius(data.get(position).getTemp())) + (char) 0x00B0 + "C");
        holder.hourTextView.setText(convertUnixTimeToHour(data.get(position).getTime()));
        holder.dateTextView.setText(convertUnixTimeToDate(data.get(position).getTime()));
        holder.descTextView.setText(data.get(position).getDesc());
        holder.iconImageView.setImageResource(selectIconPath(data.get(position).getIcon()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {

        private TextView tempTextView;
        private TextView hourTextView;
        private TextView dateTextView;
        private TextView descTextView;
        private ImageView iconImageView;

        public ForecastViewHolder(View itemView) {
            super(itemView);

            tempTextView = (TextView) itemView.findViewById(R.id.tempetature_card_textview);
            hourTextView = (TextView) itemView.findViewById(R.id.hour_card_textview);
            dateTextView = (TextView) itemView.findViewById(R.id.date_card_textview);
            descTextView = (TextView) itemView.findViewById(R.id.description_card_textview);
            iconImageView = (ImageView) itemView.findViewById(R.id.icon_card_imageview);
        }
    }

    private int convertKelvinToCelsius(double k) {
        k -= 273.15;
        int c = (int) k;
        return c;
    }

    private String convertUnixTimeToHour(long t) {
        t *= 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(t);
    }

    private String convertUnixTimeToDate(long t) {
        t *= 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.format(t);
    }

    private int selectIconPath(String icon) {
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
}
