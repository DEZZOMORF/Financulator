package com.example.financulator;

import retrofit2.Call;
import retrofit2.Response;

public class Util {
    public interface Callback {
        void onResponse(CoinModel coin);
    }

    private CoinModel userCoin;

    public CoinModel getCoin() {
        return userCoin;
    }

    public void getCoinData(final Callback callback, String id) {
        Call<CoinModel> call = new HttpClient().getApiService().getCoinById(id);
        call.enqueue(new retrofit2.Callback<CoinModel>() {
            @Override
            public void onResponse(Call<CoinModel> call, Response<CoinModel> response) {
                userCoin = response.body();
                callback.onResponse(userCoin);
            }

            @Override
            public void onFailure(Call<CoinModel> call, Throwable t) {
            }
        });
    }
}
