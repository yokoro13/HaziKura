package com.example.dev.hazikura.fragment.Household;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.example.dev.hazikura.activity.CalenderInputActivity;

public class CustomDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static CustomDialogFragment newInstance(int year, int month, int dayOfMonth) {
        CustomDialogFragment frag = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("dayOfMonth", dayOfMonth);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = getArguments().getInt("year");
        int month = getArguments().getInt("month");
        int dayOfMonth = getArguments().getInt("dayOfMonth");

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), this, year, month, dayOfMonth);

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Activity activity = getActivity();
        //if (activity instanceof CalenderInputActivity) {
        //    ((CalenderInputActivity)activity).setDate(year, month, dayOfMonth);
        //}
    }

}

