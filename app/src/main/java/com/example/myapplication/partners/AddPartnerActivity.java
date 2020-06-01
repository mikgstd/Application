package com.example.myapplication.partners;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.MyScladContract;

public class AddPartnerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EDIT_PARTNER_LOADER = 121;
    Uri currentMember;
    private EditText addPartnerNameEditText;
    private EditText addPartnerStreetEditText;
    private EditText addPartnerFIOEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partner);

        //получаем intent при помощи которого запустили activity
        Intent intent = getIntent();

        currentMember = intent.getData(); //если запущено кликом то uri не будет равен null и в нем будет содержаться нужный нам uri

        if ( currentMember == null){ // если равен null то запус совершен при помощи кнопки добавления нового пользователя
            setTitle("Добавление"); // заголовок activity
            invalidateOptionsMenu(); // отключаем меню при добавлении пользователя
        } else {
            setTitle("Редактирование");
            getSupportLoaderManager().initLoader(EDIT_PARTNER_LOADER, null, this); // инициализировали Loader только если редактируется текущий член клуба
        }

        addPartnerNameEditText  = findViewById(R.id.addPartnerNameEditText);
         addPartnerStreetEditText = findViewById(R.id.addPartnerStreetEditText);
         addPartnerFIOEditText = findViewById(R.id.addPartnerFIOEditText);


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
        String addName = addPartnerNameEditText.getText().toString().trim(); //trim() - обрезает все пробелы в начале и в конце строки
        String addArticle = addPartnerStreetEditText.getText().toString().trim();
        String addPrice = addPartnerFIOEditText.getText().toString().trim();

        if (TextUtils.isEmpty(addName)){ // проверка на пустоту полей (isEmpty проверяет есть ли хоть один символ)
            Toast.makeText(this,"Пустое наименование", Toast.LENGTH_LONG).show();
            return; //выходим из функции ничего не сохраняя
        } else if (TextUtils.isEmpty(addArticle)){
            Toast.makeText(this,"Пустая фамилия", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(addPrice)){
            Toast.makeText(this,"Пустое поле спорт", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MyScladContract.PartnerEntry.COLUMN_NAME, addName);
        contentValues.put(MyScladContract.PartnerEntry.COLUMN_ADDRESS, addArticle);
        contentValues.put(MyScladContract.PartnerEntry.COLUMN_CONTACT, addPrice);

        if (currentMember == null){ // null - если создаем новую запись в таблице
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(MyScladContract.PartnerEntry.CONTENT_URI, contentValues); //contentResolver.insert возвращает uri
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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                MyScladContract.PartnerEntry._ID,
                MyScladContract.PartnerEntry.COLUMN_NAME,
                MyScladContract.PartnerEntry.COLUMN_ADDRESS,
                MyScladContract.PartnerEntry.COLUMN_CONTACT
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
            int nameColumnIndex = cursor.getColumnIndex(MyScladContract.PartnerEntry.COLUMN_NAME);
            int articleColumnIndex = cursor.getColumnIndex(MyScladContract.PartnerEntry.COLUMN_ADDRESS);
            int priceColumnIndex = cursor.getColumnIndex(MyScladContract.PartnerEntry.COLUMN_CONTACT);

            //извлекаем данные из cursor по этим индексам и присваиваем их соответствующим переменным
            String name = cursor.getString(nameColumnIndex); // в строку кидаем извлеченные из cursor по индексу столбца данные
            String article = cursor.getString(articleColumnIndex); // в строку кидаем извлеченные из cursor по индексу столбца данные
            String price = cursor.getString(priceColumnIndex); // в строку кидаем извлеченные из cursor по индексу столбца данные


            //Устанавливаем текст в EditText
            addPartnerNameEditText.setText(name);
            addPartnerStreetEditText.setText(article);
            addPartnerFIOEditText.setText(price);

            // Устанавливаем текст в spinner
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

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
}
