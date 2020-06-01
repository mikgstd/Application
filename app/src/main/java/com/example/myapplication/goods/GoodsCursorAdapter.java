package com.example.myapplication.goods;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.MyScladContract;

public class GoodsCursorAdapter extends CursorAdapter {
    public GoodsCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) { //Создаеются пустые поля для заполнения по шаблону из goods_item
        return LayoutInflater.from(context).inflate(R.layout.goods_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = view.findViewById(R.id.name);
        TextView article = view.findViewById(R.id.article);
        TextView price = view.findViewById(R.id.price);



        String names = cursor.getString(cursor.getColumnIndexOrThrow(MyScladContract.GoodsEntry.COLUMN_NAME));
        String prices = cursor.getString(cursor.getColumnIndexOrThrow(MyScladContract.GoodsEntry.COLUMN_PRICE)); //возможно нужен int
        String articles = cursor.getString(cursor.getColumnIndexOrThrow(MyScladContract.GoodsEntry.COLUMN_ARTICLE));


        name.setText("Наименование: " + names);
        article.setText("Артикул: " + articles);
        price.setText("Цена: " + prices + " руб.");


    }
}
