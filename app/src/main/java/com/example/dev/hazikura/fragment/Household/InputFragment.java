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

public class InputFragment extends Fragment {

    private MyListener myListener;

    private TextView date;
    private EditText content;
    private EditText amount;
    View rootView;

    public static InputFragment newInstance() {
        InputFragment fragment = new InputFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_input, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);

        view.findViewById(R.id.dateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myListener != null) {
                    myListener.onClickButton();
                }
            }
        });
    }

    @Override
    public  void onStart(){
        super.onStart();

        findView();
        init();
        Button button = (Button)getActivity().findViewById(R.id.write_input);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // DBに登録
                saveList();
            }
        });
    }

    public interface MyListener {
        void onClickButton();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyListener) {
            myListener = (MyListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myListener = null;
    }

    private void findView(){
        date = (TextView) getActivity().findViewById(R.id.input_date);
        content = (EditText) getActivity().findViewById(R.id.input_content);
        amount = (EditText) getActivity().findViewById(R.id.input_amount);
    }

    private void init(){
        content.setText("");
        amount.setText("");
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void saveList(){
        String strDate = date.getText().toString();
        String strContent = content.getText().toString();
        String strAmount = amount.getText().toString();

        if(strDate.equals("日付を選択してください") || strAmount.equals("") || strContent.equals("")) {
            Toast.makeText(getActivity(), "全ての箇所を入力してください", Toast.LENGTH_SHORT).show();
        } else {
            int iAmount = Integer.parseInt(strAmount);
            DBAdapter dbAdapter = new DBAdapter(getActivity());
            dbAdapter.openDB();
            dbAdapter.saveIncome(strDate, strContent, iAmount);
            dbAdapter.closeDB();

            init();
        }
    }

}