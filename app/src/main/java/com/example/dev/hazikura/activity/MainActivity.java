package com.example.dev.hazikura.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.dev.hazikura.R;

import com.example.dev.hazikura.fragment.Household.DatePick;
import com.example.dev.hazikura.fragment.Household.DatePick2;
import com.example.dev.hazikura.fragment.Household.HouseholdFragment;
import com.example.dev.hazikura.fragment.Household.InputFragment;
import com.example.dev.hazikura.fragment.Household.OutputFragment;
import com.example.dev.hazikura.fragment.Management.ManagementFragment;
import com.example.dev.hazikura.fragment.Map.MapFragment;
import com.example.dev.hazikura.fragment.Remainder.RemainderFragment;


public class MainActivity extends AppCompatActivity implements InputFragment.MyListener, OutputFragment.MyListener2{
    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

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
                                fragment = new MapFragment();
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
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
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
}
