package com.example.financulator;

public class BuyModel {
    private double quantity;
    private double price;
    private String info;

    public BuyModel(double quantity, double price, String info) {
        this.quantity = quantity;
        this.price = price;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getSum() {
        return quantity*price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
