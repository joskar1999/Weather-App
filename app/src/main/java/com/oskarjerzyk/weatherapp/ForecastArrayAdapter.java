package com.oskarjerzyk.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        holder.tempTextView.setText(Integer.toString(ConvertUtils.convertKelvinToCelsius(data.get(position).getTemp())) + (char) 0x00B0 + "C");
        holder.hourTextView.setText(ConvertUtils.convertUnixTimeToHour(data.get(position).getTime()));
        holder.dateTextView.setText(ConvertUtils.convertUnixTimeToDate(data.get(position).getTime()));
        holder.descTextView.setText(ResourcesUtils.selectWeatherDescriptionPath(data.get(position).getDesc()));
        holder.iconImageView.setImageResource(ResourcesUtils.selectIconPath(data.get(position).getIcon()));
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
}
