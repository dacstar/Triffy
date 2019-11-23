package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triffyandroid.Api.AirlineApi;
import com.example.triffyandroid.Api.BalanceApi;
import com.example.triffyandroid.Model.Airline;
import com.example.triffyandroid.Model.Balance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyBank extends AppCompatActivity {
    ProgressBar myProgressBar;
    TextView myTextView, myBalance, myPercent;
    Button search_btn;
    EditText destination,outboundDate,inboundDate, cabinClass;

    private Retrofit retrofit;
    private BalanceApi api;
    private String BASE_URL = "http://10.3.17.166:8000/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(BalanceApi.class);

        search_btn = (Button)findViewById(R.id.search_btn);
        destination = (EditText)findViewById(R.id.destination);
        outboundDate = (EditText)findViewById(R.id.outboundDate);
        inboundDate = (EditText)findViewById(R.id.inboundDate);
        cabinClass = (EditText)findViewById(R.id.cabinClass);

        getBalance();


        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AirlineActivity.class);
                intent.putExtra("destination",destination.getText().toString());
                intent.putExtra("outboundDate",outboundDate.getText().toString());
                intent.putExtra("inboundDate",inboundDate.getText().toString());
                intent.putExtra("cabinClass",cabinClass.getText().toString());
                startActivity(intent);

            }
        });
    }

    public void getBalance(){
        Intent intent = getIntent();
        myProgressBar = (ProgressBar)findViewById(R.id.h_progressbar);
        myTextView = (TextView)findViewById(R.id.cureentValue);
        myBalance = (TextView)findViewById(R.id.balance);
        myPercent = (TextView)findViewById(R.id.percent);


        Call<Balance> call = api.getBalance(intent.getExtras().getInt("id"));

        call.enqueue(new Callback<Balance>() {
            @Override
            public void onResponse(Call<Balance> call, Response<Balance> response) {
                if(response.isSuccessful()){
                    Log.d("적금 조회","성공");
                    Balance balance = response.body();
                    myProgressBar.setMax(balance.getGoal_amount());
                    myProgressBar.setProgress(balance.getNow_amount());
                    myTextView.setText(balance.getName());
                    myBalance.setText(balance.getNow_amount() + "");
                    myPercent.setText(balance.getAchieve() + "%");

                    if(balance.getAchieve() < 80){
                        search_btn.setClickable(false);
                    }

                }
                else{
                    Log.d("적금 조회","실패");
                }
            }

            @Override
            public void onFailure(Call<Balance> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });
    }

    public void account_book(View view){
        startActivity(new Intent(getApplicationContext(), AccountBook.class));
    }

    public void trip(View view){
        startActivity(new Intent(getApplicationContext(), TripActivity.class));
    }

}
