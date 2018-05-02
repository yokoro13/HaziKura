package com.example.dev.hazikura.fragment.Household;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dev.hazikura.R;
import com.example.dev.hazikura.fragment.Management.ManagementFragment;

/**
     * Created by dev on 2018/03/12.
     */
public class HouseholdFragment extends Fragment {

        public HouseholdFragment() {
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_household, container, false);
            View button = root.findViewById(R.id.loadButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();

                    transaction.replace(R.id.main_container, new ManagementFragment());

                    transaction.commit();
                }
            });
            return root;
        }


}

