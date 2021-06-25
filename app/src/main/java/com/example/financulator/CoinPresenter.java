package com.example.financulator;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinPresenter {

    public void getCoinDataForBuy(View view, String id) {
        Call<CoinModel> call = new HttpClient().getApiService().getCoinById(id);
        call.enqueue(new Callback<CoinModel>() {
            @Override
            public void onResponse(Call<CoinModel> call, Response<CoinModel> response) {
                view.findViewById(R.id.preloader).setVisibility(View.GONE);
                TextView name = view.findViewById(R.id.name);
                TextView symbol = view.findViewById(R.id.symbol);
                TextView price = view.findViewById(R.id.price);
                TextView currentCurrency = view.findViewById(R.id.current_currency);
                ImageView logo1 = view.findViewById(R.id.logo1);
                ImageView logo2 = view.findViewById(R.id.logo2);
                ImageView backgroundLogo = view.findViewById(R.id.background_logo);
                TextView logoBuffer = view.findViewById(R.id.logo_buffer);
                Spinner spinner = view.findViewById(R.id.currency);
                TextInputEditText priceInput = view.findViewById(R.id.price_input);

                String url = response.body().getImage().getLarge();
                logoBuffer.setText(url);
                LoadImageFromWeb(view.getContext(), url, logo1);
                LoadImageFromWeb(view.getContext(), url, logo2);
                LoadImageFromWeb(view.getContext(), url, backgroundLogo);
                name.setText(response.body().getName());
                symbol.setText(response.body().getSymbol().toUpperCase());

                List<String> currencyList = new ArrayList<>();
                Map<String, Double> currencies =  response.body().getMarketData().getCurrencies();
                Set<String> keys = currencies.keySet();
                for (String k: keys) { currencyList.add(k.toUpperCase()); }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, currencyList);
                spinner.setAdapter(adapter);
                spinner.setSelection(adapter.getPosition("USD"));

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        currentCurrency.setText(parent.getItemAtPosition(position).toString());
                        Double s = currencies.get(parent.getItemAtPosition(position).toString().toLowerCase());
                        priceInput.setText(String.format("%.9f", s).replace("," , "."));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                price.setText(String.format("%.9f", currencies.get("usd")).replace("," , ".") + " " + response.body().getSymbol().toUpperCase() + "/USD");
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
