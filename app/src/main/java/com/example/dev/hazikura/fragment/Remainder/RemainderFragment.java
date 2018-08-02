package com.example.dev.hazikura.fragment.Remainder;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev.hazikura.R;
import com.example.dev.hazikura.fragment.Household.DBAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yokoro
 **/

public class RemainderFragment extends Fragment {

    DBAdapter dbAdapter;

    private TextView titleText;
    private Button prevButton, nextButton;
    private CalAdapter mCalendarAdapter;
    private GridView calendarGridView;
    private String selectedGridDate;
    private TextView setDate;
    private EditText editPlan;
    private EditText editPlace;
    private Date date;
    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_remainder, null);

        setDate = rootView.findViewById(R.id.titleGetDate);
        editPlan = rootView.findViewById(R.id.editPlan);
        editPlace = rootView.findViewById(R.id.editPlace);

        titleText = rootView.findViewById(R.id.titleText);
        prevButton = rootView.findViewById(R.id.prevButton);



        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.prevMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });

        nextButton = rootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.nextMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });


        calendarGridView = rootView.findViewById(R.id.calendarGridView);
        mCalendarAdapter = new CalAdapter(getActivity());
        calendarGridView.setAdapter(mCalendarAdapter);
        titleText.setText(mCalendarAdapter.getTitle());

        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                date = (CalAdapter.dateArray.get(position));

                selectedGridDate = new SimpleDateFormat("yyyy/MM/dd").format(date);

                setDate.setText(selectedGridDate);

                if(setDate.getText().toString() != "日付を選択してください") {
                    editPlan.setText(searchPlan(selectedGridDate));
                    editPlace.setText(searchPlace(selectedGridDate));
                }
            }
        });

        Button button = (Button) rootView.findViewById(R.id.write_plan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveList();
            }
        });
        return rootView;
    }

    public String searchPlan(String date) {
        String plan;

        dbAdapter = new DBAdapter(getActivity());
        dbAdapter.readDB();                         // DBの読み込み(読み込みの方)

        String column = "date";          //検索対象のカラム名
        String[] name = {date};            //検索対象の文字

        // DBの検索データを取得 入力した文字列を参照してDBの品名から検索
        Cursor c = dbAdapter.searchDB("remainder",null, column, name);

        c.moveToFirst();
        try{
            plan = c.getString(2);
        }
        catch (CursorIndexOutOfBoundsException e){

            Log.d("search","null");
            plan = "";
        }
        c.close();
        dbAdapter.closeDB();        // DBを閉じる

        return plan;
    }

    public String searchPlace(String date) {
        String place;

        dbAdapter = new DBAdapter(getActivity());
        dbAdapter.readDB();                         // DBの読み込み(読み込みの方)

        String column = "date";          //検索対象のカラム名
        String[] name = {date};            //検索対象の文字

        // DBの検索データを取得 入力した文字列を参照してDBの品名から検索
        Cursor c = dbAdapter.searchDB("remainder",null, column, name);

        c.moveToFirst();
        try{
            place = c.getString(3);
        }
        catch (CursorIndexOutOfBoundsException e){
            Log.d("search","null");
            place = "";
        }
        c.close();
        dbAdapter.closeDB();        // DBを閉じる

        return place;
    }

    private void saveList(){

        String strDate = setDate.getText().toString();
        String strPlan = editPlan.getText().toString();
        String strPlace = editPlace.getText().toString();
        if(strPlan == "" ){
            Log.d("strPlan:","empty");
        }

        Log.d("strDate",strDate);
        Log.d("strPlan",strPlace);
        Log.d("strPlace",strPlace);

        if(strDate.equals("日付を選択してください")) {
            Toast.makeText(getActivity(), "日付を選択してください", Toast.LENGTH_SHORT).show();
        } else {

            dbAdapter = new DBAdapter(getActivity());
            dbAdapter.openDB();
            dbAdapter.updatePlans(strDate, strPlan, strPlace);
            Log.d("search", "updated");

            Log.d("Write remainder:", strDate);
            Log.d("write remainder:", strPlan);
            Log.d("write remainder:", strPlace);
            dbAdapter.closeDB();

            mCalendarAdapter.notifyDataSetChanged();
            calendarGridView.setAdapter(mCalendarAdapter);
        }
    }
}

