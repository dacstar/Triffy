package com.example.triffyandroid.Model;

public class Airline {
    private double price;
    private String name;
    private String img;
    private String out;
    private String in;

    public Airline(double price, String name, String img, String out, String in) {
        this.price = price;
        this.name = name;
        this.img = img;
        this.out = out;
        this.in = in;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }
}
