package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteAdpter {

    public static final String MYDATABASE_NAME = "database";
    public static final int MYDATABASE_VERSION = 1;

    public static final String TABLE_PRODUCT = "product";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_MERCHANT_ID = "merchantId";
    public static final String KEY__CAT_ID = "categoryId";
    public static final String KEY__SUB_ID = "subCategoryId";
    public static final String KEY_DEAL_ID = "dealId";
    public static final String KEY_IMG = "imageUrl";
    public static final String KEY_CITY = "cityId";
    public static final String KEY_VALIDFR = "validFrom";
    public static final String KEY_VAILD_TO = "validTo";
    public static final String KEY_AC_PRICE = "actualPrice";
    public static final String KEY_DIS_PRISCE = "discountedPrice";
    public static final String KEY_DIS_PER = "discountedPercentage";
    public static final String KEY_MSER_NAME = "merchantName";
    public static final String KEY_MSER_ADD = "MerchantAddress";
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LONG = "longitude";

    Cursor cursor;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;


    public SqlLiteAdpter(Context c) {
        context = c;
    }

    public SqlLiteAdpter openToRead() throws SQLException {

        sqLiteHelper = new SQLiteHelper(this.context, MYDATABASE_NAME, null,
                MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public SqlLiteAdpter openToWrite() throws SQLException {
        sqLiteHelper = new SQLiteHelper(this.context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        sqLiteHelper.close();
    }


    private static final String TABLE_CREATE = "create table " + TABLE_PRODUCT + " (" + KEY_ID
            + " integer primary key autoincrement, " + KEY_NAME
            + " text , " + KEY_MERCHANT_ID
            + " text , " + KEY__CAT_ID
            + " text, " + KEY__SUB_ID
            + " text, " + KEY_DEAL_ID
            + " text, " + KEY_IMG
            + " text, " + KEY_CITY
            + " text, " + KEY_VALIDFR
            + " text, " + KEY_VAILD_TO
            + " text , " + KEY_AC_PRICE
            + " text, " + KEY_DIS_PRISCE
            + " text, " + KEY_DIS_PER
            + " text, " + KEY_MSER_NAME
            + " text, " + KEY_MSER_ADD
            + " text, " + KEY_LAT
            + " text, " + KEY_LONG
            + " text);";


    public class SQLiteHelper extends SQLiteOpenHelper {
        public SQLiteHelper(Context context, String name,
                            CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
            onCreate(db);
        }
    }


    public long insertintoTable(String name, String merchandid, String cat_id, String sub_id,
                                String deal_id, String img, String city, String validet_frm, String valid_to,
                                String ac_price, String dis_price, String dis_per, String msername, String mser_add, String lat, String lng
    ) {


        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_MERCHANT_ID, merchandid);
        contentValues.put(KEY__CAT_ID, cat_id);
        contentValues.put(KEY__SUB_ID, sub_id);
        contentValues.put(KEY_DEAL_ID, deal_id);
        contentValues.put(KEY_IMG, img);
        contentValues.put(KEY_CITY, city);
        contentValues.put(KEY_VALIDFR, validet_frm);
        contentValues.put(KEY_VAILD_TO, valid_to);
        contentValues.put(KEY_AC_PRICE, ac_price);
        contentValues.put(KEY_DIS_PRISCE, dis_price);
        contentValues.put(KEY_DIS_PER, dis_per);
        contentValues.put(KEY_MSER_NAME, msername);
        contentValues.put(KEY_MSER_ADD, mser_add);
        contentValues.put(KEY_LAT, lat);
        contentValues.put(KEY_LONG, lng);

        return sqLiteDatabase.insert(TABLE_PRODUCT, null, contentValues);

    }


    public Cursor queueArticlesDetails() {
        Cursor cursor;
        System.out.println("420");

        cursor = sqLiteHelper.getReadableDatabase().rawQuery(
                "select  * from " + TABLE_PRODUCT + " ORDER BY (" + KEY_ID + ") ", null);
        return cursor;

    }




    public boolean CheckContentIdAvailable(String contentId) throws SQLException {

        int count = -1;
        Cursor c = null;
        try {
            String query = "SELECT COUNT(*) FROM "
                    + TABLE_PRODUCT + " WHERE " + KEY_ID + " = ?";
            c = sqLiteHelper.getReadableDatabase().rawQuery(query, new String[]{contentId});
            if (c.moveToFirst()) {

                count = c.getInt(0);
            }

            return count > 0;
        } finally {

            if (c != null) {
                c.close();

            }
        }
    }
}
