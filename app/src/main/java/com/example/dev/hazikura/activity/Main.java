package com.example.dev.hazikura.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.dev.hazikura.R;
import com.example.dev.hazikura.fragment.HouseholdFragment;
import com.example.dev.hazikura.fragment.ManagementFragment;
import com.example.dev.hazikura.fragment.MapFragment;
import com.example.dev.hazikura.fragment.OptionFragment;
import com.example.dev.hazikura.fragment.RemainderFragment;

public class Main extends AppCompatActivity {
    int x;
    private static final String TAG = Main.class.getSimpleName();
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = new HouseholdFragment();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
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
                            case R.id.option:
                                fragment = new OptionFragment();
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
}
