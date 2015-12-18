package com.fmedlin.digest;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TourPagerAdapter extends PagerAdapter {

    Context context;
    int pageCount = 3;
    TourScreen[] screens = new TourScreen[pageCount];

    public TourPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TourScreen screen = null;

        switch (position) {
            case 0:
                screen = new TourScreenZero(context);
                break;

            case 1:
                screen = new TourScreenOne(context);
                break;

            case 2:
                screen = new TourScreenTwo(context);
                break;

            default:
                break;
        }

        ViewGroup layout = (ViewGroup) LayoutInflater.from(context).inflate(screen.getLayoutResId(), container, false);
        container.addView(layout);
        screen.bindLayout(layout);
        screens[position] = screen;

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        screens[position] = null;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public TourScreen getItem(int position) {
        return screens[position];
    }
}