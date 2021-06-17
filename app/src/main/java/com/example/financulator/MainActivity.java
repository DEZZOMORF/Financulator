package com.example.financulator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DBRequest{

    RecyclerView recyclerView;
    MainListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(android.R.id.content).getRootView();
        //String str = String.format("%.2f", 100 * (b - a) / a) + "%";

        findViewById(R.id.preloader).setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        View view = findViewById(android.R.id.content).getRootView();
        recyclerView = findViewById(R.id.recycler_view_ma);

        List<BuyModel> buyModelList = getAll(this);
        List<MainModel> mainModelList = new MainPresenter().sort(buyModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        adapter = new MainListAdapter(this, mainModelList, view);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.preloader).setVisibility(View.GONE);
    }

    public void startCoinsListActivity(View view) {
        Intent intent = new Intent(this, CoinsListActivity.class);
        startActivity(intent);
    }

}