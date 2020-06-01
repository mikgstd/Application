package com.example.myapplication.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyScladContentProvider extends ContentProvider {

    MyScladDbHelper myScladDbHelper;

    private static final int GOODS = 11;
    private static final int GOOD_ID = 22;
    private static final int PARTNERS = 33;
    private static final int PARTNER_ID = 44;
    private static final int RECEIVING = 55;
    private static final int RECEIVING_ID = 66;
    private static final int SALE = 77;
    private static final int SALE_ID = 88;
    private static final int SCLAD = 99;
    private static final int SCLAD_ID = 110;


    @Override
    public boolean onCreate() {
        myScladDbHelper = new MyScladDbHelper(getContext());
        return true;
    }

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(MyScladContract.AUTHORITY, MyScladContract.GoodsEntry.PATH_GOODS, GOODS);
        uriMatcher.addURI(MyScladContract.AUTHORITY, MyScladContract.PartnerEntry.PATH_PARTNERS, PARTNERS);
        uriMatcher.addURI(MyScladContract.AUTHORITY, MyScladContract.GoodsEntry.PATH_GOODS + "/#", GOOD_ID);
        uriMatcher.addURI(MyScladContract.AUTHORITY, MyScladContract.PartnerEntry.PATH_PARTNERS + "/#", PARTNER_ID);
        uriMatcher.addURI(MyScladContract.AUTHORITY, MyScladContract.ReceivingEntry.PATH_PARTNERS, RECEIVING);
        uriMatcher.addURI(MyScladContract.AUTHORITY, MyScladContract.ReceivingEntry.PATH_PARTNERS + "/#", RECEIVING_ID);
        uriMatcher.addURI(MyScladContract.AUTHORITY, MyScladContract.SaleEntry.PATH_PARTNERS, SALE);
        uriMatcher.addURI(MyScladContract.AUTHORITY, MyScladContract.SaleEntry.PATH_PARTNERS + "/#", SALE_ID);
        uriMatcher.addURI(MyScladContract.AUTHORITY, MyScladContract.ScladEntry.PATH_PARTNERS, SCLAD);
        uriMatcher.addURI(MyScladContract.AUTHORITY, MyScladContract.ScladEntry.PATH_PARTNERS + "/#", SCLAD_ID);
    }

    @Override
    public Cursor query(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = myScladDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri); // отправляем uri в uriMatcher сравниваем и возвращается число нужного

        switch (match){
            case GOODS:
                // если нужно вытащить все данные из таблицы
                cursor = db.query(MyScladContract.GoodsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            break;
            case GOOD_ID:
                // если нужно вытащить одну строку
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.GoodsEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                cursor = db.query(MyScladContract.GoodsEntry.TABLE_NAME, projection, selection, selectionArgs,null, null, sortOrder);
                // передаем запрос с название талицы, projection (со столбцами), selection - условие отбора,  selectionArgs - номер id,
                break;
            case PARTNERS:
                // если нужно вытащить все данные из таблицы
                cursor = db.query(MyScladContract.PartnerEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PARTNER_ID:
                // если нужно вытащить одну строку
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.PartnerEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                cursor = db.query(MyScladContract.PartnerEntry.TABLE_NAME, projection, selection, selectionArgs,null, null, sortOrder);
                // передаем запрос с название талицы, projection (со столбцами), selection - условие отбора,  selectionArgs - номер id,
                break;
            case RECEIVING:
                // если нужно вытащить все данные из таблицы
                cursor = db.query(MyScladContract.ReceivingEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case RECEIVING_ID:
                // если нужно вытащить одну строку
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.ReceivingEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                cursor = db.query(MyScladContract.ReceivingEntry.TABLE_NAME, projection, selection, selectionArgs,null, null, sortOrder);
                // передаем запрос с название талицы, projection (со столбцами), selection - условие отбора,  selectionArgs - номер id,
                break;
            case SALE:
                // если нужно вытащить все данные из таблицы
                cursor = db.query(MyScladContract.SaleEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SALE_ID:
                // если нужно вытащить одну строку
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.SaleEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                cursor = db.query(MyScladContract.SaleEntry.TABLE_NAME, projection, selection, selectionArgs,null, null, sortOrder);
                // передаем запрос с название талицы, projection (со столбцами), selection - условие отбора,  selectionArgs - номер id,
            case SCLAD:
                // если нужно вытащить все данные из таблицы
                cursor = db.query(MyScladContract.ScladEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SCLAD_ID:
                // если нужно вытащить одну строку
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.ScladEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                cursor = db.query(MyScladContract.ScladEntry.TABLE_NAME, projection, selection, selectionArgs,null, null, sortOrder);
                // передаем запрос с название талицы, projection (со столбцами), selection - условие отбора,  selectionArgs - номер id,
                break;
            default:
                //когда URI некорректный
                throw new IllegalArgumentException("Чтение невозможно, неккоректный URI" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri); // отправляем uri в uriMatcher сравниваем и возвращается число нужного

        switch (match){
            case GOODS:
                // код напише позже
                return MyScladContract.GoodsEntry.CONTENT_MULTIPLE_ITEMS;
            case PARTNERS:
                // код напише позже
                return MyScladContract.PartnerEntry.CONTENT_MULTIPLE_ITEMS;
            case GOOD_ID:
                return MyScladContract.GoodsEntry.CONTENT_SINGLE_ITEMS;
            case PARTNER_ID:
                return MyScladContract.PartnerEntry.CONTENT_SINGLE_ITEMS;
            case RECEIVING:
                return MyScladContract.ReceivingEntry.CONTENT_MULTIPLE_ITEMS;
            case RECEIVING_ID:
                return MyScladContract.ReceivingEntry.CONTENT_SINGLE_ITEMS;
            case SALE:
                return MyScladContract.SaleEntry.CONTENT_MULTIPLE_ITEMS;
            case SALE_ID:
                return MyScladContract.SaleEntry.CONTENT_SINGLE_ITEMS;
            case SCLAD:
                return MyScladContract.ScladEntry.CONTENT_MULTIPLE_ITEMS;
            case SCLAD_ID:
                return MyScladContract.ScladEntry.CONTENT_SINGLE_ITEMS;
            default:
                //когда URI некорректный
                throw new IllegalArgumentException("Неизвестный URI" + uri); // Если вводится неккоректный параметр
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // передаем URI типа - content://com.example.sport/members без указания конкретной строки + данные ContentValues которые вставляются в новую строку, новая строка будет создана в конце, то есть последний id +1
        if (uri.toString() == "content://com.example.sport/members"){
        String firstName = values.getAsString(MyScladContract.GoodsEntry.COLUMN_NAME);
        if(firstName == null){
            throw new IllegalArgumentException("Вы должны ввести наименомание товара" + uri);
        }

        String article = values.getAsString(MyScladContract.GoodsEntry.COLUMN_ARTICLE);
        if(article == null){
            throw new IllegalArgumentException("Вы должны ввести артикль " + uri);
        }

        String price = values.getAsString(MyScladContract.GoodsEntry.COLUMN_PRICE);
        if(price == null){
            throw new IllegalArgumentException("Вы должны ввести цену продажи" + uri);
        }
    }


        SQLiteDatabase db = myScladDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri); // отправляем uri в uriMatcher сравниваем и возвращается число нужного

        switch (match){
            case GOODS:
                // код напише позже
                long id = db.insert(MyScladContract.GoodsEntry.TABLE_NAME, null, values); // возвращает значение типа long равное значению id новой строки, в случае ошибки возвращается -1
                if (id == -1){
                    Log.d("insertMethod", "Произошла ошибка при вставке новой строки в таблицу!" + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri,null);

                return ContentUris.withAppendedId(uri, id); //withAppendedId добавляет в конец uri - id
            case PARTNERS:
                // код напише позже
                long id2 = db.insert(MyScladContract.PartnerEntry.TABLE_NAME, null, values); // возвращает значение типа long равное значению id новой строки, в случае ошибки возвращается -1
                if (id2 == -1){
                    Log.d("insertMethod", "Произошла ошибка при вставке новой строки в таблицу!" + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri,null);

                return ContentUris.withAppendedId(uri, id2); //withAppendedId добавляет в конец uri - id
            case RECEIVING:
                // код напише позже
                long id3 = db.insert(MyScladContract.ReceivingEntry.TABLE_NAME, null, values); // возвращает значение типа long равное значению id новой строки, в случае ошибки возвращается -1
                if (id3 == -1){
                    Log.d("insertMethod", "Произошла ошибка при вставке новой строки в таблицу!" + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri,null);

                return ContentUris.withAppendedId(uri, id3); //withAppendedId добавляет в конец uri - id

            case SALE:
                // код напише позже
                long id4 = db.insert(MyScladContract.SaleEntry.TABLE_NAME, null, values); // возвращает значение типа long равное значению id новой строки, в случае ошибки возвращается -14
                if (id4 == -1){
                    Log.d("insertMethod", "Произошла ошибка при вставке новой строки в таблицу!" + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri,null);

                return ContentUris.withAppendedId(uri, id4); //withAppendedId добавляет в конец uri - id

            case SCLAD:
                // код напише позже
                long id5 = db.insert(MyScladContract.ScladEntry.TABLE_NAME, null, values); // возвращает значение типа long равное значению id новой строки, в случае ошибки возвращается -14
                if (id5 == -1){
                    Log.d("insertMethod", "Произошла ошибка при вставке новой строки в таблицу!" + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri,null);

                return ContentUris.withAppendedId(uri, id5); //withAppendedId добавляет в конец uri - id
            default:
                //когда URI некорректный
                throw new IllegalArgumentException("Произошла ошибка при вставке новой строки в таблицу!" + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myScladDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri); // отправляем uri в uriMatcher сравниваем и возвращается число нужного

        int rowsDeleted;
        switch (match){
            case GOODS:
                // код напише позже
                rowsDeleted = db.delete(MyScladContract.GoodsEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case PARTNERS:
                // код напише позже
                rowsDeleted = db.delete(MyScladContract.PartnerEntry.TABLE_NAME,selection,selectionArgs);
                break;

            case GOOD_ID:
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.GoodsEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                rowsDeleted = db.delete(MyScladContract.GoodsEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case PARTNER_ID:
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.PartnerEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                rowsDeleted = db.delete(MyScladContract.PartnerEntry.TABLE_NAME,selection,selectionArgs);
                break;

            case RECEIVING:
                rowsDeleted = db.delete(MyScladContract.ReceivingEntry.TABLE_NAME,selection,selectionArgs);
                break;

            case RECEIVING_ID:
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.ReceivingEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                rowsDeleted = db.delete(MyScladContract.ReceivingEntry.TABLE_NAME,selection,selectionArgs);
                break;

            case SALE:
                rowsDeleted = db.delete(MyScladContract.SaleEntry.TABLE_NAME,selection,selectionArgs);
                break;

            case SALE_ID:
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.SaleEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                rowsDeleted = db.delete(MyScladContract.SaleEntry.TABLE_NAME,selection,selectionArgs);
                break;

            case SCLAD:
                rowsDeleted = db.delete(MyScladContract.ScladEntry.TABLE_NAME,selection,selectionArgs);
                break;

            case SCLAD_ID:
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.ScladEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                rowsDeleted = db.delete(MyScladContract.ScladEntry.TABLE_NAME,selection,selectionArgs);
                break;

            default:
                //когда URI некорректный
                throw new IllegalArgumentException("Невозможно удалить этот URI" + uri); // Если вводится неккоректный параметр
        }
        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return  rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(MyScladContract.GoodsEntry.COLUMN_NAME)){
            String name = values.getAsString(MyScladContract.GoodsEntry.COLUMN_NAME);
            if(name == null){
                throw new IllegalArgumentException("Вы должны ввести наименование товара" + uri);
            }
        }

        if (values.containsKey(MyScladContract.GoodsEntry.COLUMN_ARTICLE)) {
            String article = values.getAsString(MyScladContract.GoodsEntry.COLUMN_ARTICLE);
            if (article == null) {
                throw new IllegalArgumentException("Вы должны ввести артикль товара" + uri);
            }
        }

        if (values.containsKey(MyScladContract.GoodsEntry.COLUMN_PRICE)) {
            String price = values.getAsString(MyScladContract.GoodsEntry.COLUMN_PRICE);
            if (price == null) {
                throw new IllegalArgumentException("Вы должны ввести цену товара" + uri);
            }
        }



        SQLiteDatabase db = myScladDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri); // отправляем uri в uriMatcher сравниваем и возвращается число нужного

        int rowsUpdated;

        switch (match){
            case GOODS:
                rowsUpdated  =  db.update(MyScladContract.GoodsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            case GOOD_ID:
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.GoodsEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                rowsUpdated =  db.update(MyScladContract.GoodsEntry.TABLE_NAME, values,selection,selectionArgs);
                break;
            case PARTNERS:
                rowsUpdated  =  db.update(MyScladContract.PartnerEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case PARTNER_ID:
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.PartnerEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                rowsUpdated =  db.update(MyScladContract.PartnerEntry.TABLE_NAME, values,selection,selectionArgs);
                break;
            case RECEIVING:
                rowsUpdated  =  db.update(MyScladContract.ReceivingEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case RECEIVING_ID:
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.ReceivingEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                rowsUpdated =  db.update(MyScladContract.ReceivingEntry.TABLE_NAME, values,selection,selectionArgs);
                break;
            case SALE:
                rowsUpdated  =  db.update(MyScladContract.SaleEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SALE_ID:
                // selection = "_id=?" в SQL запросе заместо ? будут поставлены аргументы selectionArgs
                // selectionArgs = 34
                selection = MyScladContract.SaleEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                // ContentUris.parseId(uri) - последняя часть uri (content://com.example.sport/members/34) в нашем случае 34 будет преобразована в long
                rowsUpdated =  db.update(MyScladContract.SaleEntry.TABLE_NAME, values,selection,selectionArgs);
                break;

            default:
                //когда URI некорректный
                throw new IllegalArgumentException("Невозможно обновить этот URI" + uri); // Если вводится неккоректный параметр
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
