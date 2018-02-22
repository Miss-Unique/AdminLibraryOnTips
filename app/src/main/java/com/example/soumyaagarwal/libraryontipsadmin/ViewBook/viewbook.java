package com.example.soumyaagarwal.libraryontipsadmin.ViewBook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.view.View;
import android.widget.EditText;

import com.example.soumyaagarwal.libraryontipsadmin.R;
import com.example.soumyaagarwal.libraryontipsadmin.admin_page;

public class viewbook extends AppCompatActivity {

    private TabLayout tab;
    private ViewPager vpager;
    private EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbook);

        tab = (TabLayout) findViewById(R.id.tabLayout);
        search = (EditText) findViewById(R.id.search);
        vpager = (ViewPager) findViewById(R.id.pager);

        final pager adapter = new pager(getSupportFragmentManager());
        adapter.addFragment(new tab1("All"), "All");
        adapter.addFragment(new tab1("Information Technology"), "IT");
        adapter.addFragment(new tab1("Computer Science"), "CS");
        adapter.addFragment(new tab1("Mechanical Engineering"), "MECHANICAL");
        adapter.addFragment(new tab1("Electrical Engineering"), "ELECTRICAL");

        vpager.setAdapter(adapter);

        tab.setupWithViewPager(vpager);

  /*      tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

       /* vpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                search.setText("");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

       tab.setOnScrollChangeListener(new View.OnScrollChangeListener() {
           @Override
           public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
               search.setText("");
           }
       });

        vpager.setOffscreenPageLimit(3);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getItem(tab.getSelectedTabPosition()).onTextChanged(s,start,before,count);

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getItem(tab.getSelectedTabPosition()).afterTextChanged(s);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),admin_page.class);
        startActivity(intent);
        finish();
    }
}