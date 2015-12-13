package com.fmedlin.digest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import butterknife.OnPageChange.Callback;

public class TourActivity extends AppCompatActivity {

    @Bind(R.id.viewpager) ViewPager pager;
    TourPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        ButterKnife.bind(this);

        adapter = new TourPagerAdapter(this);
        pager.setAdapter(adapter);
    }

    @OnClick(R.id.skip)
    public void onClick() {
        finish();
    }

    @OnPageChange(R.id.viewpager)
    public void onPageSelected(int page) {

        switch (page) {
            case 0:
                onEnter(0);
                break;

            case 1:
                onExit(0);
                onEnter(1);
                break;

            case 2:
                onExit(1);
                onEnter(2);
                break;

            default:
                break;
        }
    }

    @OnPageChange(value = R.id.viewpager, callback = Callback.PAGE_SCROLLED)
    public void onPageScrolled(int position, float positionOffset, int pixelOffset) {

    }

    private void onEnter(int page) {
        TourScreen screen = adapter.getItem(page);
        if (screen != null) {
            screen.onEnter();
        }
    }

    private void onExit(int page) {
        TourScreen screen = adapter.getItem(page);
        if (screen != null) {
            screen.onExit();
        }
    }

    class TourPagerAdapter extends PagerAdapter {

        Context context;
        int pageCount = 3;
        TourScreen[] screens = new TourScreen[pageCount];

        public TourPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TourScreen screen = null;

            switch(position) {
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

    abstract class TourScreen {
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

    }

    public class TourScreenZero extends TourScreen {

        @Bind({R.id.camera, R.id.pie_chart, R.id.analysis, R.id.music, R.id.quotes, R.id.location, R.id.dubya})
        List<ImageView> icons;
        int[] sequence = new int[] {1, 4, 3, 2, 6, 5, 4};

        public TourScreenZero(Context context) {
            super(context);
        }

        @Override
        public int getLayoutResId() {
            return R.layout.view_tour_page0;
        }

        @Override
        public void bindLayout(ViewGroup layout) {
            ButterKnife.bind(this, layout);
            onEnter();
        }

        @Override
        public void onEnter() {
            showIcons();
        }

        @Override
        public void onExit() {
            hideIcons();
        }

        public void showIcons() {
            ButterKnife.apply(icons, FADEIN);
        }

        public void hideIcons() {
            ButterKnife.apply(icons, TRANSPARENT);
        }

        ButterKnife.Action<View> FADEIN = new ButterKnife.Action<View>() {
            @Override
            public void apply(final View view, int index) {
                view.animate()
                        .alpha(1.0f)
                        .setStartDelay(sequence[index] * 200L);
            }
        };

        ButterKnife.Action<View> TRANSPARENT = new ButterKnife.Action<View>() {
            @Override
            public void apply(final View view, int index) {
                view.setAlpha(0f);
            }
        };

    }

    public class TourScreenOne extends TourScreen {

        public TourScreenOne(Context context) {
            super(context);
        }

        @Override
        public int getLayoutResId() {
            return R.layout.view_tour_page1;
        }

        @Override
        public void bindLayout(ViewGroup layout) {
            ButterKnife.bind(this, layout);
        }

    }

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

}
