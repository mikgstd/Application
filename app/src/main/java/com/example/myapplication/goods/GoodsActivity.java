package com.example.myapplication.goods;

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

public class GoodsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int MEMBER_LOADER = 123;
    GoodsCursorAdapter memberCursorAdapter; // адаптер для listView

    ListView dataListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        // инициируем ListView
        dataListView = findViewById(R.id.dataListView);

        // инициируем кнопку
        FloatingActionButton floatingActionAddGoods = findViewById(R.id.floatingActionAddGoods);
        floatingActionAddGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsActivity.this, AddGoodsActivity.class);
                startActivity(intent);
            }
        });

        memberCursorAdapter = new GoodsCursorAdapter(this, null, false);
        dataListView.setAdapter(memberCursorAdapter);

        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //инициализируем нажатие на какой либо элемент listView
                Intent intent = new Intent(GoodsActivity.this,AddGoodsActivity.class);
                Uri currentMember = ContentUris.withAppendedId(MyScladContract.GoodsEntry.CONTENT_URI, id);
                intent.setData(currentMember);
                startActivity(intent); //запускаем activity
            }
        });

        getSupportLoaderManager().initLoader(MEMBER_LOADER, null, this); // инициализация Loader
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) { //В нем создаем наш объект CursorLoader, запрос  к базе данных
        String[] projection = {
                MyScladContract.GoodsEntry._ID,
                MyScladContract.GoodsEntry.COLUMN_NAME,
                MyScladContract.GoodsEntry.COLUMN_ARTICLE,
                MyScladContract.GoodsEntry.COLUMN_PRICE
        };

        CursorLoader cursorLoader = new CursorLoader( this,
                MyScladContract.GoodsEntry.CONTENT_URI,
                projection, // столбцы которые будут выводится
                null,
                null,
                null
        );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) { // Принимает данные из onCreateLoader с данными из базы данных, мы передает их в адаптер откуда они передаюся в ListView
        memberCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) { // используется для того чтобы удалять невостребованые Cursor (запись из таблицы удалена, изменена, то удаляем ссылки на эти данные)
        memberCursorAdapter.swapCursor(null);
    }
}
