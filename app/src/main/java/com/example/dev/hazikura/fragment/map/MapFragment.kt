package com.example.dev.hazikura.fragment.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.example.dev.hazikura.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

/**
 * Created by yokoro
 */

class MapFragment : Fragment(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        //CameraUpdate update = CameraUpdateFactory.zoomBy(3);
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val jpn = LatLng(34.6, 135.5)
        val update = CameraUpdateFactory.newLatLng(jpn)

        mMap!!.moveCamera(update)

    }
}


