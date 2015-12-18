package com.fmedlin.digest;

import android.content.Context;
import android.view.ViewGroup;

public class TourScreenTwo extends TourScreen {

    public TourScreenTwo(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.view_tour_page2;
    }

    @Override
    public void bindLayout(ViewGroup layout) {
    }
}
