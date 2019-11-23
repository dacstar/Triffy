package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccountBook extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_book);

        intent = getIntent();
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
