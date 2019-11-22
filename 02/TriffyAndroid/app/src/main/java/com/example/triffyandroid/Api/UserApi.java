package com.example.triffyandroid.Api;

import com.example.triffyandroid.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {
    @FormUrlEncoded
    @POST("auth/signup/")
    Call<Void> createUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("age") int age,
            @Field("gender") String gender,
            @Field("ssn") String ssn,
            @Field("account") String account
            );
}
