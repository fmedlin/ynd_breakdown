package com.fmedlin.digest;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import butterknife.OnPageChange.Callback;

public class TourActivity extends AppCompatActivity {

    static int LAST_POSITION = -1;

    @Bind(R.id.viewpager) ViewPager pager;
    @Bind({R.id.page0, R.id.page1, R.id.page2}) List<ImageView> pageIndicators;

    TourPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        setupViews();
    }

    private void setupViews() {
        ButterKnife.bind(this);
        adapter = new TourPagerAdapter(this);
        pager.setAdapter(adapter);
        ButterKnife.apply(pageIndicators, INDICATE_PAGE, 0);
    }

    @OnClick(R.id.skip)
    public void onClick() {
        finish();
    }

    @OnPageChange(R.id.viewpager)
    public void onPageSelected(int page) {
        ButterKnife.apply(pageIndicators, INDICATE_PAGE, page);

        switch (page) {
            case 0:
                onExit(LAST_POSITION);
                onEnter(0);
                break;

            case 1:
                onExit(LAST_POSITION);
                onEnter(1);
                break;

            case 2:
                onExit(LAST_POSITION);
                onEnter(2);
                break;

            default:
                return;
        }

        LAST_POSITION = page;
    }

    ButterKnife.Setter<View, Integer> INDICATE_PAGE = new ButterKnife.Setter<View, Integer>() {
        @Override
        public void set(final View view, Integer page, int index) {
            ImageView indicator = (ImageView) view;
            indicator.setImageResource(page == index ? R.drawable.ic_page_on : R.drawable.ic_page_off);
        }
    };

    private void onEnter(int page) {
        TourScreen screen = adapter.getItem(page);
        if (screen != null) {
            screen.onEnter();
        }
    }

    private void onExit(int page) {
        if (page >= 0) {
            TourScreen screen = adapter.getItem(page);
            if (screen != null) {
                screen.onExit();
            }
        }
    }

    @OnPageChange(value = R.id.viewpager, callback = Callback.PAGE_SCROLLED)
    public void onPageScrolled(int position, float positionOffset, int pixelOffset) {
        onPageScroll(position, positionOffset, pixelOffset);
    }

    private void onPageScroll(int page, float positionOffset, int pixelOffset) {
        TourScreen screen = adapter.getItem(page);
        if (screen != null) {
            screen.onPageScrolled(positionOffset, pixelOffset);

            if (page == 0) {
                TourScreen screen1 = adapter.getItem(1);
                if (screen1 != null) {
                    screen1.onPageScrolled(positionOffset, 0);
                }
            }
        }
    }
}
