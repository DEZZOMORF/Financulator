  package com.example.financulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class BuyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        View view = findViewById(android.R.id.content).getRootView();

        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("id").toString();
        new CoinPresenter().getCoinData(view, id);

    }

    public void back(View view) {
        super.finish();
    }
}