package com.example.financulator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DBRequest, MyMath {

    RecyclerView recyclerView;
    TextView usdBalance;
    TextView btcBalance;
    TextView change;
    MainListAdapter adapter;
    List<MainModel> mainList;
    List<BuyModel> allBuyList;
    double usdBalanceCounter = 0.0;
    double btcBalanceCounter = 0.0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.preloader).setVisibility(View.VISIBLE);
        View view = findViewById(android.R.id.content).getRootView();
        recyclerView = findViewById(R.id.recycler_view_ma);
        usdBalance = findViewById(R.id.usd_balance);
        btcBalance = findViewById(R.id.btc_balance);
        change = findViewById(R.id.change);

        allBuyList = getAll(this);
        mainList = new MainPresenter().sort(allBuyList);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        adapter = new MainListAdapter(this, mainList, view);
        recyclerView.setAdapter(adapter);

        updData(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        View view = findViewById(android.R.id.content).getRootView();

        allBuyList = getAll(this);
        mainList = new MainPresenter().sort(allBuyList);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        adapter = new MainListAdapter(this, mainList, view);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.preloader).setVisibility(View.GONE);
    }

    public void startCoinsListActivity(View view) {
        Intent intent = new Intent(this, CoinsListActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setData(CoinModel coin) {
        allBuyList = getAll(this);
        mainList = new MainPresenter().sort(allBuyList);
        for (MainModel obj : mainList) {
            if (obj.getId().equals(coin.getId())) {
                double usd_sum = obj.getTotalQuantity() * coin.getMarketData().getCurrencies().get("usd");
                double btc_sum = obj.getTotalQuantity() * coin.getMarketData().getCurrencies().get("btc");
                usdBalanceCounter += usd_sum;
                btcBalanceCounter += btc_sum;
            }
        }
        usdBalance.setText("$ " + String.format("%.2f", usdBalanceCounter));
        btcBalance.setText(String.format("%.6f", btcBalanceCounter) + " BTC");
    }

    public void updData (View view) {
        usdBalanceCounter = 0.0;
        btcBalanceCounter = 0.0;
        for (MainModel obj : mainList) {
            new MainPresenter().getCoinDataById(obj.getId(), MainActivity.this);
        }
    }
}