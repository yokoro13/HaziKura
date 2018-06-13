package com.example.dev.hazikura.activity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.VoiceInteractor;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;


import com.example.dev.hazikura.R;
import com.example.dev.hazikura.fragment.Household.CustomDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CalenderInputActivity extends Fragment {//implements OnClickListener, DatePickerDialog.OnDateSetListener{
/**
    private TextView mDate;
    Context mContext;
    private SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remainder, container, false);
        mContext = getActivity();
        mDate = (TextView) view.findViewById(R.id.date);
        mDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = mDate.getText().toString();

                int year = 0;
                int month = 0;
                int dayOfMonth = 0;
                if (TextUtils.isEmpty(date)) {
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                } else {
                    year = Integer.valueOf(date.substring(0, 4));
                    month = Integer.valueOf(date.substring(5, 7));
                    month = month - 1;
                    dayOfMonth = Integer.valueOf(date.substring(8, 10));
                }
                showDatePickerDialog(year, month, dayOfMonth);
            }
        });

    }

    @Override
    public void onClick(View view){

    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        String str = String.format(Locale.US, "%d/%d/%d",year, monthOfYear, dayOfMonth);
        TextView.setText( str );

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePick();
        newFragment.show((FragmentActivity) mContext).getSupportFragmentManager(), "datePicker");

    }**/
}
