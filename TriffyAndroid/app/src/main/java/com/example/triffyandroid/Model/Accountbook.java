package com.example.triffyandroid.Model;

public class Accountbook {
    private String date;
    private int pk;
    private String category;
    private String content;
    private double money;
    private boolean card;
    private boolean spent;
    private String currency;

    public Accountbook(String date, int pk, String category, String content, double money, boolean card, boolean spent, String currency) {
        this.date = date;
        this.pk = pk;
        this.category = category;
        this.content = content;
        this.money = money;
        this.card = card;
        this.spent = spent;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Accountbook{" +
                "date='" + date + '\'' +
                ", pk=" + pk +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", money=" + money +
                ", card=" + card +
                ", spent=" + spent +
                ", currency='" + currency + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isCard() {
        return card;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public boolean isSpent() {
        return spent;
    }

    public void setSpent(boolean spent) {
        this.spent = spent;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
