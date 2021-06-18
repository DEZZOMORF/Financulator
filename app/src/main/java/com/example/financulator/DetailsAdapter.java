package com.example.financulator;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> implements DBRequest, MyMath {

    private final LayoutInflater inflater;
    private final List<BuyModel> buyList = new ArrayList<BuyModel>();
    private final RecyclerView recyclerView;
    private final CoinModel coinData;
    private View view;

    DetailsAdapter(Context context, List<BuyModel> coins, CoinModel coinData, View view) {
        this.coinData = coinData;
        this.view = view;
        this.buyList.addAll(coins);
        this.inflater = LayoutInflater.from(context);
        this.recyclerView = view.findViewById(R.id.recycler_view);
    }
    @Override
    public DetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_bought_coin3, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            BuyModel buy = buyList.get(itemPosition);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(DetailsAdapter.ViewHolder holder, int position) {
        BuyModel buy = buyList.get(position);

        holder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                deleteBuyById(buy.getId(), holder.buttonView.getContext());
                buyList.remove(position);
                recyclerView.getAdapter().notifyDataSetChanged();
                
                TextView change = view.findViewById(R.id.changeTotal);
                getTotalChange(buyList, coinData, change);

                TextView totalQuantity = view.findViewById(R.id.total_quantity);
                double quantity =  getTotalQuantity(buyList);
                totalQuantity.setText(String.format("%.6f",quantity).replace("," , ".") + " " + buy.getCurrencySymbol() + " / " + String.format("%.6f", coinData.getMarketData().getCurrencies().get("usd")*quantity).replace("," , ".") + " USD");
            }
        });
        holder.numberView.setText((position+1) +".");
        holder.infoView.setText(buy.getInfo());
        holder.quantityView.setText(String.format("%.6f", buy.getQuantity()).replace("," , ".") + " " + buy.getCurrencySymbol().toUpperCase());
        holder.summaView.setText(String.format("%.6f", buy.getSum()).replace("," , ".") + " " + buy.getPurchasedFor().toUpperCase());
        holder.purchasePriceView.setText(String.format("%.6f", buy.getPrice()).replace("," , ".") + " " + buy.getPurchasedFor());

        double currentPrice = coinData.getMarketData().getCurrencies().get(buy.getPurchasedFor().toLowerCase());
        double buyPrice = buy.getPrice();
        double percent =  100 * (currentPrice - buyPrice) / buyPrice;
        double priceChange = currentPrice - buyPrice;
        holder.profitView.setText(String.format("%.2f", percent).replace("," , ".")+"% / " + String.format("%.6f",priceChange * buy.getQuantity()).replace("," , ".")  + " " + buy.getPurchasedFor());
        if (percent>0) {
            holder.profitView.setTextColor(view.getResources().getColor(R.color.accent, null));
        } else if (percent<0) {
            holder.profitView.setTextColor(view.getResources().getColor(R.color.red, null));
        } else {
            holder.profitView.setTextColor(view.getResources().getColor(R.color.secondary_text, null));
        }
        if(position % 2 != 0) holder.lineView.setRotation(0);

    }

    @Override
    public int getItemCount() {
        return buyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView buttonView;
        final TextView  quantityView, infoView, summaView, purchasePriceView, profitView, numberView;
        final View lineView;
        ViewHolder(View view){
            super(view);
            quantityView = (TextView) view.findViewById(R.id.quantity);
            infoView = (TextView) view.findViewById(R.id.info);
            summaView = (TextView) view.findViewById(R.id.summa);
            purchasePriceView = (TextView) view.findViewById(R.id.purchase_price);
            profitView = (TextView) view.findViewById(R.id.profit);
            numberView = (TextView) view.findViewById(R.id.number);
            buttonView = (ImageView) view.findViewById(R.id.deleteBuy);
            lineView = (View) view.findViewById(R.id.view2);
        }
    }
}
