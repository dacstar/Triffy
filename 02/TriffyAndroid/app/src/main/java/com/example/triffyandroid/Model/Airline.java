package com.example.triffyandroid.Model;

public class Airline {
    private String name;
    private String img;
    private Out out;
    private In in;

    public Airline(String name, String img, Out out, In in) {
        this.name = name;
        this.img = img;
        this.out = out;
        this.in = in;
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

    public Out getOut() {
        return out;
    }

    public void setOut(Out out) {
        this.out = out;
    }

    public In getIn() {
        return in;
    }

    public void setIn(In in) {
        this.in = in;
    }
}

class Out{
    private String departure;
    private String arrival;

    public Out(String departure, String arrival) {
        this.departure = departure;
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }
}

class In{
    private String departure;
    private String arrival;

    public In(String departure, String arrival) {
        this.departure = departure;
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }
}