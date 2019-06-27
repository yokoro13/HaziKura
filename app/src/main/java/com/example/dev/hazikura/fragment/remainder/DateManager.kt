package com.example.dev.hazikura.fragment.remainder

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by yokoro
 */

class DateManager {
    internal var mCalendar: Calendar = Calendar.getInstance()

    //当月の要素を取得

    //現在の状態を保持
    //GridViewに表示するマスの合計を計算
    //当月のカレンダーに表示される前月分の日数を計算
    //状態を復元
    val days: List<Date>
        get() {
            val startDate = mCalendar.time
            val count = weeks * 7
            mCalendar.set(Calendar.DATE, 1)
            val dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK) - 1
            mCalendar.add(Calendar.DATE, -dayOfWeek)
            val days = ArrayList<Date>()
            for (i in 0 until count) {
                days.add(mCalendar.time)
                mCalendar.add(Calendar.DATE, 1)
            }
            mCalendar.time = startDate

            return days
        }

    //週数を取得
    val weeks: Int
        get() = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH)

    //当月かどうか確認
    fun isCurrentMonth(date: Date): Boolean {
        val format = SimpleDateFormat("yyyy.MM", Locale.US)
        val currentMonth = format.format(mCalendar.time)
        return currentMonth == format.format(date)
    }

    //曜日を取得
    fun getDayOfWeek(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    //翌月へ
    fun nextMonth() {
        mCalendar.add(Calendar.MONTH, 1)
    }

    //前月へ
    fun prevMonth() {
        mCalendar.add(Calendar.MONTH, -1)
    }
}
