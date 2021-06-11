  package com.example.financulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

  public class BuyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        View view = findViewById(android.R.id.content).getRootView();

        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("id").toString();
        new CoinPresenter().getCoinData(view, id);

        List<String> currencyList = new ArrayList<>();
        currencyList.add("usd");
        currencyList.add("uah");
        Spinner spinner = findViewById(R.id.currency);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencyList);
        spinner.setAdapter(adapter);

    }

    public void back(View view) {
        super.finish();
    }
}