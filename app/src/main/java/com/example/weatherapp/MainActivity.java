package com.example.weatherapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AppDialog.DialogEvent{

    public static final int DIALOG_FAIL_ID = 1;
    public static final int DIALOG_EXIT_IF = 2;

    TextView btn_getBy_Name;

    EditText userInput;

    RecyclerView weatherList;
    RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_getBy_Name = findViewById(R.id.btn_get_by_name);

        userInput = findViewById(R.id.et_identifier);

        weatherList = findViewById(R.id.weather_forecast);
        weatherList.setLayoutManager(new LinearLayoutManager(this));

        // Create new instance for WeatherService for latter process
        final WeatherService weatherService = new WeatherService(MainActivity.this);

        btn_getBy_Name.setOnClickListener(view -> weatherService.getForecastByName(userInput.getText().toString(), new WeatherService.ForecastWeatherByName() {
            @Override
            public void onError(String error, String url) {
                createDialog(DIALOG_FAIL_ID,
                        "Something went wrong!",
                        "We could not access the source site. Try again later or check the url: \n \n" + String.format(getResources().getString(R.string.url), url),
                        "Continue",
                        "");
            }

            @Override
            public void onResponse(List<WeatherReportModel> models) {

                // Display the data
                if (mAdapter == null) {
                    mAdapter = new RecyclerViewAdapter(models);
                }

                weatherList.setAdapter(mAdapter);
            }
        }));
    }

    private void createDialog(int id, String title, String content, String pos, String neg) {
        AppDialog appDialog = new AppDialog();
        Bundle args = new Bundle();

        args.putInt(AppDialog.DIALOG_ID, id);
        args.putString(AppDialog.DIALOG_TITLE, title);
        args.putString(AppDialog.DIALOG_CONTENT, content);
        args.putString(AppDialog.DIALOG_POS, pos);
        args.putString(AppDialog.DIALOG_NEG, neg);

        appDialog.setArguments(args);
        appDialog.show(getSupportFragmentManager(), null);
    }


    @Override
    public void onPositiveClick(int dialogId, Bundle args) {
        switch (dialogId) {
            case DIALOG_EXIT_IF:
                super.onBackPressed();
                break;
            case DIALOG_FAIL_ID:
        }
    }

    @Override
    public void onNegativeClick(int dialogId, Bundle args) {

    }

    @Override
    public void onBackPressed() {
        createDialog(DIALOG_EXIT_IF,
                "Exit from " + getResources().getString(R.string.app_name),
                "Do you sure you want to leave this application? if you do, your data will not be saved",
                "Continue",
                "Cancel");
    }
}

