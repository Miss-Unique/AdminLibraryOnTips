package com.example.soumyaagarwal.libraryontipsadmin.ViewBook;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class pager extends FragmentPagerAdapter {
    //int tabCount;
    private final List<tab1> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    tab1 x;

    public pager(FragmentManager fm) {
        super(fm);
    }


    public tab1 getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFragment(tab1 fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
}