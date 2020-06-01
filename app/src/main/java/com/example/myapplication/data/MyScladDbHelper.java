package com.example.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyScladDbHelper extends SQLiteOpenHelper {

    public MyScladDbHelper(Context context) {
        super(context, MyScladContract.DATABASE_NAME, null, MyScladContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            String CREATE_GOODS_TABLE = "CREATE TABLE " + MyScladContract.GoodsEntry.TABLE_NAME + "(" + MyScladContract.GoodsEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                    + MyScladContract.GoodsEntry.COLUMN_NAME + " TEXT,"
                    + MyScladContract.GoodsEntry.COLUMN_ARTICLE + " TEXT,"
                    + MyScladContract.GoodsEntry.COLUMN_PRICE + " TEXT" + ")";
            db.execSQL(CREATE_GOODS_TABLE);

            String CREATE_PARTNER_TABLE = "CREATE TABLE " + MyScladContract.PartnerEntry.TABLE_NAME + "(" + MyScladContract.PartnerEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                    + MyScladContract.PartnerEntry.COLUMN_NAME + " TEXT,"
                    + MyScladContract.PartnerEntry.COLUMN_ADDRESS + " TEXT,"
                    + MyScladContract.PartnerEntry.COLUMN_CONTACT + " TEXT" + ")";
            db.execSQL(CREATE_PARTNER_TABLE);

        String CREATE_RECEIVING_TABLE = "CREATE TABLE " + MyScladContract.ReceivingEntry.TABLE_NAME + "(" + MyScladContract.ReceivingEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + MyScladContract.ReceivingEntry.COLUMN_COMMENT + " TEXT,"
                + MyScladContract.ReceivingEntry.COLUMN_NAME + " TEXT,"
                + MyScladContract.ReceivingEntry.COLUMN_GOOD_LOT + " INTEGER,"
                + MyScladContract.ReceivingEntry.COLUMN_GOOD + " INTEGER,"
                + MyScladContract.ReceivingEntry.COLUMN_PARENT + " INTEGER" + ")";
        db.execSQL(CREATE_RECEIVING_TABLE);

        String CREATE_SALE_TABLE = "CREATE TABLE " + MyScladContract.SaleEntry.TABLE_NAME + "(" + MyScladContract.SaleEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + MyScladContract.SaleEntry.COLUMN_NAME + " TEXT,"
                + MyScladContract.SaleEntry.COLUMN_GOOD_LOT + " INTEGER,"
                + MyScladContract.SaleEntry.COLUMN_GOOD + " INTEGER,"
                + MyScladContract.SaleEntry.COLUMN_GOOD_WHOLE_PRICE + " INTEGER,"
                + MyScladContract.SaleEntry.COLUMN_PARENT + " INTEGER" + ")";
        db.execSQL(CREATE_SALE_TABLE);

        String CREATE_SCLAD_TABLE = "CREATE TABLE " + MyScladContract.ScladEntry.TABLE_NAME + "(" + MyScladContract.ScladEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + MyScladContract.ScladEntry.COLUMN_GOOD_LOT + " INTEGER" + ")";
        db.execSQL(CREATE_SCLAD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Вызывается при необходимоси обновить базу данных
        db.execSQL("DROP TABLE IF EXISTS " + MyScladContract.DATABASE_NAME); // удаляем старую базу данных
        onCreate(db); // вызываем onCreate и создаем заново (новую)
    }
}
