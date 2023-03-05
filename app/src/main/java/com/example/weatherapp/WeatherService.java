package com.example.weatherapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class response for downloading and sharing all the data from the source
 */
public class WeatherService {
    public final static String BASE_SOURCE_URL_GET_ID = "https://www.metaweather.com/api/location/search/?query=";
    public final static String BASE_SOURCE_URL_GET_BY_ID = "https://www.metaweather.com/api/location/search/";

    Context context;
    String cityId;

    public WeatherService(Context context) {
        this.context = context;
    }

    // This interface will notify the main thread about the new data about the city id when available.
    public interface VolleyResponseListener {
        void onError(String message, String url);
        void onResponse(String cityId);
    }

    // This interface will notify the main thread about the new data about the forecast by id when available.
    public interface ForecastWeatherById {
        void onError(String error, String url);
        void onResponse(List<WeatherReportModel>models);
    }

    // This interface will notify the main thread about the new data about the forecast by name when available.
    public interface ForecastWeatherByName {
        void onError(String error, String url);
        void onResponse(List<WeatherReportModel>models);
    }


    // Return the city id by its name.
    // Unfortunately, due to background threads possess that take place in this action,
    // we must create a callback that notify the main thread about the returned value.
    public void getCityId(String cityName, VolleyResponseListener mVolleyResponseListener) {

        // Update the base url to the target
        String url = BASE_SOURCE_URL_GET_ID + cityName;

        // Request the data from the source in the url
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            cityId = "";

            try {
                JSONObject cityInfo = response.getJSONObject(0);
                cityId = cityInfo.getString("woeid");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mVolleyResponseListener.onResponse(cityId);

        }, error -> mVolleyResponseListener.onError(error.toString(), BASE_SOURCE_URL_GET_ID));


        //Add the request to the queue
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    //this class is getting city id and return its weather forecast
    // (Also as callback)
    public void getWeatherById(String cityId, ForecastWeatherById mForecastWeatherById) {

        List<WeatherReportModel>models = new ArrayList<>();

        // The link for the wanted city
        String url = BASE_SOURCE_URL_GET_BY_ID + cityId;

        // Get the JSON object request and process the data
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather_list");

                for (int i = 0; i < consolidated_weather_list.length(); i++) {

                    //Get the forecast fot the next 6 days from the json array
                    WeatherReportModel forecast_weather = new WeatherReportModel();

                    JSONObject one_day_forecast = (JSONObject) consolidated_weather_list.get(0);

                    forecast_weather.setId(one_day_forecast.getInt("id"));
                    forecast_weather.setWeather_state_name(one_day_forecast.getString("weather_state_name"));
                    forecast_weather.setWeather_state_abbr(one_day_forecast.getString("weather_state_abbr"));
                    forecast_weather.setGetWind_direction_compass(one_day_forecast.getString("getWind_direction_compass"));
                    forecast_weather.setCreated(one_day_forecast.getString("created"));
                    forecast_weather.setDate((one_day_forecast.getString("applicable_date")));
                    forecast_weather.setMin_temp(one_day_forecast.getLong("min_temp"));
                    forecast_weather.setMax_temp(one_day_forecast.getLong("max_temp"));
                    forecast_weather.setThe_temp(one_day_forecast.getLong("the_temp"));
                    forecast_weather.setWind_speed(one_day_forecast.getLong("wind_speed"));
                    forecast_weather.setWind_direction(one_day_forecast.getLong("wind_direction"));
                    forecast_weather.setArr_pressure((one_day_forecast.getInt("arr_pressure")));
                    forecast_weather.setHumidity(one_day_forecast.getInt("humidity"));
                    forecast_weather.setVisibility(one_day_forecast.getLong("visibility"));
                    forecast_weather.setPredictability((one_day_forecast.getInt("predictability")));

                    models.add(forecast_weather);
                }

                //Notify the main thread about the data
                mForecastWeatherById.onResponse(models);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> mForecastWeatherById.onError(error.toString(), BASE_SOURCE_URL_GET_BY_ID));

        //Add the request to the queue
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getForecastByName(String cityName, ForecastWeatherByName mForecastWeatherByName) {

        // Get the city id by its name
        getCityId(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message, String url) {
                mForecastWeatherByName.onError(message, url);
            }

            @Override
            public void onResponse(String cityId) {

                // Get the forecast by the city id
                getWeatherById(cityId, new ForecastWeatherById() {
                    @Override
                    public void onError(String error, String url) {
                        mForecastWeatherByName.onError(error, url);
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> models) {

                        // We got the forecast, now notify the main thread
                        mForecastWeatherByName.onResponse(models);
                    }
                });
            }
        });
    }

}
