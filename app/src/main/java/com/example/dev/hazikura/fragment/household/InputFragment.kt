package com.example.dev.hazikura.fragment.household

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.example.dev.hazikura.R

/**
 * Created by yokoro
 */

class InputFragment : Fragment() {

    private var myListener: MyListener? = null

    private lateinit var date: TextView
    private lateinit var content: EditText
    private lateinit var amount: EditText
    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, saveInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_input, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, saveInstanceState: Bundle?) {
        super.onViewCreated(view, saveInstanceState)

        view.findViewById<View>(R.id.dateButton).setOnClickListener {
            if (myListener != null) {
                myListener!!.onClickButton()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        findView()
        init()
        val button = activity?.findViewById<View>(R.id.write_input) as Button
        button.setOnClickListener {
            // DBに登録
            saveList()
        }
    }

    interface MyListener {
        fun onClickButton()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MyListener) {
            myListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        myListener = null
    }

    private fun findView() {
        date = activity?.findViewById<View>(R.id.input_date) as TextView
        content = activity?.findViewById<View>(R.id.input_content) as EditText
        amount = activity?.findViewById<View>(R.id.input_amount) as EditText
    }

    private fun init() {
        content.setText("")
        amount.setText("")
    }

    private fun saveList() {
        val strDate = date.text.toString()
        val strContent = content.text.toString()
        val strAmount = amount.text.toString()

        if (strDate == "日付を選択してください" || strAmount == "" || strContent == "") {
            Toast.makeText(activity, "全ての箇所を入力してください", Toast.LENGTH_SHORT).show()
        } else {
            val iAmount = Integer.parseInt(strAmount)
            val dbAdapter = DBAdapter(activity!!)
            dbAdapter.openDB()
            dbAdapter.saveIncome(strDate, strContent, iAmount)
            dbAdapter.closeDB()

            init()
        }
    }

    companion object {

        fun newInstance(): InputFragment {
            return InputFragment()
        }
    }

}