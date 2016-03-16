package com.fmedlin.digest;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnPageChange;

public class TourView {

    @Bind(R.id.viewpager) ViewPager pager;
    @Bind({R.id.page0, R.id.page1, R.id.page2}) List<ImageView> pageIndicators;

    WeakReference<Activity> activityRef;

    public TourView(Activity activity) {
        activityRef = new WeakReference<Activity>(activity);
        setupViews(activity);
    }

    private void setupViews(Activity activity) {
        ButterKnife.bind(this, activity);
    }

    public void setPagerAdapter(TourPagerAdapter adapter) {
        pager.setAdapter(adapter);
    }

    @Nullable
    public Activity getActivity() {
        return activityRef.get();
    }

    public void startScreenZero() {
        ButterKnife.apply(pageIndicators, INDICATE_PAGE, 0);
    }

    @OnPageChange(R.id.viewpager)
    public void onPageSelected(int page) {
        ButterKnife.apply(pageIndicators, INDICATE_PAGE, page);
    }

    ButterKnife.Setter<View, Integer> INDICATE_PAGE = new ButterKnife.Setter<View, Integer>() {
        @Override
        public void set(final View view, Integer page, int index) {
            ImageView indicator = (ImageView) view;
            indicator.setImageResource(page == index ? R.drawable.ic_page_on : R.drawable.ic_page_off);
        }
    };

}
