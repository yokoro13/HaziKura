package com.example.dev.hazikura.fragment.Option;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.dev.hazikura.R;

// Fragmentクラスを継承
public class OptionFragment extends Fragment {
    public OptionFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_option, container, false);
    }

}