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

class OutputFragment : Fragment() {

    private var myListener2: MyListener2? = null
    private lateinit var date: TextView
    private lateinit var content: EditText
    private lateinit var number: EditText
    private lateinit var amount: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_output, container, false)

    }

    override fun onViewCreated(view: View, saveInstanceState: Bundle?) {
        super.onViewCreated(view, saveInstanceState)

        view.findViewById<View>(R.id.dateButton2).setOnClickListener {
            if (myListener2 != null) {
                myListener2!!.onClickButton2()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        findView()
        init()
        val button = activity?.findViewById<View>(R.id.write_output) as Button
        button.setOnClickListener {
            // DBに登録
            saveList()
        }
    }

    interface MyListener2 {
        fun onClickButton2()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MyListener2) {
            myListener2 = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        myListener2 = null
    }

    private fun findView() {
        date = activity?.findViewById<View>(R.id.output_date) as TextView
        content = activity?.findViewById<View>(R.id.output_content) as EditText
        number = activity?.findViewById<View>(R.id.output_number) as EditText
        amount = activity?.findViewById<View>(R.id.output_amount) as EditText
    }

    private fun init() {
        content.setText("")
        number.setText("")
        amount.setText("")
    }

    private fun saveList() {
        val strDate = date.text.toString()
        val strContent = content.text.toString()
        val strNumber = number.text.toString()
        val strAmount = amount.text.toString()

        if (strDate == "日付を選択してください" || strAmount == "" || strNumber == "" || strContent == "") {
            Toast.makeText(activity, "全ての箇所を入力してください", Toast.LENGTH_SHORT).show()
        } else {
            val iNumber = Integer.parseInt(strNumber)
            val iAmount = Integer.parseInt(strAmount)
            val dbAdapter = DBAdapter(activity!!)
            dbAdapter.openDB()
            dbAdapter.saveOutgo(strDate, strContent, iNumber, iAmount)
            dbAdapter.closeDB()

            init()
        }
    }

    companion object {

        fun newInstance(): OutputFragment {
            return OutputFragment()
        }
    }
}
