package com.example.triffyandroid.Model;

public class Balance {
    private String account;
    private String name;
    private double interest;
    private int goal_amount;
    private int now_amount;
    private String start_date;
    private String end_date;
    private double achieve;

    public Balance(String account, String name, double interest, int goal_amount, int now_amount, String start_date, String end_date, double achieve) {
        this.account = account;
        this.name = name;
        this.interest = interest;
        this.goal_amount = goal_amount;
        this.now_amount = now_amount;
        this.start_date = start_date;
        this.end_date = end_date;
        this.achieve = achieve;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getGoal_amount() {
        return goal_amount;
    }

    public void setGoal_amount(int goal_amount) {
        this.goal_amount = goal_amount;
    }

    public int getNow_amount() {
        return now_amount;
    }

    public void setNow_amount(int now_amount) {
        this.now_amount = now_amount;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public double getAchieve() {
        return achieve;
    }

    public void setAchieve(double achieve) {
        this.achieve = achieve;
    }
}
