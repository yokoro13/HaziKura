package com.example.dev.hazikura.fragment.remainder

import android.database.CursorIndexOutOfBoundsException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast

import com.example.dev.hazikura.R
import com.example.dev.hazikura.fragment.household.DBAdapter

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yokoro
 */

class RemainderFragment : Fragment() {

    private lateinit var dbAdapter: DBAdapter

    private lateinit var titleText: TextView
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var mCalendarAdapter: CalAdapter
    private lateinit var calendarGridView: GridView
    private lateinit var selectedGridDate: String
    private lateinit var setDate: TextView
    private lateinit var editPlan: EditText
    private lateinit var editPlace: EditText
    private lateinit var date: Date
    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_remainder, null)

        setDate = rootView.findViewById(R.id.titleGetDate)
        editPlan = rootView.findViewById(R.id.editPlan)
        editPlace = rootView.findViewById(R.id.editPlace)

        titleText = rootView.findViewById(R.id.titleText)
        prevButton = rootView.findViewById(R.id.prevButton)



        prevButton.setOnClickListener {
            mCalendarAdapter.prevMonth()
            titleText.text = mCalendarAdapter.title
        }

        nextButton = rootView.findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            mCalendarAdapter.nextMonth()
            titleText.text = mCalendarAdapter.title
        }


        calendarGridView = rootView.findViewById(R.id.calendarGridView)
        mCalendarAdapter = CalAdapter(activity!!)
        calendarGridView.adapter = mCalendarAdapter
        titleText.text = mCalendarAdapter.title

        calendarGridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            date = CalAdapter.dateArray[position]

            selectedGridDate = SimpleDateFormat("yyyy/MM/dd", Locale.US).format(date)

            setDate.text = selectedGridDate

            if (setDate.text.toString() !== "日付を選択してください") {
                editPlan.setText(searchPlan(selectedGridDate))
                editPlace.setText(searchPlace(selectedGridDate))
            }
        }

        val button = rootView.findViewById<View>(R.id.write_plan) as Button
        button.setOnClickListener { saveList() }
        return rootView
    }

    private fun searchPlan(date: String): String {
        val plan: String

        dbAdapter = DBAdapter(activity!!)
        dbAdapter.readDB()                         // DBの読み込み(読み込みの方)

        val column = "date"          //検索対象のカラム名
        val name = arrayOf(date)            //検索対象の文字

        // DBの検索データを取得 入力した文字列を参照してDBの品名から検索
        val c = dbAdapter.searchDB("remainder", null, column, name)

        c.moveToFirst()
        plan = try {
            c.getString(2)
        } catch (e: CursorIndexOutOfBoundsException) {

            Log.d("search", "null")
            ""
        }

        c.close()
        dbAdapter.closeDB()        // DBを閉じる

        return plan
    }

    private fun searchPlace(date: String?): String {
        val place: String

        dbAdapter = DBAdapter(activity!!)
        dbAdapter.readDB()                         // DBの読み込み(読み込みの方)

        val column = "date"          //検索対象のカラム名
        val name = arrayOf(date.toString())            //検索対象の文字

        // DBの検索データを取得 入力した文字列を参照してDBの品名から検索
        val c = dbAdapter.searchDB("remainder", null, column, name)

        c.moveToFirst()
        place = try {
            c.getString(3)
        } catch (e: CursorIndexOutOfBoundsException) {
            Log.d("search", "null")
            ""
        }

        c.close()
        dbAdapter.closeDB()        // DBを閉じる

        return place
    }

    private fun saveList() {

        val strDate = setDate.text.toString()
        val strPlan = editPlan.text.toString()
        val strPlace = editPlace.text.toString()
        if (strPlan === "") {
            Log.d("strPlan:", "empty")
        }

        if (strDate == "日付を選択してください") {
            Toast.makeText(activity, "日付を選択してください", Toast.LENGTH_SHORT).show()
        } else {

            if (searchPlan(strDate) !== "" || searchPlace(strDate) !== "") {
                dbAdapter = DBAdapter(activity!!)
                dbAdapter.openDB()
                dbAdapter.updatePlans(strDate, strPlan, strPlace)
            } else {
                dbAdapter = DBAdapter(activity!!)
                dbAdapter.openDB()
                dbAdapter.saveRemainder(strDate, strPlan, strPlace)
            }

            Log.d("Write remainder:", strDate)
            Log.d("write remainder:", strPlan)
            Log.d("write remainder:", strPlace)
            dbAdapter.closeDB()

            mCalendarAdapter.notifyDataSetChanged()
            calendarGridView.adapter = mCalendarAdapter
        }
    }
}

