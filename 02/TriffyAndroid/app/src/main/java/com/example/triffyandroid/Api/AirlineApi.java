package com.example.triffyandroid.Api;

import com.example.triffyandroid.Model.Airline;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AirlineApi {
    @GET("?destination={destination}&outboundDate={outboundDate}&inboundDate={inboundDate}&cabinClass={cabinClass}")
    Call<List<Airline>> getAirline(
            @Path("destination") String destination,
            @Path("outboundDate") String outboundDate,
            @Path("inboundDate") String inboundDate,
            @Path("cabinClass") String cabinClass
            );

}
