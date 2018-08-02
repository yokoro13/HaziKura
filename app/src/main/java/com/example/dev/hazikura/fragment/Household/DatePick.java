package com.example.dev.hazikura.fragment.Household;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.example.dev.hazikura.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by yokoro
 **/

public class DatePick extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this, year, month, dayOfMonth);

        return datePickerDialog;
    }

    public void onDateSet(android.widget.DatePicker view, int y, int m, int d) {
        final TextView textView = getActivity().findViewById(R.id.input_date);
        String str = String.format(Locale.US, "%d/%d/%d", y, m+1, d);
        textView.setText(str);
    }

}