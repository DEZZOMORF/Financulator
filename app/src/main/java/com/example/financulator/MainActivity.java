package com.example.financulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(android.R.id.content).getRootView();
        //String str = String.format("%.2f", 100 * (b - a) / a) + "%";

        //new CoinPresenter().getCoinsList(view);
        //new CoinPresenter().getCoinData(view, "bitcoin");

        //TODO Toolbar c кнопкой создающей новое активити со списком
    }

    public void startCoinsListActivity(View view) {
        Intent intent = new Intent(this, CoinsListActivity.class);
        startActivity(intent);
    }
}