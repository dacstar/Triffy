package com.example.triffyandroid.Model;

public class ReserveAirline {
    private String name;
    private int price;
    private String outdeparture;
    private String outarrival;
    private String indeparture;
    private String inarrival;

    public ReserveAirline(String name, int price, String outdeparture, String outarrival, String indeparture, String inarrival) {
        this.name = name;
        this.price = price;
        this.outdeparture = outdeparture;
        this.outarrival = outarrival;
        this.indeparture = indeparture;
        this.inarrival = inarrival;
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

    public String getOutdeparture() {
        return outdeparture;
    }

    public void setOutdeparture(String outdeparture) {
        this.outdeparture = outdeparture;
    }

    public String getOutarrival() {
        return outarrival;
    }

    public void setOutarrival(String outarrival) {
        this.outarrival = outarrival;
    }

    public String getIndeparture() {
        return indeparture;
    }

    public void setIndeparture(String indeparture) {
        this.indeparture = indeparture;
    }

    public String getInarrival() {
        return inarrival;
    }

    public void setInarrival(String inarrival) {
        this.inarrival = inarrival;
    }
}
