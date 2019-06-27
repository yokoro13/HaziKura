package com.example.dev.hazikura.fragment.household

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import android.widget.TextView

import com.example.dev.hazikura.R

import java.util.Calendar
import java.util.Locale

/**
 * Created by yokoro
 */

class DatePick2 : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity, this, year, month, dayOfMonth)
    }

    override fun onDateSet(view: DatePicker, y: Int, m: Int, d: Int) {
        val textView = activity?.findViewById<TextView>(R.id.output_date)
        val str = String.format(Locale.US, "%d/%d/%d", y, m + 1, d)
        textView?.text = str
    }

}