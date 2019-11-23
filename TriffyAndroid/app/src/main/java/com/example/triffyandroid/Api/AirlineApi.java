package com.example.triffyandroid.Api;

import com.example.triffyandroid.Model.Airline;
import com.example.triffyandroid.Model.Balance;
import com.example.triffyandroid.Model.ReserveAirline;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AirlineApi {
    @FormUrlEncoded
    @POST("show_airplane/")
    Call<List<Airline>> getAirline(
            @Field("destination") String destination,
            @Field("outboundDate") String outboundDate,
            @Field("inboundDate") String inboundDate,
            @Field("cabinClass") String cabinClass
    );

    @FormUrlEncoded
    @POST("check/sub_balance/")
    Call<Balance> spentAmount(
            @Field("spent_amount") double spent_amount,
            @Field("category") String air
    );

    @FormUrlEncoded
    @POST("save_airline/")
    Call<Void> saveAirline(
            @Field("user_id") String user_id,
            @Field("name")  String name,
            @Field("outdeparture") String outdeparture,
            @Field("outarrival") String outarrival,
            @Field("indeparture") String indeparture,
            @Field("inarrival") String inarrival,
            @Field("price") double price
    );

    @FormUrlEncoded
    @POST("show_reserve_airline/")
    Call<ReserveAirline> ReserveAirlineCheck(@Field("user_id") String user_id);
}
