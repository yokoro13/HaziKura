package com.example.dev.hazikura.fragment.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import android.location.Address;
import android.location.Geocoder;

import com.example.dev.hazikura.R;
import com.example.dev.hazikura.fragment.Household.DBAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;


/**
 * Created by yokoro
 **/

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMyLocationButtonClickListener, LocationSource {

    DBAdapter dbAdapter;

    private LatLng placeMarker;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;

    private OnLocationChangedListener onLocationChangedListener = null;

    private int priority[] = {LocationRequest.PRIORITY_HIGH_ACCURACY, LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
            LocationRequest.PRIORITY_LOW_POWER, LocationRequest.PRIORITY_NO_POWER};
    private int locationPriority;

    private SupportMapFragment mapFragment;
    MarkerOptions options;
    private  View view;

    private Date date;
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    Calendar calendar = Calendar.getInstance();

    public MapFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_map, container, false);
        locationRequest = LocationRequest.create();
        locationPriority = priority[1];


        if(locationPriority == priority[0]){
            locationRequest.setPriority(locationPriority);
            locationRequest.setInterval(5000);
            locationRequest.setFastestInterval(16);
        }
        else if(locationPriority == priority[1]){
            // 消費電力を考慮する場合
            locationRequest.setPriority(locationPriority);
            locationRequest.setInterval(60000);
            locationRequest.setFastestInterval(16);
        }
        else if(locationPriority == priority[2]){
            // "city" level accuracy
            locationRequest.setPriority(locationPriority);
        }
        else{
            // 外部からのトリガーでの測位のみ
            locationRequest.setPriority(locationPriority);
        }

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //CameraUpdate update = CameraUpdateFactory.zoomBy(3);


        mapFragment.getMapAsync(this);



        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // check permission
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d("debug", "permission granted");

            mMap = googleMap;
            // default の LocationSource から自前のsourceに変更する
            mMap.setLocationSource(this);
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);

            int i = 0;
            date = new Date();
            for (calendar.setTime(date),i = 0;i < 15;calendar.add(Calendar.DATE, 1),i++){
                Log.d("readDate",format.format(calendar.getTime()));
                setMarker(format.format(calendar.getTime()));
            }
        }
        else{
            Log.d("debug", "permission error");
            return;
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("debug","onLocationChanged");
        if (onLocationChangedListener != null) {
            onLocationChangedListener.onLocationChanged(location);

            double lat = location.getLatitude();
            double lng = location.getLongitude();

            Log.d("debug","location="+lat+","+lng);

            Toast.makeText(getActivity(), "location="+lat+","+lng, Toast.LENGTH_SHORT).show();

            // Add a marker and move the camera
            LatLng newLocation = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(newLocation).title("My Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));

        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // check permission
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d("debug", "permission granted");

            // FusedLocationApi
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, locationRequest, this);
        }
        else{
            Log.d("debug", "permission error");
            return;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("debug", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("debug", "onConnectionFailed");
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(getActivity(), "onMyLocationButtonClick", Toast.LENGTH_SHORT).show();

        return false;
    }

    // OnLocationChangedListener calls activate() method
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        this.onLocationChangedListener = null;
    }


    /***
     * place にマーカーをおく
     */
    public void setMarker(String dt){
        String place = searchPlace(dt);

        if (!place.equals("")) {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.JAPAN);
            List<Address> lstAddr;
            try {
                lstAddr = geocoder.getFromLocationName(place, 1);
                if (lstAddr != null && lstAddr.size() > 0) {
                    Address addr = lstAddr.get(0);
                    placeMarker = new LatLng(addr.getLatitude(), addr.getLongitude());
                    options = new MarkerOptions();
                    options.position(placeMarker);
                    mMap.addMarker(options.title(place + "/" +dt));
                }
            } catch (IOException e) {
                Log.d("setMarker","変換に失敗");
                e.printStackTrace();
            }
        }
    }

    public String searchPlace(String date) {
        String place = "";

        dbAdapter = new DBAdapter(getActivity());
        dbAdapter.readDB();                         // DBの読み込み(読み込みの方)

        String column = "date";          //検索対象のカラム名
        String[] name = {date};            //検索対象の文字

        // DBの検索データを取得 入力した文字列を参照してDBの品名から検索
        Cursor c = dbAdapter.searchDB("remainder",null, column, name);

        c.moveToFirst();
        try{
            place = c.getString(3);
            Log.d("mapPlace", place);
        }
        catch (CursorIndexOutOfBoundsException e){
            Log.d("search","null");
            Log.d("mapDate",date);
            place = "";
        }
        c.close();
        dbAdapter.closeDB();        // DBを閉じる

        return place;
    }
}


