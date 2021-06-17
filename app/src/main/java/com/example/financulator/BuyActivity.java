  package com.example.financulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

  public class BuyActivity extends AppCompatActivity {
      String id;
      TextView totalCost;
      TextView symbol;
      TextView logoBuffer;
      TextInputEditText info;
      TextInputEditText quantity;
      TextInputEditText price;
      Spinner spinner;
      Button save;
      DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        View view = findViewById(android.R.id.content).getRootView();

        totalCost = findViewById(R.id.total_cost);
        symbol = findViewById(R.id.symbol);
        info = findViewById(R.id.info_input);
        quantity = findViewById(R.id.quantity_input);
        price = findViewById(R.id.price_input);
        spinner = findViewById(R.id.currency);
        save = findViewById(R.id.save);
        logoBuffer = findViewById(R.id.logo_buffer);

        quantity.addTextChangedListener(inputTextWatcher);
        price.addTextChangedListener(inputTextWatcher);

        Bundle arguments = getIntent().getExtras();
        id = arguments.get("id").toString();
        new CoinPresenter().getCoinDataForBuy(view, id);
        dbHelper = new DBHelper(this);

    }
    public void back(View view) {
        super.finish();
    }

    TextWatcher inputTextWatcher = new TextWatcher() {
          public void afterTextChanged(Editable s) {
              if (!price.getText().toString().matches("") && !quantity.getText().toString().matches("")) {
                  try {
                      double p = Double.parseDouble(price.getText().toString());
                      double q = Double.parseDouble(quantity.getText().toString());
                      totalCost.setText("Total cost: " + String.format("%.9f", p * q));
                      save.setEnabled(true);
                  } catch (Exception e){
                      totalCost.setText("Wrong number");
                      save.setEnabled(false);
                  }
              } else {
                  totalCost.setText("Total cost: ~");
                  save.setEnabled(false);
              }
          }
          public void beforeTextChanged(CharSequence s, int start, int count, int after){
          }
          public void onTextChanged(CharSequence s, int start, int before, int count) {
          }
      };

    public void saveBuy(View view) {
        BuyModel buy = new BuyModel(id, Double.parseDouble(String.valueOf(quantity.getText())), Double.parseDouble(String.valueOf(price.getText())), info.getText().toString(), id, symbol.getText().toString(), spinner.getSelectedItem().toString(), logoBuffer.getText().toString());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_CURRENCY_ID, buy.getCurrencyId());
        contentValues.put(DBHelper.KEY_CURRENCY_SYMBOL, buy.getCurrencySymbol());
        contentValues.put(DBHelper.KEY_PRICE, buy.getPrice());
        contentValues.put(DBHelper.KEY_QUANTITY, buy.getQuantity());
        contentValues.put(DBHelper.KEY_INFO, buy.getInfo());
        contentValues.put(DBHelper.KEY_PURCHASED_FOR, buy.getPurchasedFor());
        contentValues.put(DBHelper.KEY_LOGO, buy.getLogo());
        database.insert(DBHelper.TABLE_BUY, null, contentValues);

        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.finish();
    }
  }
