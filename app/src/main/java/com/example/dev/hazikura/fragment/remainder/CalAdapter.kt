package com.example.dev.hazikura.fragment.remainder

import android.database.CursorIndexOutOfBoundsException
import android.graphics.Color
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.TextView

import com.example.dev.hazikura.R
import com.example.dev.hazikura.fragment.household.DBAdapter

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

/**
 * Created by yokoro
 */

class CalAdapter(private val mContext: FragmentActivity) : BaseAdapter() {
    private lateinit var dbAdapter: DBAdapter
    private val mDateManager: DateManager = DateManager()
    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    //表示月を取得
    val title: String
        get() {
            val format = SimpleDateFormat("yyyy.MM", Locale.JAPAN)
            return format.format(mDateManager.mCalendar.time)
        }

    val date: String
        get() {
            val format = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
            return format.format(mDateManager.mCalendar.time)
        }

    //カスタムセルを拡張したらここでWigetを定義
    private class ViewHolder {
        var dateText: TextView? = null
    }

    init {
        dateArray = mDateManager.days
    }

    override fun getCount(): Int {
        return dateArray.size
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view
        var holder = ViewHolder()
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.calendar_cell, null)
            holder.dateText = convertView!!.findViewById(R.id.dateText)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        //セルのサイズを指定
        val dp = mContext.resources.displayMetrics.density
        val params = AbsListView.LayoutParams(parent.width / 7 - dp.toInt(), (parent.height - dp.toInt() * mDateManager.weeks) / mDateManager.weeks)
        convertView.layoutParams = params

        //日付のみ表示させる
        val dateFormat = SimpleDateFormat("d", Locale.US)
        holder.dateText!!.text = dateFormat.format(dateArray[position])

        val format = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)

        //当月以外のセルをグレーアウト
        when {
            searchPlan(format.format(dateArray[position])) !== "" -> convertView.setBackgroundColor(Color.rgb(210, 240, 200))
            mDateManager.isCurrentMonth(dateArray[position]) -> convertView.setBackgroundColor(Color.WHITE)
            else -> convertView.setBackgroundColor(Color.LTGRAY)
        }

        //日曜日を赤、土曜日を青に
        val colorId: Int = when (mDateManager.getDayOfWeek(dateArray[position])) {
            1 -> Color.RED
            7 -> Color.BLUE

            else -> Color.BLACK
        }
        holder.dateText!!.setTextColor(colorId)

        return convertView
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    //翌月表示
    fun nextMonth() {
        mDateManager.nextMonth()
        dateArray = mDateManager.days
        this.notifyDataSetChanged()
    }

    //前月表示
    fun prevMonth() {
        mDateManager.prevMonth()
        dateArray = mDateManager.days
        this.notifyDataSetChanged()
    }

    private fun searchPlan(date: String): String {
        var plan: String

        dbAdapter = DBAdapter(this.mContext)
        dbAdapter.readDB()                         // DBの読み込み(読み込みの方)

        val column = "date"          //検索対象のカラム名
        val name = arrayOf(date)            //検索対象の文字

        // DBの検索データを取得 入力した文字列を参照してDBの品名から検索
        val c = dbAdapter.searchDB("remainder", null, column, name)

        c.moveToFirst()
        try {
            plan = c.getString(2)
            Log.d(date, plan)
        } catch (e: CursorIndexOutOfBoundsException) {

            Log.d(date, "null")
            plan = ""
        }

        c.close()
        dbAdapter.closeDB()        // DBを閉じる

        return plan
    }

    companion object {

        var dateArray: List<Date> = ArrayList()
    }

}
