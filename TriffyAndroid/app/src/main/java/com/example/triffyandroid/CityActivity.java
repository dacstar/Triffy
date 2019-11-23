package com.example.triffyandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.triffyandroid.Adapter.HotelAdapter;
import com.example.triffyandroid.Api.CityApi;
import com.example.triffyandroid.Model.Exchange;
import com.example.triffyandroid.Model.Hotel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private CityApi api;
    private String BASE_URL = "http://10.3.17.166:8000/api/";

    List<Hotel> hotels;
    ListView listView;
    HotelAdapter hotelAdapter;
    Exchange[] exchange;
    EditText arrival_date,departure_date;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        arrival_date = (EditText)findViewById(R.id.arrival_date);
        departure_date = (EditText)findViewById(R.id.departure_date);

        listView = (ListView)findViewById(R.id.list_city);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Call<Void> saveHotel = api.saveHotel(
                        intent.getExtras().getString("user_id"),
                        hotelAdapter.getItem(position).getHotel_name(),
                        hotelAdapter.getItem(position).getMin_price(),
                        arrival_date.getText().toString(),
                        departure_date.getText().toString()
                );
                saveHotel.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("저장","저장완료");
                        OnClickHandler();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("error",t.getMessage());
                    }
                });
            }
        });

        intent = getIntent();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(CityApi.class);

        Call<Exchange[]> exchangeCall = api.getExchangeRate();
        exchangeCall.enqueue(new Callback<Exchange[]>() {
            @Override
            public void onResponse(Call<Exchange[]> call, Response<Exchange[]> response) {
                exchange = response.body();
            }

            @Override
            public void onFailure(Call<Exchange[]> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });
    }

    public void search(View view){
        Call<List<Hotel>> call = api.getHotelList("losangeles",arrival_date.getText().toString(),departure_date.getText().toString());
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                hotels = response.body();
                ArrayList<Hotel> arrayList = (ArrayList<Hotel>)hotels;
                hotelAdapter = new HotelAdapter(getApplicationContext(),arrayList, exchange[0]);
                listView.setAdapter(hotelAdapter);
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });


    }

    public void my_bank(View view){
        startActivity(new Intent(getApplicationContext(), MyBankActivity.class));
    }

    public void account_book(View view){
        startActivity(new Intent(getApplicationContext(), AccountBookActivity.class));
    }

    public void ssul(View view){
        startActivity(new Intent(getApplicationContext(), SsulActivity.class));
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
