package com.example.dev.hazikura.fragment.Household;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev.hazikura.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateSetFragment extends Fragment {
    private TextView mDate;

    public DateSetFragment() {

    }


    public static DateSetFragment newInstance() {
        DateSetFragment fragment = new DateSetFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }



    @Override
    public void onStart() {
        super.onStart();

        Button button = (Button)getActivity().findViewById(R.id.loadButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



            }
        });

    }

}
