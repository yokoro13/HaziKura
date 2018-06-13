package com.example.dev.hazikura.fragment.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.dev.hazikura.R;

/**
 * Created by dev on 2018/03/12.
 */

public class MapFragment extends Fragment{

    public MapFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    /**public MapFragment() {
    }
    private GoogleMap mMap;
    public MainBranchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.main_branch_map);
        mapFragment.getMapAsync(this);
        return view;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng UCA = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(UCA).title("YOUR TITLE")).showInfoWindow();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(UCA,17));

    }
}**/

}
