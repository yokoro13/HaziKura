package com.example.dev.hazikura.fragment.Household;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dev.hazikura.R;

public class InputFragment extends Fragment {

    private MyListener myListener;

    public static InputFragment newInstance() {
        InputFragment fragment = new InputFragment();
        return fragment;
    }

    public interface MyListener {
        public void onClickButton();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MyListener){
            myListener = (MyListener) context;
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        myListener = null;
    }



    @Override
    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        view.findViewById(R.id.dateButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(myListener != null){
                    myListener.onClickButton();
                }
            }
        });
    }

    @Override
    public void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

}

