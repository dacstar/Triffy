package com.example.triffyandroid.Api;

import com.example.triffyandroid.Model.Exchange;
import com.example.triffyandroid.Model.Hotel;
import com.example.triffyandroid.Model.ReserveHotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CityApi {
    @FormUrlEncoded
    @POST("show_house/")
    Call<List<Hotel>> getHotelList(
            @Field("text") String text,
            @Field("arrival_date") String arrival_date,
            @Field("departure_date") String departure_date
    );

    @POST("get_exchange/")
    Call<Exchange[]> getExchangeRate();

    @FormUrlEncoded
    @POST("save_house/")
    Call<Void> saveHotel(
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("price") double price,
            @Field("arrival") String arrival,
            @Field("departure") String departure
    );

    @FormUrlEncoded
    @POST("show_reserve_hotel/")
    Call<ReserveHotel> ReserveHotelCheck(@Field("user_id") String user_id);
}
