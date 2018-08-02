package com.example.dev.hazikura.fragment.Household;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev.hazikura.R;

/**
 * Created by yokoro
 **/

public class OutputFragment extends Fragment {

    private MyListener2 myListener2;
    private TextView date;
    private EditText content;
    private EditText number;
    private EditText amount;

    public static OutputFragment newInstance() {
        OutputFragment fragment = new OutputFragment();
        return fragment;
    }

    @Override
    public void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_output, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        view.findViewById(R.id.dateButton2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(myListener2 != null){
                    myListener2.onClickButton2();
                }
            }
        });
    }

    @Override
    public  void onStart(){
        super.onStart();

        findView();
        init();
        Button button = (Button)getActivity().findViewById(R.id.write_output);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // DBに登録
                saveList();
            }
        });
    }

    public interface MyListener2 {
        void onClickButton2();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MyListener2){
            myListener2 = (MyListener2) context;
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        myListener2 = null;
    }

    private void findView(){
        date = (TextView) getActivity().findViewById(R.id.output_date);
        content = (EditText) getActivity().findViewById(R.id.output_content);
        number = (EditText) getActivity().findViewById(R.id.output_number);
        amount = (EditText) getActivity().findViewById(R.id.output_amount);
    }

    private void init(){
        content.setText("");
        number.setText("");
        amount.setText("");
    }

    private void saveList(){
        String strDate = date.getText().toString();
        String strContent = content.getText().toString();
        String strNumber = number.getText().toString();
        String strAmount = amount.getText().toString();

        if(strDate.equals("日付を選択してください") || strAmount.equals("") || strNumber.equals("") || strContent.equals("")) {
            Toast.makeText(getActivity(), "全ての箇所を入力してください", Toast.LENGTH_SHORT).show();
        } else {
            int iNumber = Integer.parseInt(strNumber);
            int iAmount = Integer.parseInt(strAmount);
            DBAdapter dbAdapter = new DBAdapter(getActivity());
            dbAdapter.openDB();
            dbAdapter.saveOutgo(strDate, strContent, iNumber, iAmount);
            dbAdapter.closeDB();

            init();
        }
    }
}
