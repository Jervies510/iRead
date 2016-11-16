package com.jervies.iread.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Jervies on 2016/11/15.
 */

public class MyViewPagerAdapterMain extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;
    private String[] mTabs;

    public MyViewPagerAdapterMain(FragmentManager fm, ArrayList<Fragment> fragments, String[] tabs) {
        super(fm);
        mFragments = fragments;
        mTabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position];
    }
}
