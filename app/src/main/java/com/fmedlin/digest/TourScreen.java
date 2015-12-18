package com.fmedlin.digest;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

public     abstract class TourScreen {
    Context context;
    ViewGroup layout;

    public TourScreen(Context context) {
        this.context = context;
    }

    abstract @LayoutRes public int getLayoutResId();
    abstract public void bindLayout(ViewGroup layout);

    public void setLayout(ViewGroup layout) {
        this.layout = layout;
    }

    public ViewGroup getLayout() {
        return layout;
    }

    public void onEnter() {
        // nop
    }

    public void onExit() {
        // nop
    }

    public void onPageScrolled(float positionOffset, int pixelOffset) {
        // nop
    }

}

