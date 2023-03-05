package com.example.weatherapp;

public class WeatherReportModel {
    private int id;
    private String weather_state_name;
    private String weather_state_abbr;
    private float wind_direction;
    private String created;
    private String date;
    private float min_temp;
    private float max_temp;
    private float the_temp;
    private float wind_speed;
    private String getWind_direction_compass;
    private int arr_pressure;
    private int humidity;
    private float visibility;
    private int predictability;

    public WeatherReportModel(int id, String weather_state_name, String weather_state_abbr, float wind_direction, String created, String date, float min_temp, float max_temp, float the_temp, float wind_speed, String getWind_direction_compass, int arr_pressure, int humidity, float visibility, int predictability) {
        this.id = id;
        this.weather_state_name = weather_state_name;
        this.weather_state_abbr = weather_state_abbr;
        this.wind_direction = wind_direction;
        this.created = created;
        this.date = date;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.the_temp = the_temp;
        this.wind_speed = wind_speed;
        this.getWind_direction_compass = getWind_direction_compass;
        this.arr_pressure = arr_pressure;
        this.humidity = humidity;
        this.visibility = visibility;
        this.predictability = predictability;
    }

    public WeatherReportModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeather_state_abbr() {
        return weather_state_abbr;
    }

    public void setWeather_state_abbr(String weather_state_abbr) {
        this.weather_state_abbr = weather_state_abbr;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(float min_temp) {
        this.min_temp = min_temp;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(float max_temp) {
        this.max_temp = max_temp;
    }

    public float getThe_temp() {
        return the_temp;
    }

    public void setThe_temp(float the_temp) {
        this.the_temp = the_temp;
    }

    public String getGetWind_direction_compass() {
        return getWind_direction_compass;
    }

    public void setGetWind_direction_compass(String getWind_direction_compass) {
        this.getWind_direction_compass = getWind_direction_compass;
    }

    public int getArr_pressure() {
        return arr_pressure;
    }

    public void setArr_pressure(int arr_pressure) {
        this.arr_pressure = arr_pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public int getPredictability() {
        return predictability;
    }

    public void setPredictability(int predictability) {
        this.predictability = predictability;
    }

    public String getWeather_state_name() {
        return weather_state_name;
    }

    public void setWeather_state_name(String weather_state_name) {
        this.weather_state_name = weather_state_name;
    }

    public float getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(float wind_direction) {
        this.wind_direction = wind_direction;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    @Override
    public String toString() {
        return "WeatherReportModel{" +
                "id=" + id +
                ", weather_state_name='" + weather_state_name + '\'' +
                ", weather_state_abbr='" + weather_state_abbr + '\'' +
                ", wind_direction='" + wind_direction + '\'' +
                ", created='" + created + '\'' +
                ", date='" + date + '\'' +
                ", min_temp=" + min_temp +
                ", max_temp=" + max_temp +
                ", the_temp=" + the_temp +
                ", wind_speed=" + wind_speed +
                ", getWind_direction_compass=" + getWind_direction_compass +
                ", arr_pressure=" + arr_pressure +
                ", humidity=" + humidity +
                ", visibility=" + visibility +
                ", predictability=" + predictability +
                '}';
    }
}
