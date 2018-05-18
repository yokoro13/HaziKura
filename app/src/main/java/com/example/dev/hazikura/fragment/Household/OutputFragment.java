package com.example.dev.hazikura.fragment.Household;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dev.hazikura.R;

public class OutputFragment extends Fragment {
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
}
