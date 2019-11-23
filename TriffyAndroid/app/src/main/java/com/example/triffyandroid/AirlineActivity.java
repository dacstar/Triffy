package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.triffyandroid.Adapter.AirlineAdapter;
import com.example.triffyandroid.Api.AirlineApi;
import com.example.triffyandroid.Api.BalanceApi;
import com.example.triffyandroid.Model.Airline;
import com.example.triffyandroid.Model.AirlineKey;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AirlineActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private AirlineApi api;
    private String BASE_URL = "http://10.3.17.166:8000/api/";

    Gson gson = new Gson();
    ListView listView;
    JsonParser jsonParser = new JsonParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airline);

        listView = (ListView)findViewById(R.id.listView);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(AirlineApi.class);

        Intent intent = getIntent();
        Call<ResponseBody> call = api.getAirline(
                intent.getExtras().getString("destination"),
                intent.getExtras().getString("outboundDate"),
                intent.getExtras().getString("inboundDate"),
                intent.getExtras().getString("cabinClass")
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    String str = response.body().string();
                    Log.d("str",str);
                    JsonObject jo = (JsonObject) jsonParser.parse(str);
                    Map zero = ((Map)jo.get(""+0));
                    Iterator<Map.Entry> it = zero.entrySet().iterator();
                    while(it.hasNext()) {
                        Map.Entry pair = it.next();
                        Log.d("pair", ""+pair.getKey() + ", " + pair.getValue());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });
    }
    private void loading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                AirlineAdapter airlineAdapter = new AirlineAdapter(getApplicationContext(),airlines);
//                listView.setAdapter(airlineAdapter);
            }
        }, 10000);
    }
}
