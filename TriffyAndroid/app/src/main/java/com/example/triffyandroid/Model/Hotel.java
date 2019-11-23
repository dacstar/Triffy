package com.example.triffyandroid.Model;

public class Hotel {
    private String photo_url;
    private double min_price;
    private double review_score;
    private String hotel_name;

    public Hotel(String photo_url, double min_price, double review_score, String hotel_name) {
        this.photo_url = photo_url;
        this.min_price = min_price;
        this.review_score = review_score;
        this.hotel_name = hotel_name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public double getMin_price() {
        return min_price;
    }

    public void setMin_price(double min_price) {
        this.min_price = min_price;
    }

    public double getReview_score() {
        return review_score;
    }

    public void setReview_score(double review_score) {
        this.review_score = review_score;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }
}
