package com.example.samanthamorris.warboat;

public class Skin {
    int price;
    int skinNum;
    public Skin (int price, int skinNum) {
        this.price = price;
        this.skinNum = skinNum;
    }

    public int getPrice() {
        return this.price;
    }

    public int getSkinNum() {
        return this.skinNum;
    }
}
