package com.example.triffyandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.triffyandroid.Adapter.AirlineAdapter;
import com.example.triffyandroid.Api.AirlineApi;
import com.example.triffyandroid.Model.Airline;
import com.example.triffyandroid.Model.Balance;
import com.google.gson.Gson;
import com.google.gson.JsonParser;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AirlineActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private AirlineApi api;
    private String BASE_URL = "http://10.3.17.166:8000/api/";

    ListView listView;
    List<Airline> airlines;
    AirlineAdapter airlineAdapter;
    Balance balance;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airline);

        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Call<Balance> buyAirline = api.spentAmount(airlineAdapter.getItem(position).getPrice(),"항공권");
                buyAirline.enqueue(new Callback<Balance>() {
                    @Override
                    public void onResponse(Call<Balance> call, Response<Balance> response) {
                        balance = response.body();
                        OnClickHandler();
                    }

                    @Override
                    public void onFailure(Call<Balance> call, Throwable t) {
                        Log.d("error",t.getMessage());
                    }
                });

                Call<Void> saveAirlne = api.saveAirline(
                        intent.getExtras().getString("user_id"),
                        airlineAdapter.getItem(position).getName(),
                        airlineAdapter.getItem(position).getOutdeparture(),
                        airlineAdapter.getItem(position).getOutarrival(),
                        airlineAdapter.getItem(position).getIndeparture(),
                        airlineAdapter.getItem(position).getInarrival(),
                        airlineAdapter.getItem(position).getPrice()
                        );
                saveAirlne.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("save","저장");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("error",t.getMessage());
                    }
                });
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(AirlineApi.class);

        intent = getIntent();
        Call<List<Airline>> call = api.getAirline(
                intent.getExtras().getString("destination"),
                intent.getExtras().getString("outboundDate"),
                intent.getExtras().getString("inboundDate"),
                intent.getExtras().getString("cabinClass")
        );

        call.enqueue(new Callback<List<Airline>>() {
            @Override
            public void onResponse(Call<List<Airline>> call, Response<List<Airline>> response) {
                airlines = response.body();
                ArrayList<Airline> arrayList = (ArrayList<Airline>)airlines;
                airlineAdapter = new AirlineAdapter(getApplicationContext(),arrayList);
                listView.setAdapter(airlineAdapter);


            }

            @Override
            public void onFailure(Call<List<Airline>> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });
    }

    public void OnClickHandler()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("예매가 완료되었습니다").setMessage("예매 관리 페이지로 이동하시겠습니까?");

        builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Intent reserveintent = new Intent(getApplicationContext(), ReserveActivity.class);
                reserveintent.putExtra("user_id",intent.getExtras().getString("user_id"));
                startActivity(reserveintent);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
