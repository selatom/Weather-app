package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter in order to display each forecast neatly
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<WeatherReportModel> models;

    public RecyclerViewAdapter(List<WeatherReportModel> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflate the forecast vir=ew and create new viewHolder instance
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (this.models.size() > 0) {

            WeatherReportModel reportModel = this.models.get(position);

            // Start display the data
            holder.temp.setText(MessageFormat.format("{0}*", reportModel.getThe_temp()));
            holder.min.setText(MessageFormat.format("{0}*", reportModel.getMin_temp()));
            holder.max.setText(MessageFormat.format("{0}*", reportModel.getMax_temp()));

            holder.humidity.setText(MessageFormat.format("%", reportModel.getHumidity()));
            holder.wind.setText(MessageFormat.format("k/h", reportModel.getWind_speed()));

            holder.date.setText(reportModel.getDate());

            // Display weather status
            switch (reportModel.getWeather_state_name()) {
                case "Heavy CloudDate":
                    holder.status.setBackgroundResource(R.drawable.heavyclouds);
                    break;
                case "Light CloudDate":
                    holder.status.setBackgroundResource(R.drawable.cloudy);
                    break;
                case "Heavy RainDate":
                    holder.status.setBackgroundResource(R.drawable.heavyrain);
                    break;
                case "Light RainDate":
                case "ShowersDate":
                    holder.status.setBackgroundResource(R.drawable.downpour);
                    break;
                case "LightningDate":
                    holder.status.setBackgroundResource(R.drawable.thunderstorm);
                    break;
                default:
                    holder.status.setBackgroundResource(R.drawable.sun);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * View holder who holds an instance to the forecast view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView date;
        public final TextView min;
        public final TextView max;
        public final TextView temp;
        public final TextView humidity;
        public final TextView wind;
        public final ImageView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.detail_date);
            min = itemView.findViewById(R.id.detail_min);
            max = itemView.findViewById(R.id.detail_max);
            temp = itemView.findViewById(R.id.detail_temp);
            humidity = itemView.findViewById(R.id.detail_humidity);
            wind = itemView.findViewById(R.id.detail_wind);
            status= itemView.findViewById(R.id.detail_status);
        }
    }
}
