package com.example.dev.hazikura.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.dev.hazikura.R;
import com.example.dev.hazikura.fragment.Household.HouseholdFragment;
import com.example.dev.hazikura.fragment.Management.ManagementFragment;
import com.example.dev.hazikura.fragment.Map.MapFragment;
import com.example.dev.hazikura.fragment.Option.OptionFragment;
import com.example.dev.hazikura.fragment.Remainder.RemainderFragment;

public class Main extends AppCompatActivity {
    private static final String TAG = Main.class.getSimpleName();
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    private Intent intent;
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
                                /**intent = new Intent(getApplication(),HouseholdMain.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);**/
                                break;
                            case R.id.daily_necessities_management:
                                fragment = new ManagementFragment();
                                break;
                            case R.id.map_display:
                                fragment = new MapFragment();
                                /**intent = new Intent(getApplication(),MapMain.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);**/
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
