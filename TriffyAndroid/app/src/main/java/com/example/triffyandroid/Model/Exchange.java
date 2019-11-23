package com.example.triffyandroid.Model;

public class Exchange {
    private double buy;
    private double sell;
    private String currency;
    private String now_date;
    private String now_time;

    public Exchange(double buy, double sell, String currency, String now_date, String now_time) {
        this.buy = buy;
        this.sell = sell;
        this.currency = currency;
        this.now_date = now_date;
        this.now_time = now_time;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNow_date() {
        return now_date;
    }

    public void setNow_date(String now_date) {
        this.now_date = now_date;
    }

    public String getNow_time() {
        return now_time;
    }

    public void setNow_time(String now_time) {
        this.now_time = now_time;
    }
}
