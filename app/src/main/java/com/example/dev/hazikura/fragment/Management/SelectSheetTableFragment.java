package com.example.dev.hazikura.fragment.Management;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dev.hazikura.R;

public class SelectSheetTableFragment extends Fragment {

    View rootView;
    public static SelectSheetTableFragment newInstance() {
        SelectSheetTableFragment fragment = new SelectSheetTableFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        rootView = inflater.inflate(R.layout.select_table, container, false);
        return rootView;
    }
}
