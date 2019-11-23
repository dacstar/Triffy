package com.example.triffyandroid.Api;

import com.example.triffyandroid.Model.Balance;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BalanceApi {
    @FormUrlEncoded
    @POST("get_balance/")
    Call<Balance> getBalance(@Field("user_id") int id);
}
