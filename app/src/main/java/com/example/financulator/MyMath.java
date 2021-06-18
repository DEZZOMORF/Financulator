package com.example.financulator;

import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.List;

public interface MyMath {

    default void getTotalChange(List<BuyModel> buyList, CoinModel coin, TextView textView) {

            new Util().getCoinData(new Util.Callback() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onResponse(CoinModel usdt) {
                    double percentChange = 0;
                    double usdChange = 0;
                    for (BuyModel bm: buyList) {
                        double currentPrice = coin.getMarketData().getCurrencies().get(bm.getPurchasedFor().toLowerCase());
                        double percent =  100 * (currentPrice - bm.getPrice()) / bm.getPrice();
                        double changeInUSD = (bm.getSum()/100*percent)/usdt.getMarketData().getCurrencies().get(bm.getPurchasedFor().toLowerCase());
                        usdChange +=changeInUSD;
                    }

                    percentChange = getChangeInPercent(buyList, coin, usdChange);

                    textView.setText(String.format("%.2f", percentChange).replace("," , ".") + "% / " + String.format("%.2f", usdChange).replace("," , ".")  + " USD");

                    if (percentChange>0) {
                        textView.setTextColor(textView.getResources().getColor(R.color.accent, null));
                    } else if (percentChange<0) {
                        textView.setTextColor(textView.getResources().getColor(R.color.red, null));
                    } else {
                        textView.setTextColor(textView.getResources().getColor(R.color.secondary_text, null));
                    }
                }
            }, "tether");

    }

     default double getChangeInPercent(List<BuyModel> buyList, CoinModel coin, double usdChange) {
        double now = getTotalQuantity(buyList) * coin.getMarketData().getCurrencies().get("usd");
        double was = now + usdChange;
        return (was/now-1)*100;
    }

    default double getTotalQuantity(List<BuyModel> buyList) {
        double quantityCounter = 0;
        for (BuyModel bm: buyList) {
            quantityCounter += bm.getQuantity();
        }
        return quantityCounter;
    }
}
