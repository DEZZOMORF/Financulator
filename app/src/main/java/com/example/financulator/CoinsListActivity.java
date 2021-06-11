package com.example.financulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CoinsListActivity extends AppCompatActivity {

    TextView tv;
    TextInputLayout til;
    ImageView ivSearch;
    ImageView ivClose;
    InputMethodManager imm;
    TextInputEditText tiet;
    RecyclerView recyclerView;
    CoinsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins_list);
        View view = findViewById(android.R.id.content).getRootView();

        tv = findViewById(R.id.text);
        til = findViewById(R.id.textinput);
        ivSearch = findViewById(R.id.search);
        ivClose = findViewById(R.id.close);
        tiet = findViewById(R.id.textinputedittext);
        recyclerView = view.findViewById(R.id.recycler_view);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        new CoinPresenter().getCoinsList(view);

        tiet.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        tiet.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                adapter = (CoinsListAdapter) recyclerView.getAdapter();
                if (adapter != null) {
                    String data = s.toString();
                    adapter.getFilter().filter(data);
                }
            }
        });

    }

    public void back(View view) {
        super.finish();
    }

    public void search(View view) {
        Runnable endAction = () -> {
            tv.setVisibility(View.GONE);
            ivSearch.setVisibility(View.GONE);
        };
        tv.animate().alpha(0.0f).withEndAction(endAction);
        ivSearch.animate().alpha(0.0f);
        til.animate().alpha(1.0f);
        ivClose.animate().alpha(1.0f);
        til.setVisibility(View.VISIBLE);
        ivClose.setVisibility(View.VISIBLE);

        til.requestFocus();
        imm.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

    public void closeSearch(View view) {
        Runnable endAction = () -> {
            til.setVisibility(View.GONE);
            ivClose.setVisibility(View.GONE);
        };
        tv.animate().alpha(1.0f);
        ivSearch.animate().alpha(1.0f);
        til.animate().alpha(0.0f).withEndAction(endAction);
        ivClose.animate().alpha(0.0f);
        tv.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.VISIBLE);

        til.getEditText().setText("");
        til.clearFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}