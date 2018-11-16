// これは何ですか．

package com.example.dev.hazikura.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.dev.hazikura.R;

import com.example.dev.hazikura.fragment.Household.DatePick;
import com.example.dev.hazikura.fragment.Household.DatePick2;
import com.example.dev.hazikura.fragment.Household.HouseholdFragment;
import com.example.dev.hazikura.fragment.Household.InputFragment;
import com.example.dev.hazikura.fragment.Household.OutputFragment;
import com.example.dev.hazikura.fragment.Management.ManagementFragment;
import com.example.dev.hazikura.fragment.Map.MapFragment;
import com.example.dev.hazikura.fragment.Remainder.RemainderFragment;
import com.google.android.gms.location.LocationRequest;


public class MainActivity extends AppCompatActivity implements InputFragment.MyListener, OutputFragment.MyListener2{
    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    private final int REQUEST_PERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_main);
        fragment = new HouseholdFragment();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.footer_menu);

        fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.household_accounts:
                                fragment = new HouseholdFragment();
                                break;
                            case R.id.daily_necessities_management:
                                fragment = new ManagementFragment();
                                break;
                            case R.id.map_display:
                                if(Build.VERSION.SDK_INT >= 23){
                                    checkPermission();
                                }
                                else{
                                    fragment = new MapFragment();
                                }
                                break;
                            case R.id.remainder:
                                fragment = new RemainderFragment();
                                break;
                        }
                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container, fragment).commit();
                        return true;
                    }
                });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, new HouseholdFragment());
        transaction.commit();
    }


    @Override
    protected void onResume(){
        super.onResume();
        //Menu menu = bottomNavigationView.getMenu();
        //MenuItem menuItem = menu.getItem(0);
        //menuItem.setChecked(true);
    }

    @Override
    public void onClickButton(){
        DatePick datePick = new DatePick();
        datePick.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onClickButton2(){
        DatePick2 datePick2 = new DatePick2();
        datePick2.show(getSupportFragmentManager(), "datePicker2");
    }

    // 位置情報許可の確認
    public void checkPermission() {
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fragment = new MapFragment();
            Log.d("MainActivity", "********thought permission*********");
        }
        // 拒否していた場合
        else{
            requestLocationPermission();
        }
    }

    // 許可を求める
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
            Log.d("MainActivity", "********thought permission**********");

        } else {
            Toast toast = Toast.makeText(this, "許可されないとアプリが実行できません", Toast.LENGTH_SHORT);
            toast.show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, REQUEST_PERMISSION);

        }
    }

    // 結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fragment = new MapFragment();
                return;

            } else {
                // それでも拒否された時の対応
                fragment = new HouseholdFragment();
            }
        }
    }

}
