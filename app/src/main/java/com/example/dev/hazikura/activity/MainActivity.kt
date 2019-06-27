package com.example.dev.hazikura.activity

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager

import com.example.dev.hazikura.R

import com.example.dev.hazikura.fragment.household.DatePick
import com.example.dev.hazikura.fragment.household.DatePick2
import com.example.dev.hazikura.fragment.household.HouseholdFragment
import com.example.dev.hazikura.fragment.household.InputFragment
import com.example.dev.hazikura.fragment.household.OutputFragment
import com.example.dev.hazikura.fragment.management.ManagementFragment
import com.example.dev.hazikura.fragment.map.MapFragment
import com.example.dev.hazikura.fragment.remainder.RemainderFragment


class MainActivity : AppCompatActivity(), InputFragment.MyListener, OutputFragment.MyListener2 {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setContentView(R.layout.activity_main)
        fragment = HouseholdFragment()
        bottomNavigationView = (findViewById<View>(R.id.footer_menu) as BottomNavigationView)

        fragmentManager = supportFragmentManager
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.household_accounts -> fragment = HouseholdFragment()
                R.id.daily_necessities_management -> fragment = ManagementFragment()
                R.id.map_display -> fragment = MapFragment()
                R.id.remainder -> fragment = RemainderFragment()
            }
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, fragment).commit()
            true
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, HouseholdFragment())
        transaction.commit()
    }


    override fun onResume() {
        super.onResume()
        val menu = bottomNavigationView.menu
        val menuItem = menu.getItem(0)
        menuItem.isChecked = true
    }

    override fun onClickButton() {
        val datePick = DatePick()
        datePick.show(supportFragmentManager, "datePicker")
    }

    override fun onClickButton2() {
        val datePick2 = DatePick2()
        datePick2.show(supportFragmentManager, "datePicker2")
    }
}
