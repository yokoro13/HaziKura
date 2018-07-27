package com.example.dev.hazikura.fragment.Management;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dev.hazikura.R;

/**
 * Created by dev on 2018/03/12.
 */

public class ManagementFragment extends Fragment{

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    public ManagementFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_management_, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager_manage);
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        return view;
    }

    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private static final int NUM_ITEMS = 2;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return SelectListViewFragment.newInstance();
            } else {
                return SelectListViewOutFragment.newInstance();
            }
        }

        @Override
        public CharSequence getPageTitle(int position){
            if (position == 0){
                return "収入";
            }
            else {
                return "支出";
            }
        }
    }

}