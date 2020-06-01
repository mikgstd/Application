package com.example.myapplication.receiving;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
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
import com.example.myapplication.partners.PartnerCursorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReceivingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int RECEIVING_LOADER = 125;
    ReceivingCursorAdapter memberCursorAdapter; // адаптер для listView
    ListView receivingLiastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving);

        // инициируем ListView
        receivingLiastView = findViewById(R.id.receivingLiastView);

        // инициируем кнопку
        FloatingActionButton floatingActionButton3 = findViewById(R.id.floatingActionButton3);
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceivingActivity.this, AddReceivingActivity.class);
                startActivity(intent);
            }
        });

        memberCursorAdapter = new ReceivingCursorAdapter(this ,null, false);
        receivingLiastView.setAdapter(memberCursorAdapter);

        receivingLiastView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReceivingActivity.this,AddReceivingActivity.class);
                Uri currentMember = ContentUris.withAppendedId(MyScladContract.ReceivingEntry.CONTENT_URI, id);
                intent.setData(currentMember);
                startActivity(intent); //запускаем activity
            }
        });

        getSupportLoaderManager().initLoader(RECEIVING_LOADER, null, this); // инициализация Loader


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                MyScladContract.ReceivingEntry._ID,
                MyScladContract.ReceivingEntry.COLUMN_NAME,
                MyScladContract.ReceivingEntry.COLUMN_GOOD_LOT

        };

        CursorLoader cursorLoader = new CursorLoader( this,
                MyScladContract.ReceivingEntry.CONTENT_URI,
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
