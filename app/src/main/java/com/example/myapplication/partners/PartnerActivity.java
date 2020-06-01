package com.example.myapplication.partners;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PartnerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PARTNER_LOADER = 124;
    PartnerCursorAdapter memberCursorAdapter; // адаптер для listView
    ListView partnersLiastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        // инициируем ListView
        partnersLiastView = findViewById(R.id.partnersLiastView);

        // инициируем кнопку
        FloatingActionButton floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartnerActivity.this, AddPartnerActivity.class);
                startActivity(intent);
            }
        });

        memberCursorAdapter = new PartnerCursorAdapter(this, null, false);
        partnersLiastView.setAdapter(memberCursorAdapter);

        partnersLiastView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PartnerActivity.this,AddPartnerActivity.class);
                Uri currentMember = ContentUris.withAppendedId(MyScladContract.PartnerEntry.CONTENT_URI, id);
                intent.setData(currentMember);
                startActivity(intent); //запускаем activity
            }
        });

        getSupportLoaderManager().initLoader(PARTNER_LOADER, null, this); // инициализация Loader
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                MyScladContract.PartnerEntry._ID,
                MyScladContract.PartnerEntry.COLUMN_NAME,
                MyScladContract.PartnerEntry.COLUMN_ADDRESS,
                MyScladContract.PartnerEntry.COLUMN_CONTACT
        };

        CursorLoader cursorLoader = new CursorLoader( this,
                MyScladContract.PartnerEntry.CONTENT_URI,
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
