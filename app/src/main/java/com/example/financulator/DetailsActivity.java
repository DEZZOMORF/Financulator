package com.example.financulator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements DBRequest, MyMath {

    String id;
    String symbol;
    ImageView logo;
    TextView name;
    TextView price;
    TextView change;
    TextView totalQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        View view = findViewById(android.R.id.content).getRootView();

        Bundle arguments = getIntent().getExtras();
        id = arguments.get("id").toString();
        symbol = arguments.get("symbol").toString().toUpperCase();

        logo = findViewById(R.id.logo);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        totalQuantity = findViewById(R.id.total_quantity);
        change = findViewById(R.id.changeTotal);

        List<BuyModel> buyList = getById(id, view.getContext());
        double quantity =  getTotalQuantity(buyList);

        new Util().getCoinData(new Util.Callback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(CoinModel coin) {
                new CoinPresenter().LoadImageFromWeb(view.getContext(), coin.getImage().getLarge(), logo);

                RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager((view.getContext())));
                DetailsAdapter adapter = new DetailsAdapter(view.getContext(), buyList, coin, view);
                recyclerView.setAdapter(adapter);

                getTotalChange(buyList, coin, change);

                totalQuantity.setText(String.format("%.6f",quantity).replace("," , ".") + " " + symbol + " / " + String.format("%.6f", coin.getMarketData().getCurrencies().get("usd")*quantity).replace("," , ".") + " USD");
                name.setText(coin.getName() + " ("+coin.getSymbol()+")");
                price.setText(String.format("%.6f", coin.getMarketData().getCurrencies().get("usd")).replace("," , ".") + " "+ symbol +"/USD");
                view.findViewById(R.id.preloader).setVisibility(View.GONE);
            }
        }, id);
    }

    public void back(View view) {
        super.finish();
    }

    public void deleteCoin(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete all purchases of this coin?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCurrencyById(id, view.getContext());
                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void addBuy(View v) {
        Intent intent = new Intent(v.getContext(), BuyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", id);
        v.getContext().startActivity(intent);
    }
}