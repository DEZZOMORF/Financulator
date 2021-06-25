package com.example.financulator;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class CoinsListAdapter extends RecyclerView.Adapter<CoinsListAdapter.ViewHolder> implements Filterable {

    private final LayoutInflater inflater;
    private final RecyclerView recyclerView;
    private final List<CoinModel> coins = new ArrayList<CoinModel>();
    private final List<CoinModel> filteredCoins = new ArrayList<CoinModel>();
    private final View view;

    CoinsListAdapter(Context context, List<CoinModel> coins, View view) {
        this.filteredCoins.addAll(coins);
        this.coins.addAll(coins);
        this.inflater = LayoutInflater.from(context);
        this.recyclerView = view.findViewById(R.id.recycler_view);
        this.view = view;
    }
    @Override
    public CoinsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            String item = filteredCoins.get(itemPosition).getId();
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            Intent intent = new Intent(v.getContext(), BuyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", item);
            v.getContext().startActivity(intent);
        }
    };

    @Override
    public void onBindViewHolder(CoinsListAdapter.ViewHolder holder, int position) {
        CoinModel coin = filteredCoins.get(position);

        /*Call<CoinModel> call = new HttpClient().getApiService().getCoinById(coin.getId());
        call.enqueue(new Callback<CoinModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<CoinModel> call, Response<CoinModel> response) {
                new CoinPresenter().LoadImageFromWeb(view.getContext(), response.body().getImage().getLarge(), holder.imageView);
            }

            @Override
            public void onFailure(Call<CoinModel> call, Throwable t) {

            }
        });*/

        holder.nameView.setText(coin.getName());
        holder.symbolView.setText(coin.getSymbol());
    }

    @Override
    public int getItemCount() {
        return filteredCoins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, symbolView;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.logo);
            nameView = (TextView) view.findViewById(R.id.name);
            symbolView = (TextView) view.findViewById(R.id.symbol);
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CoinModel> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()) {
                filteredList.addAll(coins);
            } else {
                for (CoinModel coin: coins) {
                    if(coin.getName().toLowerCase().contains(constraint.toString().toLowerCase()) || coin.getSymbol().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(coin);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredCoins.clear();
                    filteredCoins.addAll((Collection<? extends CoinModel>) results.values);
                    notifyDataSetChanged();
        }
    };
}
