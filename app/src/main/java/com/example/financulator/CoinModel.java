package com.example.financulator;

import com.google.gson.annotations.SerializedName;

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
    private CurrentPrice currentPrice = new CurrentPrice();

    public CurrentPrice getCurrentPrice() {
        return currentPrice;
    }
}

class CurrentPrice {
    String aed,ars,aud,bch,bdt,bhd,bmd,bnb,brl,btc,cad,chf,clp,cny,czk,dkk,dot,eos,eth,eur,gbp,hkd,huf,idr,ils,inr,jpy,krw,kwd,lkr,ltc,mmk,mxn,myr,ngn,nok,nzd,php,pkr,pln,rub,sar,sek,sgd,thb,twd,uah,usd,vef,vnd,xag,xau,xdr,xlm,xrp,yfi,zar,bits,link,sats;
    @SerializedName("try")
    String TRY;
}