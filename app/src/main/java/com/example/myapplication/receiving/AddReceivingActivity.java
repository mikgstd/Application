package com.example.myapplication.receiving;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.MyScladContract;

import java.util.ArrayList;

public class AddReceivingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EDIT_RECEIVING_LOADER = 131;
    Uri currentMember;
    Uri currentReceivingAddLot;
    private EditText commentEditText;
    private EditText addLotGoodEditText;

    private Spinner goodSpinner;
    private ArrayAdapter spinnerAdapter;
    private ArrayList spinnerArrayList;
    private int selected;

    private Spinner spinner_partner;
    private ArrayAdapter spinnerPartnerAdapter;
    private ArrayList spinnerPartnerArrayList;
    private int spinnerpartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receiving);

        goodSpinner = findViewById(R.id.spinner_good);

        spinnerArrayList = new ArrayList(); // нужно в этот ArrayList() загружать данные из базы данных
        ContentResolver contentResolver = getContentResolver();

        Cursor сursor = contentResolver.query(MyScladContract.GoodsEntry.CONTENT_URI, // читаем данные о товарах из бд
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
        goodSpinner.setAdapter(spinnerAdapter); // присвоение

        //Спинер partner
        spinner_partner = findViewById(R.id.spinner_partner);

        spinnerPartnerArrayList = new ArrayList(); // нужно в этот ArrayList() загружать данные из базы данных
        ContentResolver contentPartnerResolver = getContentResolver();

        Cursor сursor2 = contentResolver.query(MyScladContract.PartnerEntry.CONTENT_URI,  // заполняем данными о контрагентах (поставщиках)
                new String[]{"_id", "name"},
                null,
                null,
                null);

        SimpleCursorAdapter adapterPartner = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, сursor2, new String[] {"name"}, new int[] {android.R.id.text1});
        adapterPartner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_partner.setAdapter(adapterPartner);


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
        commentEditText  = findViewById(R.id.commentEditText);
        addLotGoodEditText = findViewById(R.id.addLotGoodEditText);
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

        String selectedNameCurrentSpinner = goodSpinner.getSelectedItem().toString(); // Получаем выбранный элемент
        Log.d("lol", selectedNameCurrentSpinner);

        selected = goodSpinner.getSelectedItemPosition();
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



        spinnerpartner = spinner_partner.getSelectedItemPosition();
        String addCommentEditText = commentEditText.getText().toString().trim(); //trim() - обрезает все пробелы в начале и в конце строки
        String addLotGoodEditTexts = addLotGoodEditText.getText().toString().trim(); //trim() - обрезает все пробелы в начале и в конце строки
        int addLot =  Integer.parseInt(addLotGoodEditTexts);

        if (TextUtils.isEmpty(addCommentEditText)){ // проверка на пустоту полей (isEmpty проверяет есть ли хоть один символ)
            Toast.makeText(this,"Пустое наименование", Toast.LENGTH_LONG).show();
            return; //выходим из функции ничего не сохраняя
        }



        ContentValues contentValues = new ContentValues();
        contentValues.put(MyScladContract.ReceivingEntry.COLUMN_COMMENT, addCommentEditText);
        contentValues.put(MyScladContract.ReceivingEntry.COLUMN_GOOD_LOT, addLot);
        contentValues.put(MyScladContract.ReceivingEntry.COLUMN_GOOD, selected); // заполняем contentValues передаем столбец и что в этот столбец вставить
        contentValues.put(MyScladContract.ReceivingEntry.COLUMN_PARENT, spinnerpartner); // заполняем contentValues передаем столбец и что в этот столбец вставить
        contentValues.put(MyScladContract.ReceivingEntry.COLUMN_NAME, selectedNameCurrentSpinner); // заполняем contentValues передаем столбец и что в этот столбец вставить




        if (currentMember == null){ // null - если создаем новую запись в таблице
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(MyScladContract.ReceivingEntry.CONTENT_URI, contentValues); //contentResolver.insert возвращает uri

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

    //Я так понима. это вывод информации из базы данных в поля AddActivity
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                MyScladContract.ReceivingEntry._ID,
                MyScladContract.ReceivingEntry.COLUMN_COMMENT,
                MyScladContract.ReceivingEntry.COLUMN_GOOD,
                MyScladContract.ReceivingEntry.COLUMN_PARENT,
                MyScladContract.ReceivingEntry.COLUMN_GOOD_LOT
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
            int commentColumnIndex = cursor.getColumnIndex(MyScladContract.ReceivingEntry.COLUMN_COMMENT);
            int goodColumnIndex = cursor.getColumnIndex(MyScladContract.ReceivingEntry.COLUMN_GOOD);
            int partnerColumnIndex = cursor.getColumnIndex(MyScladContract.ReceivingEntry.COLUMN_PARENT);
            int addLotColumnIndex = cursor.getColumnIndex(MyScladContract.ReceivingEntry.COLUMN_GOOD_LOT);


            //извлекаем данные из cursor по этим индексам и присваиваем их соответствующим переменным
            String comment = cursor.getString(commentColumnIndex); // в строку кидаем извлеченные из cursor по индексу столбца данные
            int goods = cursor.getInt(goodColumnIndex); // в строку кидаем извлеченные из cursor по индексу столбца данные
            int partners = cursor.getInt(partnerColumnIndex); // в строку кидаем извлеченные из cursor по индексу столбца данные
            String addLots = cursor.getString(addLotColumnIndex); // в строку кидаем извлеченные из cursor по индексу столбца данные


            //Устанавливаем текст в EditText
            commentEditText.setText(comment);
            addLotGoodEditText.setText(addLots);

            // Устанавливаем текст в spinner
            goodSpinner.setSelection(goods);
            spinner_partner.setSelection(partners);

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

}
