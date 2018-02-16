package com.example.soumyaagarwal.libraryontipsadmin.ViewBook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;

import com.example.soumyaagarwal.libraryontipsadmin.R;
import com.example.soumyaagarwal.libraryontipsadmin.admin_page;

public class viewbook extends AppCompatActivity {

    private TabLayout tab;
    private ViewPager vpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbook);
        //       trans();

        tab = (TabLayout) findViewById(R.id.tabLayout);

        /*tab.addTab(tab.newTab().setText("IT"));
        tab.addTab(tab.newTab().setText("CS"));
        tab.addTab(tab.newTab().setText("MECHANICAL"));
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
*/
        vpager = (ViewPager) findViewById(R.id.pager);

        pager adapter = new pager(getSupportFragmentManager());
        adapter.addFragment(new tab1("Information Technology"), "IT");
        adapter.addFragment(new tab1("Computer Science"), "CS");
        adapter.addFragment(new tab1("Mechanical Engineering"), "MECHANICAL");
        adapter.addFragment(new tab1("Electrical Engineering"), "ELECTRICAL");

        vpager.setAdapter(adapter);

        tab.setupWithViewPager(vpager);

        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });

        vpager.setOffscreenPageLimit(3);
    }

    void trans()
    {
        if(android.os.Build.VERSION.SDK_INT >= 21) {
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setEnterTransition(explode);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),admin_page.class);
        startActivity(intent);
        finish();
    }
}