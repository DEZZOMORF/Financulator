package com.example.financulator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinPresenter {

    public void getCoinData(View view, String id) {
        Call<CoinModel> call = new HttpClient().getApiService().getCoinById(id);
        call.enqueue(new Callback<CoinModel>() {
            @Override
            public void onResponse(Call<CoinModel> call, Response<CoinModel> response) {
                view.findViewById(R.id.preloader).setVisibility(View.GONE);
                TextView name = view.findViewById(R.id.name);
                TextView symbol = view.findViewById(R.id.symbol);
                TextView price = view.findViewById(R.id.price);
                ImageView logo1 = view.findViewById(R.id.logo1);
                ImageView logo2 = view.findViewById(R.id.logo2);
                ImageView backgroundLogo = view.findViewById(R.id.background_logo);
                String url = response.body().getImage().getLarge();

                LoadImageFromWeb(view.getContext(), url, logo1);
                LoadImageFromWeb(view.getContext(), url, logo2);
                LoadImageFromWeb(view.getContext(), url, backgroundLogo);
                name.setText(response.body().getName());
                symbol.setText(response.body().getSymbol().toUpperCase());
                price.setText(response.body().getMarketData().getCurrentPrice().usd + " USD/" + response.body().getSymbol().toUpperCase());
            }

            @Override
            public void onFailure(Call<CoinModel> call, Throwable t) {
                Toast.makeText(view.getContext(),"Server request error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCoinsList (View view) {
        Call<List<CoinModel>> call = new HttpClient().getApiService().getCoinsList();
        call.enqueue(new Callback<List<CoinModel>>() {
            @Override
            public void onResponse(Call<List<CoinModel>> call, Response<List<CoinModel>> response) {
                onSuccess(view, response);
            }

            @Override
            public void onFailure(Call<List<CoinModel>> call, Throwable t) {

            }
        });
    }

    private void onSuccess(View view, Response<List<CoinModel>> response) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager((view.getContext())));
        CoinsListAdapter adapter = new CoinsListAdapter(view.getContext(), response.body(), view);
        recyclerView.setAdapter(adapter);
        view.findViewById(R.id.preloader).setVisibility(View.GONE);
    }

    public void LoadImageFromWeb(Context context, String url, ImageView iv) {
        Glide
                .with(context)
                .load(url)
                .into(iv);
    }
}
