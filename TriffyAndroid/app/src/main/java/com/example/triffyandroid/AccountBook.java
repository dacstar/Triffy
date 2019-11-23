package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccountBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_book);
    }

    public void my_bank(View view){
        startActivity(new Intent(getApplicationContext(), MyBank.class));
    }

    public void trip(View view){
        startActivity(new Intent(getApplicationContext(), TripActivity.class));
    }
}
