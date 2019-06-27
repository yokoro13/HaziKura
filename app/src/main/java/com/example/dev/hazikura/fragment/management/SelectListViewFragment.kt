package com.example.dev.hazikura.fragment.management


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

import com.example.dev.hazikura.R
import com.example.dev.hazikura.fragment.household.DBAdapter

import java.util.ArrayList

/**
 * Created by yokoro
 */

class SelectListViewFragment : Fragment() {
    private lateinit var dbAdapter: DBAdapter
    private lateinit var myBaseAdapter: MyBaseAdapter
    private lateinit var listItems: MutableList<MyListItem>
    private lateinit var mListView: ListView
    private lateinit var myListItem: MyListItem

    private lateinit var columns: Array<String>
    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.select_listview, container, false)

        dbAdapter = DBAdapter(activity!!)
        listItems = ArrayList()

        myBaseAdapter = MyBaseAdapter(listItems)
        mListView = rootView.findViewById<View>(R.id.listView_management) as ListView

        loadMyList()

        mListView.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("削除")
            builder.setMessage("削除しますか？")

            builder.setPositiveButton("OK") { _, _ ->
                myListItem = listItems[position]
                val listID = myListItem.getId()

                dbAdapter.openDB()
                dbAdapter.selectDelete("income", listID.toString())
                Log.d("Long Click:", listID.toString())
                dbAdapter.closeDB()
                loadMyList()
            }
            builder.setNegativeButton("キャンセル") { _, _ -> }
            val dialog = builder.create()
            dialog.show()
            false
        }

        return rootView
    }

    private fun loadMyList() {
        listItems.clear()
        dbAdapter.openDB()

        val c = dbAdapter.getDB("income", columns)

        if (c.moveToFirst()) {
            do {
                myListItem = MyListItem(
                        c.getInt(0), //id
                        c.getString(1), //date
                        c.getString(2), //content
                        c.getString(3))//amount

                Log.d("取得ID:", c.getInt(0).toString())
                Log.d("取得ID:", c.getString(1))
                Log.d("取得ID:", c.getString(2))
                Log.d("取得ID:", c.getString(3))

                listItems.add(myListItem)

            } while (c.moveToNext())
        }
        c.close()
        dbAdapter.closeDB()
        mListView.adapter = myBaseAdapter
        myBaseAdapter.notifyDataSetChanged()
        Log.d("notify:", "list更新")
    }

    inner class MyBaseAdapter// コンストラクタの生成
    (private val items: List<MyListItem>) : BaseAdapter() {

        // 毎回findViewByIdをする事なく、高速化が出来るようするholderクラス
        private inner class ViewHolder {
            internal var textDate: TextView? = null
            internal var textContent: TextView? = null
            internal var textAmount: TextView? = null
        }

        init {
            Log.d("contract", "コントラクタ通過")
        }

        // Listの要素数を返す
        override fun getCount(): Int {
            return items.size
        }

        // indexやオブジェクトを返す
        override fun getItem(position: Int): Any {
            return items[position]
        }

        // IDを他のindexに返す
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        // 新しいデータが表示されるタイミングで呼び出される
        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {

            var view: View? = convertView
            val holder: ViewHolder

            // データを取得
            myListItem = items[position]


            if (view == null) {
                val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view = inflater.inflate(R.layout.row_listview, parent, false)

                val textDate = view!!.findViewById<View>(R.id.text_inputDate) as TextView
                val textContent = view.findViewById<View>(R.id.text_inputContent) as TextView
                val textAmount = view.findViewById<View>(R.id.text_inputAmount) as TextView

                // holderにviewを持たせておく
                holder = ViewHolder()
                holder.textDate = textDate
                holder.textContent = textContent
                holder.textAmount = textAmount
                view.tag = holder

                Log.d("getView", "view get")

            } else {
                // 初めて表示されるときにつけておいたtagを元にviewを取得する
                holder = view.tag as ViewHolder
            }

            // 取得した各データを各TextViewにセット
            holder.textDate!!.text = myListItem.date
            holder.textContent!!.text = myListItem.content
            holder.textAmount!!.text = myListItem.amount

            Log.d("setView", "view set")
            return view
        }
    }

    companion object {


        fun newInstance(): SelectListViewFragment {
            return SelectListViewFragment()
        }
    }
}