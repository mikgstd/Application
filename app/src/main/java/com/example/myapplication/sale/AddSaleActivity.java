package com.example.myapplication.sale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.MyScladContract;

import java.util.ArrayList;

public class AddSaleActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EDIT_RECEIVING_LOADER = 139;
    Uri currentMember;
    Uri currentSaleAddLot;
    private EditText addLotSaleEditText;
    private TextView wholePriceTextView;

    private Spinner addSaleSpinnerGood;
    private ArrayAdapter spinnerAdapter;
    private ArrayList spinnerArrayList;
    private int selected;

    private Spinner addSaleSpinnerPartner;
    private ArrayAdapter spinnerPartnerAdapter;
    private ArrayList spinnerPartnerArrayList;
    private int spinnerpartner;
    private int current_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        addSaleSpinnerGood = findViewById(R.id.addSaleSpinnerGood);

        spinnerArrayList = new ArrayList(); // нужно в этот ArrayList() загружать данные из базы данных
        ContentResolver contentResolver = getContentResolver();

        Cursor сursor = contentResolver.query(MyScladContract.GoodsEntry.CONTENT_URI, // читаем данные о товарах из бд для спинера
                new String[]{"_id", "name"},
                null,
                null,
                null);

        while (сursor.moveToNext()) { //запоняем данными о товарах из Cursor ArrayList
            String spinner_lots;
            spinner_lots = сursor.getString(сursor.getColumnIndex(MyScladContract.GoodsEntry.COLUMN_NAME));
            spinnerArrayList.add(spinner_lots);
        } сursor.close();



        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList); //в закрытом состоянии
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // в развернутом состоянии
        addSaleSpinnerGood.setAdapter(spinnerAdapter); // присвоение

        //Спинер partner
        addSaleSpinnerPartner = findViewById(R.id.addSaleSpinnerPartner);

        spinnerPartnerArrayList = new ArrayList(); // нужно в этот ArrayList() загружать данные из базы данных
        ContentResolver contentPartnerResolver = getContentResolver();

        Cursor сursor2 = contentResolver.query(MyScladContract.PartnerEntry.CONTENT_URI,  // заполняем данными о контрагентах (поставщиках)
                new String[]{"_id", "name"},
                null,
                null,
                null);

        SimpleCursorAdapter adapterPartner = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, сursor2, new String[] {"name"}, new int[] {android.R.id.text1});
        adapterPartner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addSaleSpinnerPartner.setAdapter(adapterPartner);


        //получаем intent при помощи которого запустили activity
        Intent intent = getIntent();

        currentMember = intent.getData(); //если запущено кликом то uri не будет равен null и в нем будет содержаться нужный нам uri

        if ( currentMember == null){ // если равен null то запус совершен при помощи кнопки добавления нового пользователя
            setTitle("Добавление"); // заголовок activity
            invalidateOptionsMenu(); // отключаем меню при добавлении пользователя
        } else {
            setTitle("Редактирование");
            getSupportLoaderManager().initLoader(EDIT_RECEIVING_LOADER, null, this); // инициализировали Loader только если редактируется текущий член клуба
        }
        addLotSaleEditText = findViewById(R.id.addLotSaleEditText);
        wholePriceTextView = findViewById(R.id.wholePriceTextView);
    }

    @Override //  invalidateOptionsMenu запускает onPrepareOptionsMenu
    public boolean onPrepareOptionsMenu( Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (currentMember == null){
            MenuItem menuItem = menu.findItem(R.id.delete_member);
            menuItem.setVisible(false); //делаем невидимым меню при uri == 0
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_goods_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_member:
                saveMember();
                return true;
            case R.id.delete_member:
                showDeleteMemberDialog();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveMember(){  // кнопка сохранения

        String selectedNameCurrentSpinner = addSaleSpinnerGood.getSelectedItem().toString(); // Получаем выбранный элемент
        Log.d("lol", selectedNameCurrentSpinner);

        selected = addSaleSpinnerGood.getSelectedItemPosition();
        Log.d("lol", String.valueOf(selected));


        /*ContentResolver contentResolvers = getContentResolver();
        Cursor сursor = contentResolvers.query(MyScladContract.GoodsEntry.CONTENT_URI,
                new String[]{"_id", "lot"},
                null,
                null,
                null);

        сursor.moveToPosition(selected);

        int current_lot = сursor.getInt(сursor.getColumnIndex(MyScladContract.GoodsEntry.COLUMN_LOT));

        Log.d("lol", String.valueOf(current_lot));*/
        ContentResolver contentResolvers = getContentResolver();
        Cursor сursor = contentResolvers.query(MyScladContract.GoodsEntry.CONTENT_URI, // читаем данные о товарах из бд для спинера
                new String[]{"_id", "name", "price"},
                null,
                null,
                null);
        сursor.moveToPosition(selected);
        current_price = сursor.getInt(сursor.getColumnIndex(MyScladContract.GoodsEntry.COLUMN_PRICE));

       /* while (сursor.moveToNext()) { //запоняем данными о товарах из Cursor ArrayList
            int current_price;
            String current_string = сursor.getString(сursor.getColumnIndex(MyScladContract.GoodsEntry.COLUMN_NAME);
            if ( current_string == selectedNameCurrentSpinner)){
                current_price = сursor.getInt(сursor.getColumnIndex(MyScladContract.GoodsEntry.COLUMN_NAME));
            }
        } сursor.close();*/

        spinnerpartner = addSaleSpinnerPartner.getSelectedItemPosition();
        String addLotGoodEditTexts = addLotSaleEditText.getText().toString().trim(); //trim() - обрезает все пробелы в начале и в конце строки
        int addLot =  Integer.parseInt(addLotGoodEditTexts);
        int wholesum = addLot * current_price;



        ContentValues contentValues = new ContentValues();
        contentValues.put(MyScladContract.SaleEntry.COLUMN_GOOD_LOT, addLot);
        contentValues.put(MyScladContract.SaleEntry.COLUMN_GOOD, selected); // заполняем contentValues передаем столбец и что в этот столбец вставить
        contentValues.put(MyScladContract.SaleEntry.COLUMN_GOOD_WHOLE_PRICE, wholesum); // заполняем contentValues передаем столбец и что в этот столбец вставить
        contentValues.put(MyScladContract.SaleEntry.COLUMN_PARENT, spinnerpartner); // заполняем contentValues передаем столбец и что в этот столбец вставить
        contentValues.put(MyScladContract.SaleEntry.COLUMN_NAME, selectedNameCurrentSpinner); // заполняем contentValues передаем столбец и что в этот столбец вставить




        if (currentMember == null){ // null - если создаем новую запись в таблице
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(MyScladContract.SaleEntry.CONTENT_URI, contentValues); //contentResolver.insert возвращает uri

            if (uri == null){
                Toast.makeText(this,"Ошибка вставки строки в таблицу", Toast.LENGTH_LONG).show();
            } else Toast.makeText(this,"Всё в порядке", Toast.LENGTH_LONG).show();
        } else {
            int rowsChanged = getContentResolver().update(currentMember, contentValues, null,null);

            if (rowsChanged == 0){
                Toast.makeText(this,"Ошибка сохранения", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"Всё в порядке", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showDeleteMemberDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //this означает что диалог будет поялвяться в нашей активити
        builder.setMessage("Хотите удалить этого пользователя?");
        builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() { // Добавляем кнопку удалить
            @Override
            public void onClick(DialogInterface dialog, int which) { // Что будет происходить при нажатии на кнопку удалить
                deleteMember();
            }
        });
        builder.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null){ // проверка
                    dialog.dismiss(); //отключаем диалог
                }
            }
        });
        AlertDialog alertDialog = builder.create(); // вызываем диалог
        alertDialog.show(); //показать диалог
    }
    private void deleteMember(){
        if (currentMember != null){ // проверка, только на редактированиидобавляем возможность удаления
            int rowDeleted = getContentResolver().delete(currentMember, null,null);

            if (rowDeleted == 0) { // если rowDeleted (число удаленных строк) равно нулю, значит при удалении возникла ошибка
                Toast.makeText(this,"Ошибка удаления", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"Успешное удаление", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                MyScladContract.SaleEntry._ID,
                MyScladContract.SaleEntry.COLUMN_GOOD,
                MyScladContract.SaleEntry.COLUMN_GOOD_WHOLE_PRICE,
                MyScladContract.SaleEntry.COLUMN_PARENT,
                MyScladContract.SaleEntry.COLUMN_GOOD_LOT
        };

        return new CursorLoader( this,
                currentMember, //uri нашей отдельной записи
                projection, // столбцы которые будут выводится
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()){ //получаем индексы столюцов
            int goodColumnIndex = cursor.getColumnIndex(MyScladContract.SaleEntry.COLUMN_GOOD);
            int partnerColumnIndex = cursor.getColumnIndex(MyScladContract.SaleEntry.COLUMN_PARENT);
            int addLotColumnIndex = cursor.getColumnIndex(MyScladContract.SaleEntry.COLUMN_GOOD_LOT);
            int wholePriceColumnIndex = cursor.getColumnIndex(MyScladContract.SaleEntry.COLUMN_GOOD_WHOLE_PRICE);


            //извлекаем данные из cursor по этим индексам и присваиваем их соответствующим переменным
            int goods = cursor.getInt(goodColumnIndex); // в строку кидаем извлеченные из cursor по индексу столбца данные
            int partners = cursor.getInt(partnerColumnIndex); // в строку кидаем извлеченные из cursor по индексу столбца данные
            String addLots = cursor.getString(addLotColumnIndex); // в строку кидаем извлеченные из cursor по индексу столбца данные
            String wholePrice = cursor.getString(wholePriceColumnIndex); // в строку кидаем извлеченные из cursor по индексу столбца данные


            //Устанавливаем текст в EditText
            addLotSaleEditText.setText(addLots);
            wholePriceTextView.setText("Итоговая сумма: " + wholePrice + " руб.");

            // Устанавливаем текст в spinner
            addSaleSpinnerGood.setSelection(goods);
            addSaleSpinnerPartner.setSelection(partners);

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
