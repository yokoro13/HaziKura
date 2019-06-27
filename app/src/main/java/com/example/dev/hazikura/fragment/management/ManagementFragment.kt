package com.example.dev.hazikura.fragment.management

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.dev.hazikura.R

/**
 * Created by yokoro
 */

class ManagementFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_management_, container, false)

        viewPager = view.findViewById<View>(R.id.viewpager_manage) as ViewPager
        viewPagerAdapter = ViewPagerAdapter(fragmentManager)
        viewPager.adapter = viewPagerAdapter
        return view
    }

    class ViewPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int {
            return NUM_ITEMS
        }

        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                SelectListViewFragment.newInstance()
            } else {
                SelectListViewOutFragment.newInstance()
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return if (position == 0) {
                "収入"
            } else {
                "支出"
            }
        }

        companion object {
            private const val NUM_ITEMS = 2
        }
    }

}