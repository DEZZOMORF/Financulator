package com.example.financulator;

public class BuyModel {
    private final String id;
    private double quantity;
    private double price;
    private String info;
    private String currencyId;
    private String currencySymbol;
    private String purchasedFor;
    private String logo;

    public BuyModel(String id, double quantity, double price, String info, String currency, String currencySymbol, String purchasedFor, String logo) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.info = info;
        this.currencyId = currency;
        this.currencySymbol = currencySymbol;
        this.purchasedFor = purchasedFor;
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
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

    public String getPurchasedFor() {
        return purchasedFor;
    }

    public double getSum() {
        return quantity*price;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCurrencyId(String currency) {
        this.currencyId = currency;
    }

}
