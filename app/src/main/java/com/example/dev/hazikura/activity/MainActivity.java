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
import com.example.dev.hazikura.fragment.MainFragment;
import com.example.dev.hazikura.fragment.OptionFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView= (BottomNavigationView)
                findViewById(R.id.navigation);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.household_accounts:
                                fragment = new MainFragment();
                                break;
                            case R.id.b:
                                break;
                            case R.id.c:
                                break;
                            case R.id.d:
                                break;
                            case R.id.e:
                                fragment = new OptionFragment();
                                break;
                        }
                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container, fragment).commit();
                        return true;
                    }
                });
    }
}
