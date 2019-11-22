package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.triffyandroid.Model.User;
import com.example.triffyandroid.Service.UserService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserService userService = new UserService();
        User user = new User("kkt1212","123",26,"M","124124-1212423","230221966424");
        userService.UserRetrofit(user);
    }
}
