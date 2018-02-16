package com.example.soumyaagarwal.libraryontipsadmin.ViewBook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class pager extends FragmentStatePagerAdapter {
    //int tabCount;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public pager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        /*switch (position) {
            case 0:
                tab1 a = new tab1("IT");
                return a;
            case 1:
                tab1 b = new tab1("CS");
                return b;
            case 2:
                tab1 c = new tab1("MECHANICAL");
                return c;
            default:
                return null;
        }*/
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

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
}