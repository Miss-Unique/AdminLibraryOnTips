package com.example.soumyaagarwal.libraryontipsadmin.LibraryMap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soumyaagarwal.libraryontipsadmin.NavDrawer.drawer1;
import com.example.soumyaagarwal.libraryontipsadmin.R;

public class MapL extends drawer1 {

    TextView one,two,three,four,five,six,seven,eight,nine,ten;
    Character a;
    EditText shelf;
    String shelfno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
        getLayoutInflater().inflate(R.layout.activity_map, frame);
        shelfno = getIntent().getExtras().getString("ShelfNo");

        one = (TextView) findViewById(R.id.textView1);
        two = (TextView) findViewById(R.id.textView2);
        three = (TextView) findViewById(R.id.textView3);
        four = (TextView) findViewById(R.id.textView4);
        five = (TextView) findViewById(R.id.textView5);
        six = (TextView) findViewById(R.id.textView6);
        seven = (TextView) findViewById(R.id.textView7);
        eight = (TextView) findViewById(R.id.textView8);
        nine = (TextView) findViewById(R.id.textView9);
        ten = (TextView) findViewById(R.id.textView10);
        shelf = (EditText)findViewById(R.id.shelf);
        shelf.setText(shelfno);


                one.setBackgroundColor(getResources().getColor(R.color.light));
                two.setBackgroundColor(getResources().getColor(R.color.light));
                three.setBackgroundColor(getResources().getColor(R.color.light));
                four.setBackgroundColor(getResources().getColor(R.color.light));
                five.setBackgroundColor(getResources().getColor(R.color.light));
                six.setBackgroundColor(getResources().getColor(R.color.light));
                seven.setBackgroundColor(getResources().getColor(R.color.light));
                eight.setBackgroundColor(getResources().getColor(R.color.light));
                nine.setBackgroundColor(getResources().getColor(R.color.light));
                ten.setBackgroundColor(getResources().getColor(R.color.light));

                a = shelfno.charAt(0);

                    switch(a)
                    {
                        case 'A': one.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                        case 'B': two.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                        case 'C': three.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                        case 'D': four.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                        case 'E': five.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                        case 'F': six.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                        case 'G': seven.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                        case 'H': eight.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                        case 'I': nine.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                        case 'J': ten.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                    }
            }
 }