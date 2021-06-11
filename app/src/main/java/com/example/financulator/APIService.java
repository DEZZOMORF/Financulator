package com.example.financulator;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("coins/{id}")
    Call<CoinModel> getCoinById(@Path("id") String id);
    @GET("coins/list")
    Call<List<CoinModel>> getCoinsList();

}
