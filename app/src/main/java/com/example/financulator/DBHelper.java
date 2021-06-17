package com.example.financulator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "financulatorDB";

    public static final String TABLE_BUY = "purchases";
    public static final String KEY_ID = "_id";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_PRICE = "price";
    public static final String KEY_INFO = "info";
    public static final String KEY_CURRENCY_ID = "currencyId";
    public static final String KEY_CURRENCY_SYMBOL = "currencySymbol";
    public static final String KEY_PURCHASED_FOR = "purchasedFor";
    public static final String KEY_LOGO = "logo";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_BUY + "(" +
                KEY_ID + " integer primary key," +
                KEY_CURRENCY_ID + " text," +
                KEY_CURRENCY_SYMBOL + " text," +
                KEY_QUANTITY + " real," +
                KEY_PRICE + " real," +
                KEY_INFO + " text," +
                KEY_PURCHASED_FOR + " text," +
                KEY_LOGO + " text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_BUY);
        onCreate(db);
    }
}
