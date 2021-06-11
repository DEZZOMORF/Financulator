package com.example.financulator;

public class BuyModel {
    private double quantity;
    private double price;
    private String info;
    private String coinId;

    public BuyModel(double quantity, double price, String info, String coinId) {
        this.quantity = quantity;
        this.price = price;
        this.info = info;
        this.coinId = coinId;
    }

    public String getInfo() {
        return info;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getCoinId() {
        return coinId;
    }

    public double getSum() {
        return quantity*price;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
