package com.example.financulator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public interface DBRequest {

    default List<BuyModel> getAll(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        List<BuyModel> coinList = new ArrayList<BuyModel>();

        Cursor cursor = database.query(DBHelper.TABLE_BUY, null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            int _idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int currencyIdIndex = cursor.getColumnIndex(DBHelper.KEY_CURRENCY_ID);
            int currencyNameIndex = cursor.getColumnIndex(DBHelper.KEY_CURRENCY_SYMBOL);
            int logoIndex = cursor.getColumnIndex(DBHelper.KEY_LOGO);
            int priceIndex = cursor.getColumnIndex(DBHelper.KEY_PRICE);
            int quantityIndex = cursor.getColumnIndex(DBHelper.KEY_QUANTITY);
            int infoIndex = cursor.getColumnIndex(DBHelper.KEY_INFO);
            int purchasedForIndex = cursor.getColumnIndex(DBHelper.KEY_PURCHASED_FOR);
            do {
                BuyModel buyModel = new BuyModel(
                        cursor.getString(_idIndex),
                        cursor.getDouble(quantityIndex),
                        cursor.getDouble(priceIndex),
                        cursor.getString(infoIndex),
                        cursor.getString(currencyIdIndex),
                        cursor.getString(currencyNameIndex),
                        cursor.getString(purchasedForIndex),
                        cursor.getString(logoIndex)
                        );
                coinList.add(buyModel);
            } while (cursor.moveToNext());
        } else {
            Log.d ("mLog", "0 rows");
        }
        cursor.close();
        return coinList;
    }

    default List<BuyModel> getById(String id, Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        List<BuyModel> coinList = new ArrayList<BuyModel>();

        Cursor cursor = database.query(DBHelper.TABLE_BUY, null, DBHelper.KEY_CURRENCY_ID + " = ?", new String[] {id}, null, null, null);
        if(cursor.moveToFirst()) {
            int _idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int currencyIdIndex = cursor.getColumnIndex(DBHelper.KEY_CURRENCY_ID);
            int currencyNameIndex = cursor.getColumnIndex(DBHelper.KEY_CURRENCY_SYMBOL);
            int logoIndex = cursor.getColumnIndex(DBHelper.KEY_LOGO);
            int priceIndex = cursor.getColumnIndex(DBHelper.KEY_PRICE);
            int quantityIndex = cursor.getColumnIndex(DBHelper.KEY_QUANTITY);
            int infoIndex = cursor.getColumnIndex(DBHelper.KEY_INFO);
            int purchasedForIndex = cursor.getColumnIndex(DBHelper.KEY_PURCHASED_FOR);
            do {
                BuyModel buyModel = new BuyModel(
                        cursor.getString(_idIndex),
                        cursor.getDouble(quantityIndex),
                        cursor.getDouble(priceIndex),
                        cursor.getString(infoIndex),
                        cursor.getString(currencyIdIndex),
                        cursor.getString(currencyNameIndex),
                        cursor.getString(purchasedForIndex),
                        cursor.getString(logoIndex)
                );
                coinList.add(buyModel);
            } while (cursor.moveToNext());
        } else {
            Log.d ("mLog", "0 rows");
        }
        cursor.close();
        return coinList;
    }

    default void deleteBuyById(String id, Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(DBHelper.TABLE_BUY, DBHelper.KEY_ID + " = ?", new String[] {id});
    }


    default void deleteCurrencyById(String id, Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(DBHelper.TABLE_BUY, DBHelper.KEY_CURRENCY_ID + " = ?", new String[] {id});
    }
}
