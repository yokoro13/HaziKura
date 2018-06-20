package com.example.dev.hazikura.fragment.Household;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomOpenHelper extends SQLiteOpenHelper {

    static final private String DBName = "Wada_DB";
    static final private String TAG = "DBHelper";
    static final private String TABLE_NAME = "household";
    static final private int DATABASE_VERSION =1;

    public CustomOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public CustomOpenHelper(Context context) {
        super(context, DBName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       createIncomeTable(db);
       createOutgoTable(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    private void createOutgoTable(SQLiteDatabase db){ //支出テーブル作成
        String sql =
                "CREATE TABLE outgo ("
                        + " date char not null," //日付
                        + " purcahse char not null," //買ったもの
                        + " number integer not null," //個数
                        + " amount integer not null" //金額
                        + ");";
        db.execSQL(sql);
    }

    private void createIncomeTable(SQLiteDatabase db){ //収入テーブル作成
        String sql =
                "CREATE TABLE imcome ("
                        + " date char not null,"  //日付
                        + " amount integer not null" //金額
                        + ");";
        db.execSQL(sql);
    }
}
