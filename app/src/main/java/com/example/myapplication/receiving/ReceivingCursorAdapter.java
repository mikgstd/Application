package com.example.myapplication.receiving;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.MyScladContract;

public class ReceivingCursorAdapter extends CursorAdapter {


    public ReceivingCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.receiving_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView receiving_name = view.findViewById(R.id.receivingname);
        TextView lots = view.findViewById(R.id.lots);


        String comments = cursor.getString(cursor.getColumnIndexOrThrow(MyScladContract.ReceivingEntry.COLUMN_NAME));
        String lot = cursor.getString(cursor.getColumnIndexOrThrow(MyScladContract.ReceivingEntry.COLUMN_GOOD_LOT));


        receiving_name.setText("Наименование: " + comments);
        lots.setText("Количество: " + lot + " шт.");
    }
}
