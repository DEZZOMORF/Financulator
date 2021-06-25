package com.example.financulator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void getCoinDataById(String id, MainActivity activity) {
        Call<CoinModel> call = new HttpClient().getApiService().getCoinById(id);
        call.enqueue(new Callback<CoinModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<CoinModel> call, Response<CoinModel> response) {
                activity.setData(response.body());
            }

            @Override
            public void onFailure(Call<CoinModel> call, Throwable t) {

            }
        });
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
