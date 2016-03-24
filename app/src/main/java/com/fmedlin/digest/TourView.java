package com.fmedlin.digest;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.squareup.otto.Bus;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import butterknife.OnPageChange.Callback;

public class TourView {

    @Bind(R.id.viewpager) ViewPager pager;
    @Bind({R.id.page0, R.id.page1, R.id.page2}) List<ImageView> pageIndicators;

    WeakReference<Activity> activityRef;
    Bus bus;
    TourPagerAdapter adapter;

    public TourView(Activity activity, Bus bus) {
        activityRef = new WeakReference<Activity>(activity);
        this.bus = bus;
        setupViews(activity);
    }

    private void setupViews(Activity activity) {
        ButterKnife.bind(this, activity);
    }

    public void setPagerAdapter(TourPagerAdapter adapter) {
        this.adapter = adapter;
        pager.setAdapter(adapter);
    }

    @Nullable
    public Activity getActivity() {
        return activityRef.get();
    }

    public void startScreenZero() {
        ButterKnife.apply(pageIndicators, INDICATE_PAGE, 0);
    }

    public void enterScreen(int page, boolean isAdvancing) {
        TourScreen screen = adapter.getItem(page);
        if (screen != null) {
            screen.onEnter(isAdvancing);
        }
    }

    public void exitScreen(int page, boolean isAdvancing) {
        if (page >= 0) {
            TourScreen screen = adapter.getItem(page);
            if (screen != null) {
                screen.onExit(isAdvancing);
            }
        }
    }

    public void scrollPage(int page, float positionOffset, int pixelOffset) {
        TourScreen screen = adapter.getItem(page);
        if (screen != null) {
            screen.onPageScrolled(positionOffset, pixelOffset);
        }
    }

    @OnPageChange(R.id.viewpager)
    public void onPageSelected(int page) {
        ButterKnife.apply(pageIndicators, INDICATE_PAGE, page);
        bus.post(new PageSelectedEvent(page));
    }

    @OnPageChange(value = R.id.viewpager, callback = Callback.PAGE_SCROLLED)
    public void onPageScrolled(int page, float positionOffset, int pixelOffset) {
        bus.post(new PageScrolledEvent(page, positionOffset, pixelOffset));
    }

    @OnClick(R.id.skip)
    public void onClick() {
        bus.post(new SkipEvent());
    }

    ButterKnife.Setter<View, Integer> INDICATE_PAGE = new ButterKnife.Setter<View, Integer>() {
        @Override
        public void set(final View view, Integer page, int index) {
            ImageView indicator = (ImageView) view;
            indicator.setImageResource(page == index ? R.drawable.ic_page_on : R.drawable.ic_page_off);
        }
    };

    // events posted

    static public class PageSelectedEvent {
        int page;

        public PageSelectedEvent(int page) {
            this.page = page;
        }

        public int getPage() {
            return page;
        }
    }

    static public class PageScrolledEvent {
        int page;
        float positionOffset;
        int pixelOffset;

        public PageScrolledEvent(int page, float positionOffset, int pixelOffset) {
            this.page = page;
            this.positionOffset = positionOffset;
            this.pixelOffset = pixelOffset;
        }

        public int getPage() {
            return page;
        }

        public float getPositionOffset() {
            return positionOffset;
        }

        public int getPixelOffset() {
            return pixelOffset;
        }
    }

    public static class SkipEvent { }
}
