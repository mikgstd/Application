package com.example.myapplication.sale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.data.MyScladContract;
import com.example.myapplication.receiving.ReceivingCursorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.myapplication.R.*;

public class SaleActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SALE_LOADER = 135;
    SaleCursorAdapter memberCursorAdapter; // адаптер для listView
    ListView saleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_sale);

        // инициируем ListView
        saleListView = findViewById(id.saleListView);

        FloatingActionButton floatingActionButtonAddSale = findViewById(id.floatingActionButtonAddSale);
        floatingActionButtonAddSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleActivity.this, AddSaleActivity.class);
                startActivity(intent);
            }
        });

        memberCursorAdapter = new SaleCursorAdapter(this ,null, false);
        saleListView.setAdapter(memberCursorAdapter);

        saleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SaleActivity.this,AddSaleActivity.class);
                Uri currentMember = ContentUris.withAppendedId(MyScladContract.SaleEntry.CONTENT_URI, id);
                intent.setData(currentMember);
                startActivity(intent); //запускаем activity
            }
        });

        getSupportLoaderManager().initLoader(SALE_LOADER, null, this); // инициализация Loader

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                MyScladContract.SaleEntry._ID,
                MyScladContract.SaleEntry.COLUMN_NAME,
                MyScladContract.SaleEntry.COLUMN_GOOD_LOT,
                MyScladContract.SaleEntry.COLUMN_GOOD_WHOLE_PRICE

        };

        CursorLoader cursorLoader = new CursorLoader( this,
                MyScladContract.SaleEntry.CONTENT_URI,
                projection, // столбцы которые будут выводится
                null,
                null,
                null
        );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        memberCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        memberCursorAdapter.swapCursor(null);
    }
}
