package com.example.triffyandroid.Model;

public class AirlineKey {
    private String key;
    private String airline;

    public AirlineKey(String key, String airline) {
        this.key = key;
        this.airline = airline;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}

