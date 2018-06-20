package com.example.dev.hazikura.fragment.Household;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dev.hazikura.R;

public class OutputFragment extends Fragment {

    private MyListener2 myListener2;

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
}
