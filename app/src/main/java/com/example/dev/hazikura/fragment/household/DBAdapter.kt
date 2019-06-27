package com.example.dev.hazikura.fragment.household

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v4.app.FragmentActivity
import android.util.Log

/**
 * Created by yokoro
 */

class DBAdapter(private var context: FragmentActivity) {

    private var db: SQLiteDatabase? = null
    private var dbHelper: DBHelper? = null

    init {
        dbHelper = DBHelper(this.context)
    }

    fun openDB(): DBAdapter {
        db = dbHelper!!.writableDatabase
        return this
    }

    fun readDB(): DBAdapter {
        db = dbHelper!!.readableDatabase
        return this
    }

    fun closeDB() {
        db!!.close()
        db = null
    }

    fun saveIncome(date: String, content: String, amount: Int) {
        db!!.beginTransaction()

        try {
            val values = ContentValues()
            values.put(IN_DATE, date)
            values.put(OUT_CONTENT, content)
            values.put(IN_AMOUNT, amount)
            db!!.insert(DB_INCOME_TABLE, null, values)

            db!!.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db!!.endTransaction()
        }
    }

    fun saveOutgo(date: String, content: String, number: Int, amount: Int) {
        db!!.beginTransaction()
        try {
            val values = ContentValues()
            values.put(OUT_DATE, date)
            values.put(OUT_CONTENT, content)
            values.put(OUT_NUMBER, number)
            values.put(OUT_AMOUNT, amount)

            db!!.insert(DB_OUTGO_TABLE, null, values)

            db!!.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db!!.endTransaction()
        }
    }

    fun saveRemainder(date: String, plans: String, place: String) {
        db!!.beginTransaction()
        try {
            val values = ContentValues()
            values.put(RMD_DATE, date)
            values.put(RMD_PLAN, plans)
            values.put(RMD_PLACE, place)

            db!!.insert(DB_REMAINDER_TABLE, null, values)

            db!!.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db!!.endTransaction()
        }
    }

    fun getDB(table: String, columns: Array<String>): Cursor {
        return db!!.query(table, columns, null, null, null, null, null)
    }

    fun searchDB(table: String, columns: Array<String>?, column: String, name: Array<String>): Cursor {
        return db!!.query(table, columns, "$column like ?", name, null, null, null)
    }

    fun selectDelete(table: String, position: String) {
        var id = ""
        db!!.beginTransaction()
        if (table === DB_INCOME_TABLE) {
            id = IN_ID
        }
        if (table === DB_OUTGO_TABLE) {
            id = OUT_ID
        }
        if (table === DB_REMAINDER_TABLE) {
            id = RMD_ID
        }
        try {
            db!!.delete(table, "$id=?", arrayOf(position))
            db!!.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db!!.endTransaction()
        }
    }

    fun updatePlans(date: String, plans: String, place: String) {
        db!!.beginTransaction()

        try {
            val values = ContentValues()
            values.put(RMD_DATE, date)
            values.put(RMD_PLAN, plans)
            values.put(RMD_PLACE, place)
            db!!.update(DB_REMAINDER_TABLE, values, "$RMD_DATE = date", null)
            db!!.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db!!.endTransaction()
        }
    }

    private class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            createIncomeTable(db)
            createOutgoTable(db)
            createRemainderTable(db)
            Log.i(TAG, "*****Create Tables*****")
        }


        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // DBからテーブル削除
            db.execSQL("DROP TABLE $DB_INCOME_TABLE;")
            db.execSQL("DROP TABLE $DB_OUTGO_TABLE;")
            db.execSQL("DROP TABLE $DB_REMAINDER_TABLE;")
            // テーブル生成
            onCreate(db)
            Log.i(TAG, "*****Update Tables*****")
        }

        private fun createIncomeTable(db: SQLiteDatabase) { //収入テーブル作成
            val sql = ("CREATE TABLE " + DB_INCOME_TABLE + "("
                    + IN_ID + " integer primary key autoincrement,"
                    + IN_DATE + " text not null," //日付

                    + IN_CONTENT + " text not null,"
                    + IN_AMOUNT + " integer not null" //金額

                    + ");")
            db.execSQL(sql)
            Log.i(TAG, "*****テーブルincomeを作成*****")
        }

        private fun createOutgoTable(db: SQLiteDatabase) { //支出テーブル作成
            val sql = ("CREATE TABLE " + DB_OUTGO_TABLE + "("
                    + OUT_ID + " integer primary key autoincrement,"
                    + OUT_DATE + " text not null," //日付

                    + OUT_CONTENT + " text not null," //買ったもの

                    + OUT_NUMBER + " integer not null," //個数

                    + OUT_AMOUNT + " integer not null" //金額

                    + ");")
            db.execSQL(sql)
            Log.i(TAG, "*****テーブルoutgoを作成*****")
        }

        private fun createRemainderTable(db: SQLiteDatabase) {
            val sql = ("CREATE TABLE " + DB_REMAINDER_TABLE + "("
                    + RMD_ID + " integer primary key autoincrement,"
                    + RMD_DATE + " text not null,"
                    + RMD_PLAN + " text,"
                    + RMD_PLACE + " text"
                    + ");")

            db.execSQL(sql)
            Log.i(TAG, "*****テーブルremainderを作成*****")
        }

    }

    companion object {
        private const val DB_NAME = "hazikura.db"
        private const val DB_INCOME_TABLE = "income"
        private const val DB_OUTGO_TABLE = "outgo"
        private const val DB_REMAINDER_TABLE = "remainder"
        private const val DB_VERSION = 1

        private const val TAG = "DBHelper"

        const val OUT_ID = "id"
        const val OUT_DATE = "date"
        const val OUT_CONTENT = "content"
        const val OUT_NUMBER = "number"
        const val OUT_AMOUNT = "amount"

        const val IN_ID = "id"
        const val IN_DATE = "date"
        const val IN_CONTENT = "content"
        const val IN_AMOUNT = "amount"

        const val RMD_ID = "id"
        const val RMD_DATE = "date"
        const val RMD_PLAN = "plans"
        const val RMD_PLACE = "place"
    }
}
