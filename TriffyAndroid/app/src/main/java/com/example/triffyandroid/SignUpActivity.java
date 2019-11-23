package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.triffyandroid.Api.UserApi;
import com.example.triffyandroid.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    EditText m_id,m_password,m_name,m_age,m_gender,m_number;
    String username,password,gender,ssn,account;
    int age;

    private Retrofit retrofit;
    private UserApi api;
    private String BASE_URL = "http://10.3.17.166:8000/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(UserApi.class);

        m_id = (EditText) findViewById(R.id.m_id);
        m_password = (EditText) findViewById(R.id.m_password);
        m_name = (EditText) findViewById(R.id.m_name);
        m_age = (EditText) findViewById(R.id.m_age);
        m_gender = (EditText) findViewById(R.id.m_gender);
        m_number = (EditText) findViewById(R.id.m_number);

    }


    public void b_enroll(View view) {
        username = m_id.getText().toString();
        password = m_password.getText().toString();
        age =  Integer.parseInt(m_age.getText().toString());
        gender =  m_gender.getText().toString();
        ssn =  m_number.getText().toString();
        account =  m_name.getText().toString();

        User user = new User(username,password,age,gender,ssn,account);
        SignUp(user);

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void b_cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void SignUp(User user){
        Call<Void> call = api.SignUp(user.getUsername(),user.getPassword(),user.getAge(),user.getGender(),user.getSsn(),user.getAccount());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("회원가입 성공","회원가입이 되었습니다.");
                }
                else{
                    Log.d("회원가입 실패","중복된 아이디입니다.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });
    }
}
