package com.example.dev.hazikura.fragment.Household;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dev.hazikura.R;

public class InputFragment extends Fragment {

    public static InputFragment newInstance() {
        InputFragment fragment = new InputFragment();
        return fragment;
    }

    @Override
    public void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        Button button = (Button)getActivity().findViewById(R.id.dateButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO テキストボックスクリックしたら、カレンダー表示するようにする
            }
        });
    }

}

