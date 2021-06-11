package com.example.financulator;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private static final String BASE_URL = "https://api.coingecko.com/api/v3/";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private APIService service = retrofit.create(APIService.class);

    public APIService getApiService() {
        return service;
    }

}

