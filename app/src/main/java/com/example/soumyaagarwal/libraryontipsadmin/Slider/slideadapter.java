package com.example.soumyaagarwal.libraryontipsadmin.Slider;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.soumyaagarwal.libraryontipsadmin.R;


public class slideadapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public String[] textlist1 = {"PANIC", "TRACK LOCATION", "SECURITY NETWORK", "PIN SECURITY"};

    //List of Texts
    public String[] textlist = {"Tell your loved ones about the your ", "Be sure of the whereabouts of your loved ones",
            "Add guardians whom you may ask for help in distress", "Only you can say that you are safe"};

    //List of Background colors
    public int[] bgcolors = {
            Color.rgb(229, 115, 115),
            Color.rgb(192, 108, 132),
            Color.rgb(108, 91, 123),
            Color.rgb(53, 92, 125)
    };


    public int[] imgs = {
        //TODO: ICONS
    };

    public slideadapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return textlist.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide1, container, false);
        LinearLayout layoutslide = (LinearLayout) view.findViewById(R.id.slide_layout);
        TextView text = (TextView) view.findViewById(R.id.slidetext);
        layoutslide.setBackgroundColor(bgcolors[position]);
        text.setText(textlist[position]);
        ImageView imageView = (ImageView) view.findViewById(R.id.img);
//        imageView.setImageResource(imgs[position]);

        TextView text2 = (TextView) view.findViewById(R.id.slidetext1);
        text2.setText(textlist1[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}