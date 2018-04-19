package com.example.samanthamorris.warboat;

public class Skin {
    private int price;
    private int skinNum;
    private String name;

    public Skin (int price, int skinNum) {
        this.price = price;
        this.skinNum = skinNum;
        //this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getSkinNum() {
        return this.skinNum;
    }

    public String getName() { return name; }
}
