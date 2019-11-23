package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.triffyandroid.Api.AirlineApi;
import com.example.triffyandroid.Model.Airline;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AirlineActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private AirlineApi api;
    private String BASE_URL = "http://10.3.17.166:8000/api/show_airplane";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airline);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(AirlineApi.class);

        Intent intent = getIntent();
        Call<List<Airline>> call = api.getAirline(
                intent.getExtras().getString("destination"),
                intent.getExtras().getString("outboundDate"),
                intent.getExtras().getString("inboundDate"),
                intent.getExtras().getString("cabinClass")
        );
        call.enqueue(new Callback<List<Airline>>() {
            @Override
            public void onResponse(Call<List<Airline>> call, Response<List<Airline>> response) {
                List<Airline> airlines = response.body();
                for(Airline airline : airlines){

                }
            }

            @Override
            public void onFailure(Call<List<Airline>> call, Throwable t) {
                Log.d("error",t.getMessage());

            }
        });

    }
}
