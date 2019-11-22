package com.example.triffyandroid.Service;

import android.util.Log;
import android.widget.Toast;

import com.example.triffyandroid.Api.UserApi;
import com.example.triffyandroid.MainActivity;
import com.example.triffyandroid.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private Retrofit retrofit;
    //private String BASE_URL = "http://10.3.17.147:8000/api/";
    private String BASE_URL = "http://10.0.2.2:8000/api/";

    public void UserRetrofit(User user){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi api = retrofit.create(UserApi.class);
        Call<Void> call = api.createUser(user.getUsername(),user.getPassword(),user.getAge(),user.getGender(),user.getSsn(),user.getAccount());

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
