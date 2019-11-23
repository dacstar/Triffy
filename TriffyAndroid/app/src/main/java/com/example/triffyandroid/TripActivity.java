package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
    }

    public void my_bank(View view){
        startActivity(new Intent(getApplicationContext(), MyBank.class));
    }

    public void account_book(View view){
        startActivity(new Intent(getApplicationContext(), AccountBook.class));
    }


}
