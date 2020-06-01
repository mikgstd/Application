package com.example.myapplication.sale;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.MyScladContract;

public class SaleCursorAdapter extends CursorAdapter {
    public SaleCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.sale_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView receiving_name = view.findViewById(R.id.sale_name);
        TextView lots = view.findViewById(R.id.sale_lots);
        TextView sale_whole_price = view.findViewById(R.id.sale_whole_price);


        String comments = cursor.getString(cursor.getColumnIndexOrThrow(MyScladContract.SaleEntry.COLUMN_NAME));
        String lot = cursor.getString(cursor.getColumnIndexOrThrow(MyScladContract.SaleEntry.COLUMN_GOOD_LOT));
        String sale_whole_prices = cursor.getString(cursor.getColumnIndexOrThrow(MyScladContract.SaleEntry.COLUMN_GOOD_WHOLE_PRICE));


        receiving_name.setText("Наименование: " + comments);
        lots.setText("Количество: " + lot + " шт.");
        sale_whole_price.setText("Сумма: " + sale_whole_prices + " руб.");
    }
}
