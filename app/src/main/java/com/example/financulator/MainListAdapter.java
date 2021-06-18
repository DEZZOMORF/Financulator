package com.example.financulator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<MainModel> coins = new ArrayList<MainModel>();
    private final RecyclerView recyclerView;
    private View view;

    MainListAdapter(Context context, List<MainModel> coins, View view) {
        this.view = view;
        this.coins.addAll(coins);
        this.inflater = LayoutInflater.from(context);
        this.recyclerView = view.findViewById(R.id.recycler_view_ma);
    }
    @Override
    public MainListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_bought_coin2, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            MainModel coin = coins.get(itemPosition);
            String item = coin.getId();
            String symbol = coin.getSymbol();
            Intent intent = new Intent(v.getContext(),  DetailsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", item);
            intent.putExtra("symbol", symbol);
            v.getContext().startActivity(intent);
        }
    };

    @Override
    public void onBindViewHolder(MainListAdapter.ViewHolder holder, int position) {
        MainModel coin = coins.get(position);
        Glide
                .with(view.getContext())
                .load(coin.getLogo())
                .into(holder.imageView);
        holder.symbolView.setText(coin.getSymbol());
        holder.buyCountView.setText(coin.getBuyIdList().size() + " purchases");
        holder.totalQuantity.setText(coin.getTotalQuantity() + " coins");
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView symbolView, buyCountView, totalQuantity;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.logo);
            symbolView = (TextView) view.findViewById(R.id.symbol);
            buyCountView = (TextView) view.findViewById(R.id.buy_count);
            totalQuantity = (TextView) view.findViewById(R.id.total_quantity);
        }
    }
}
