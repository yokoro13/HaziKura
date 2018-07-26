package com.example.dev.hazikura.fragment.Household;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    private final static String DB_NAME = "hazikura.db";
    private final static String DB_INCOME_TABLE = "income";
    private final static String DB_OUTGO_TABLE = "outgo";
    private final static int DB_VERSION = 1;

    static final private String TAG = "DBHelper";

    public final static String OUT_ID = "id";
    public final static String OUT_DATE = "date";
    public final static String OUT_CONTENT = "content";
    public final static String OUT_NUMBER = "number";
    public final static String OUT_AMOUNT = "amount";

    public final static String IN_ID = "id";
    public final static String IN_DATE = "date";
    public final static String IN_CONTENT = "content";
    public final static String IN_AMOUNT = "amount";

    private SQLiteDatabase db = null;
    private DBHelper dbHelper = null;
    protected Context context;

    public DBAdapter(Context context){
        this.context = context;
        dbHelper = new DBHelper(this.context);
    }

    public DBAdapter openDB(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public DBAdapter readDB(){
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public void closeDB(){
        db.close();
        db = null;
    }

    public void saveIncome(String date, String content, int amount){
        db.beginTransaction();

        try{
            ContentValues values = new ContentValues();
            values.put(IN_DATE, date);
            values.put(OUT_CONTENT, content);
            values.put(IN_AMOUNT, amount);
            db.insert(DB_INCOME_TABLE, null, values);

            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void saveOutgo(String date, String content, int number,  int amount){
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(OUT_DATE, date);
            values.put(OUT_CONTENT, content);
            values.put(OUT_NUMBER, number);
            values.put(OUT_AMOUNT,amount);

            db.insert(DB_OUTGO_TABLE, null, values);

            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getDB(String table, String[] columns){
        return db.query(table, columns, null, null, null, null, null);
    }

    public Cursor searchDB(String table, String[] columns, String column, String[] name){
        return db.query(table, columns, column + "like ?", name, null, null, null);
    }

    public void allDelete(String table){
        db.beginTransaction();
        try {
            db.delete(table, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void selectDelete(String table, String position){
        String id = "";
        db.beginTransaction();
        if (table == DB_INCOME_TABLE){
            id = IN_ID;
        }
        if (table == DB_OUTGO_TABLE){
            id = OUT_ID;
        }
        try{
            db.delete(table, id + "=?", new String[]{position});
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
    private static class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            createIncomeTable(db);
            createOutgoTable(db);
            Log.i(TAG, "*****Create Tables*****");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // DBからテーブル削除
            db.execSQL("DROP TABLE " + DB_INCOME_TABLE + ";");
            db.execSQL("DROP TABLE " + DB_OUTGO_TABLE + ";");
            // テーブル生成
            onCreate(db);
            Log.i(TAG, "*****Update Tables*****");
        }

        private void createIncomeTable(SQLiteDatabase db){ //収入テーブル作成
            String sql =
                    "CREATE TABLE " + DB_INCOME_TABLE + "("
                            + IN_ID + " integer primary key autoincrement,"
                            + IN_DATE + " text not null," //日付
                            + IN_CONTENT + " text not null,"
                            + IN_AMOUNT + " integer not null" //金額
                            + ");";
            db.execSQL(sql);
            Log.i(TAG, "*****テーブルincomeを作成*****");
        }

        private void createOutgoTable(SQLiteDatabase db){ //支出テーブル作成
            String sql =
                    "CREATE TABLE " + DB_OUTGO_TABLE + "("
                            + OUT_ID + " integer primary key autoincrement,"
                            + OUT_DATE + " text not null," //日付
                            + OUT_CONTENT + " text not null," //買ったもの
                            + OUT_NUMBER + " integer not null," //個数
                            + OUT_AMOUNT + " integer not null" //金額
                            + ");";
            db.execSQL(sql);
            Log.i(TAG, "*****テーブルoutgoを作成*****");
        }

    }
}
