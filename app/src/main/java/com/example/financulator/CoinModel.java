package com.example.financulator;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class CoinModel {

    private String name, symbol, id = null;
    private Image image = null;
    @SerializedName("market_data")
    private MarketData marketData = null;

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public MarketData getMarketData() {
        return marketData;
    }
}

class Image {
    private String thumb, small, large;

    public String getThumb() {
        return thumb;
    }

    public String getSmall() {
        return small;
    }

    public String getLarge() {
        return large;
    }
}

class MarketData {

    @SerializedName("current_price")
    private Map<String, Double> currencies = new HashMap<String, Double>();

    public Map<String, Double> getCurrencies() {
        return currencies;
    }
}
