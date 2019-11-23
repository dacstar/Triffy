package com.example.triffyandroid.Api;

import com.example.triffyandroid.Model.Airline;
import com.example.triffyandroid.Model.AirlineKey;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AirlineApi {
    @FormUrlEncoded
    @POST("show_airplane/")
    Call<ResponseBody> getAirline(
            @Field("destination") String destination,
            @Field("outboundDate") String outboundDate,
            @Field("inboundDate") String inboundDate,
            @Field("cabinClass") String cabinClass
    );
}
