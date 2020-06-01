package com.example.myapplication.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MyScladContract {
    private MyScladContract() { // private чтобы никто не смог снаружи создать объект этого класса
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mysclad";

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.myapplication";


    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    public static final class GoodsEntry implements BaseColumns { // данные о людях
        public static final String TABLE_NAME = "goods";
        public static final String PATH_GOODS = "goods";

        public static final String KEY_ID = BaseColumns._ID; // автоматически выставляет id
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ARTICLE = "article";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_LOT = "lot";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GOODS);

        public static final String CONTENT_MULTIPLE_ITEMS = "vnd.android.cursor.dir/" + AUTHORITY + "/" + PATH_GOODS;
        public static final String CONTENT_SINGLE_ITEMS = "vnd.android.cursor.item/" + AUTHORITY + "/" + PATH_GOODS;

    }
    public static final class PartnerEntry implements BaseColumns{
        public static final String TABLE_NAME = "partners";
        public static final String PATH_PARTNERS = "partners";

        public static final String KEY_ID = BaseColumns._ID; // автоматически выставляет id
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_CONTACT = "contact";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PARTNERS);

        public static final String CONTENT_MULTIPLE_ITEMS = "vnd.android.cursor.dir/" + AUTHORITY + "/" + PATH_PARTNERS;
        public static final String CONTENT_SINGLE_ITEMS = "vnd.android.cursor.item/" + AUTHORITY + "/" + PATH_PARTNERS;
    }

    public static final class ReceivingEntry implements BaseColumns{
        public static final String TABLE_NAME = "receiving";
        public static final String PATH_PARTNERS = "receiving";

        public static final String KEY_ID = BaseColumns._ID; // автоматически выставляет id
        public static final String COLUMN_COMMENT = "comment";
        public static final String COLUMN_GOOD = "good";
        public static final String COLUMN_PARENT = "parent";
        public static final String COLUMN_GOOD_LOT = "good_lot";
        public static final String COLUMN_NAME = "name";


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PARTNERS);

        public static final String CONTENT_MULTIPLE_ITEMS = "vnd.android.cursor.dir/" + AUTHORITY + "/" + PATH_PARTNERS;
        public static final String CONTENT_SINGLE_ITEMS = "vnd.android.cursor.item/" + AUTHORITY + "/" + PATH_PARTNERS;
    }

    public static final class SaleEntry implements BaseColumns{
        public static final String TABLE_NAME = "sales";
        public static final String PATH_PARTNERS = "sales";

        public static final String KEY_ID = BaseColumns._ID; // автоматически выставляет id
        public static final String COLUMN_GOOD = "sale_good";
        public static final String COLUMN_PARENT = "sale_parent";
        public static final String COLUMN_GOOD_LOT = "add_lot_sale_good";
        public static final String COLUMN_GOOD_WHOLE_PRICE = "whole_price_sale_good";
        public static final String COLUMN_NAME = "name";


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PARTNERS);

        public static final String CONTENT_MULTIPLE_ITEMS = "vnd.android.cursor.dir/" + AUTHORITY + "/" + PATH_PARTNERS;
        public static final String CONTENT_SINGLE_ITEMS = "vnd.android.cursor.item/" + AUTHORITY + "/" + PATH_PARTNERS;
    }

    public static final class ScladEntry implements BaseColumns{ //таблица с остатками для склада
        public static final String TABLE_NAME = "sclads";
        public static final String PATH_PARTNERS = "sclads";

        public static final String KEY_ID = BaseColumns._ID; // автоматически выставляет id
        public static final String COLUMN_GOOD = "sclad_good_name"; // имя товара
        public static final String COLUMN_GOOD_LOT = "sclad_lot"; // количество

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PARTNERS);

        public static final String CONTENT_MULTIPLE_ITEMS = "vnd.android.cursor.dir/" + AUTHORITY + "/" + PATH_PARTNERS;
        public static final String CONTENT_SINGLE_ITEMS = "vnd.android.cursor.item/" + AUTHORITY + "/" + PATH_PARTNERS;
    }
}
