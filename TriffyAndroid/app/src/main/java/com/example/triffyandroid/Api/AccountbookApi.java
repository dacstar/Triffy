package com.example.triffyandroid.Api;

import com.example.triffyandroid.Model.Accountbook;
import com.example.triffyandroid.Model.Airline;
import com.example.triffyandroid.Model.Airplane;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountbookApi {

//    @POST("check/airplane/")
//    Call<Airplane> getCalendar();

//    @FormUrlEncoded
//    @POST("calendar/add_item/")
//    Call<Accountbook> addCalendar(
//            @Field("category") String category,
//            @Field("content") String content,
//            @Field("spent") boolean spent,
//            @Field("money") int money,
//            @Field("currency") String currency,
//            @Field("time_now") String time_now
//    );

    @FormUrlEncoded
    @POST("calendar/view/")
    Call<List<Accountbook>> getDateAccount(@Field("user_id") String user_id);

}
