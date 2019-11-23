package com.example.triffyandroid.Model;

public class ReserveHotel {
    private String name;
    private int price;
    private String arrival;
    private String departure;

    public ReserveHotel(String name, int price, String arrival, String departure) {
        this.name = name;
        this.price = price;
        this.arrival = arrival;
        this.departure = departure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }
}
