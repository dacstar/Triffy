package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triffyandroid.Api.AccountbookApi;
import com.example.triffyandroid.Model.Accountbook;
import com.example.triffyandroid.Model.Airline;
import com.example.triffyandroid.Model.Airplane;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountBookActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private AccountbookApi accountbookApi;
    private String BASE_URL = "http://10.3.17.166:8000/api/";

    private CalendarView cv;
    List<Accountbook> cal_accounts;

    private TableLayout table;
    private TableRow row;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_book);

        intent = getIntent();
        cv = (CalendarView)findViewById(R.id.calendarView);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        accountbookApi = retrofit.create(AccountbookApi.class);

//        Call<Airplane> call = accountbookApi.getCalendar();
//        call.enqueue(new Callback<Airplane>() {
//            @Override
//            public void onResponse(Call<Airplane> call, Response<Airplane> response) {
//                Airplane airplane = response.body();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                try {
//                    Date date = sdf.parse(airplane.getOutdeparture());
//                    cv.setMinDate(date.getTime());
//
//                    date = sdf.parse(airplane.getInarrival());
//                    cv.setMaxDate(date.getTime());
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Airplane> call, Throwable t) {
//                Log.d("error",t.getMessage());
//            }
//        });

        Call<List<Accountbook>> listCall = accountbookApi.getDateAccount(intent.getExtras().getString("user_id"));
        listCall.enqueue(new Callback<List<Accountbook>>() {
            @Override
            public void onResponse(Call<List<Accountbook>> call, Response<List<Accountbook>> response) {
                cal_accounts = response.body();
            }

            @Override
            public void onFailure(Call<List<Accountbook>> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                String cal_date = i + "-0" + (i1+1) + "-" + i2;

                Toast.makeText(getApplicationContext(),cal_date,Toast.LENGTH_LONG).show();

                for (Accountbook accountbook : cal_accounts) {
                    if(accountbook.getDate().equals(cal_date)) {
                        table = (TableLayout)findViewById(R.id.tableLayout);
                        row = (TableRow)findViewById(R.id.accountlist);



                            TableRow tr = new TableRow(AccountBookActivity.this);
                        table.removeView(tr);
                            TextView tv = new TextView(AccountBookActivity.this);
                            tv.setText(accountbook.getCategory());
                            tr.addView(tv);

                            TextView tv1 = new TextView(AccountBookActivity.this);
                            tv1.setText(accountbook.getContent());

                            tr.addView(tv1);

                            TextView tv2 = new TextView(AccountBookActivity.this);
                            tv2.setText(accountbook.isSpent() ? "시용" : "기획");

                            tr.addView(tv2);

                            TextView tv3 = new TextView(AccountBookActivity.this);
                            tv3.setText(accountbook.getCurrency());

                            tr.addView(tv3);

                            TextView tv4 = new TextView(AccountBookActivity.this);
                            tv4.setText(accountbook.getMoney()+"");

                            tr.addView(tv4);

                            table.addView(tr);

//                            tv.setText(abt.getCategory());
                        }
//                    }
                }
            }
        });

//        accountbookApi.addCalendar("", "", false,100, "", "");
    }


    public void trip(View view){
        Intent cityintent = new Intent(getApplicationContext(), CityActivity.class);
        cityintent.putExtra("user_id", intent.getExtras().getString("user_id"));
        startActivity(cityintent);
    }

    public void ssul(View view){
        Intent ssulintent = new Intent(getApplicationContext(), SsulActivity.class);
        ssulintent.putExtra("user_id", intent.getExtras().getString("user_id"));
        startActivity(ssulintent);
    }
}
