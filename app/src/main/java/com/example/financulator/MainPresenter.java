package com.example.financulator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {

    List<MainModel> mainModelList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<MainModel> sort(List<BuyModel> buyModelList) {
        mainModelList = new ArrayList<MainModel>();
        for (BuyModel buy: buyModelList) {
           boolean b = mainModelList.stream().anyMatch(obj -> obj.getId().equals(buy.getCurrencyId()));
           if(b){
               mainModelList.stream().filter(obj -> obj.getId().equals(buy.getCurrencyId())).forEach(obj -> {
                   obj.addBuyId(buy.getId());
                   obj.addToTotalQuantity(buy.getQuantity());
               });
           } else {
               mainModelList.add(new MainModel(buy.getCurrencyId(), buy.getId(), buy.getCurrencySymbol(), buy.getQuantity(), buy.getLogo()));
           }
        }

        return mainModelList;
    }
}

class MainModel {

    private String id;
    private String symbol;
    private String logo;
    private double totalQuantity = 0;
    private List<String> buyId = new ArrayList<String>();

    public MainModel(String id, String buyId, String symbol, double totalQuantity, String logo) {
        this.id = id;
        this.symbol = symbol;
        this.buyId.add(buyId);
        this.totalQuantity = totalQuantity;
        this.logo = logo;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void addToTotalQuantity(double quantity) {
        this.totalQuantity += quantity;
    }

    public void addBuyId(String buyId) {
        this.buyId.add(buyId);
    }

    public List<String> getBuyIdList() {
        return buyId;
    }

    public String getId() {
        return id;
    }
}
