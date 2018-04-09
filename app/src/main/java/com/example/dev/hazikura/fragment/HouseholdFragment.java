package com.example.dev.hazikura.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dev.hazikura.R;

    /**
     * Created by dev on 2018/03/12.
     */
public class HouseholdFragment extends Fragment {

        public HouseholdFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_household, container, false);
        }
}

