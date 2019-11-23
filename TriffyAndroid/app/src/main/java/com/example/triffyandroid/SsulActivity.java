package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SsulActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssul);
    }

    public void my_bank(View view){
        startActivity(new Intent(getApplicationContext(), MyBankActivity.class));
    }

    public void account_book(View view){
        startActivity(new Intent(getApplicationContext(), AccountBookActivity.class));
    }

    public void trip(View view){
        startActivity(new Intent(getApplicationContext(), CityActivity.class));
    }
}
