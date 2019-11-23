package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.triffyandroid.Api.AirlineApi;
import com.example.triffyandroid.Api.CityApi;
import com.example.triffyandroid.Model.ReserveAirline;
import com.example.triffyandroid.Model.ReserveHotel;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReserveActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private AirlineApi airlineApi;
    private CityApi cityApi;
    private String BASE_URL = "http://10.3.17.166:8000/api/";

    Intent intent;
    ReserveAirline reserveAirline;
    ReserveHotel reserveHotel;
    TextView hotel_name,day,min_price,airline_name,out,in,airline_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        intent = getIntent();

        hotel_name = (TextView)findViewById(R.id.hotel_name);
        day = (TextView)findViewById(R.id.day);
        min_price = (TextView)findViewById(R.id.min_price);
        airline_name = (TextView)findViewById(R.id.airline_name);
        out = (TextView)findViewById(R.id.out);
        in = (TextView)findViewById(R.id.in);
        airline_price = (TextView)findViewById(R.id.airline_price);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        airlineApi = retrofit.create(AirlineApi.class);
        cityApi = retrofit.create(CityApi.class);

        Call<ReserveAirline> airlineCall = airlineApi.ReserveAirlineCheck(intent.getExtras().getString("user_id"));
        Call<ReserveHotel> hotelCall = cityApi.ReserveHotelCheck(intent.getExtras().getString("user_id"));

        airlineCall.enqueue(new Callback<ReserveAirline>() {
            @Override
            public void onResponse(Call<ReserveAirline> call, Response<ReserveAirline> response) {
                if(response.isSuccessful()) {
                    reserveAirline = response.body();
                    airline_name.setText(reserveAirline.getName());
                    out.setText("출항 : " + reserveAirline.getOutdeparture() + " ~ " + reserveAirline.getOutarrival());
                    in.setText("귀항 : " + reserveAirline.getIndeparture() + " ~ " + reserveAirline.getInarrival());
                    airline_price.setText("원화 : " + reserveAirline.getPrice() + "원");
                }
            }

            @Override
            public void onFailure(Call<ReserveAirline> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });

        hotelCall.enqueue(new Callback<ReserveHotel>() {
            @Override
            public void onResponse(Call<ReserveHotel> call, Response<ReserveHotel> response) {
                if(response.isSuccessful()){
                    reserveHotel = response.body();
                    hotel_name.setText(reserveHotel.getName());
                    day.setText("숙박기간 : " + reserveHotel.getArrival() + " ~ " + reserveHotel.getDeparture());
                    min_price.setText("달러 : " + reserveHotel.getPrice() + "$");
                }

            }

            @Override
            public void onFailure(Call<ReserveHotel> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });


    }
}
