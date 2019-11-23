package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.triffyandroid.Api.BalanceApi;
import com.example.triffyandroid.Model.Balance;

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
    Intent intent;

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
                Intent Airline_intent = new Intent(getApplicationContext(), AirlineActivity.class);
                Airline_intent.putExtra("user_id",intent.getExtras().getString("user"));
                Airline_intent.putExtra("destination",destination.getText().toString());
                Airline_intent.putExtra("outboundDate",outboundDate.getText().toString());
                Airline_intent.putExtra("inboundDate",inboundDate.getText().toString());
                Airline_intent.putExtra("cabinClass",cabinClass.getText().toString());
                startActivity(Airline_intent);

            }
        });
    }

    public void getBalance(){
        intent = getIntent();
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
        Intent bookintent = new Intent(getApplicationContext(), AccountBook.class);
        bookintent.putExtra("user_id", intent.getExtras().getString("user"));
        startActivity(bookintent);
    }

    public void trip(View view){
        Intent cityintent = new Intent(getApplicationContext(), CityActivity.class);
        cityintent.putExtra("user_id", intent.getExtras().getString("user"));
        startActivity(cityintent);
    }

    public void ssul(View view){
        Intent ssulintent = new Intent(getApplicationContext(), SsulActivity.class);
        ssulintent.putExtra("user_id", intent.getExtras().getString("user"));
        startActivity(ssulintent);
    }
}
