package com.example.myapplication.partners;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.MyScladContract;

public class PartnerCursorAdapter extends CursorAdapter {
    public PartnerCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.partner_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView namePartner = view.findViewById(R.id.namePartner);
        TextView address = view.findViewById(R.id.address);
        TextView contact = view.findViewById(R.id.lots);

        String namePartners = cursor.getString(cursor.getColumnIndexOrThrow(MyScladContract.PartnerEntry.COLUMN_NAME));
        String addresses = cursor.getString(cursor.getColumnIndexOrThrow(MyScladContract.PartnerEntry.COLUMN_ADDRESS)); //возможно нужен int
        String contacts = cursor.getString(cursor.getColumnIndexOrThrow(MyScladContract.PartnerEntry.COLUMN_CONTACT));

        namePartner.setText("Наименование: " + namePartners);
        address.setText("Адрес: " + addresses);
        contact.setText("Контакт: " + contacts);
    }
}
