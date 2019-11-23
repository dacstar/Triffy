package com.example.triffyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.triffyandroid.Api.UserApi;
import com.example.triffyandroid.Model.UserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText et_id,et_pw;
    String username,password;

    private Retrofit retrofit;
    private UserApi api;
    private String BASE_URL = "http://10.3.17.166:8000/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = (EditText) findViewById(R.id.ed_Id);
        et_pw = (EditText) findViewById(R.id.ed_password);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(UserApi.class);
    }

    public void bt_in(View view) {
        username = et_id.getText().toString();
        password = et_pw.getText().toString();

        Login(username,password);

//        Log.d("userInfo",userInfo.toString());

    }

    public void bt_up(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
        finish();

    }


    public void Login(String username, String password){
        Call<UserInfo> call = api.Login(username,password);
        final UserInfo[] userInfo = new UserInfo[1];

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.isSuccessful()){
                    Log.d("로그인 성공","로그인이 되었습니다.");
                    userInfo[0] = response.body();
                    Log.d("userInfo",userInfo[0].toString());

                    Intent intent = new Intent(getApplicationContext(), MyBankActivity.class);
                    intent.putExtra("user", userInfo[0].getUser());
                    intent.putExtra("id", userInfo[0].getId());
                    intent.putExtra("check_balance", userInfo[0].isCheck_balance());
                    startActivity(intent);

                }
                else{
                    Log.d("로그인 실패","아이디와 비밀번호를 확인해주세요.");
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });

    }
}
