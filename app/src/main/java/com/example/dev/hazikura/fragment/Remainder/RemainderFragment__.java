package com.example.dev.hazikura.fragment.Remainder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.dev.hazikura.R;

public class RemainderFragment__ extends Fragment{

    private TextView titleText;
    private Button prevButton, nextButton;
    private CalendarAdapter__ mCalendarAdapter;
    private GridView calendarGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remainder, null);

        titleText = (TextView)View.findViewById(R.id.titleText);
        //prevButton = (Button) View.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.prevMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });
        //nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.nextMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });
        //calendarGridView = findViewById(R.id.calendarGridView);
        //mCalendarAdapter = new CalendarAdapter__(this);
        calendarGridView.setAdapter(mCalendarAdapter);
        titleText.setText(mCalendarAdapter.getTitle());

        return view;
    }

    @Override
    public void onClick(View view) {

    }*/
}

