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
        onPageScrolled(position, pixelOffset);
    }

    private void onPageScrolled(int page, int pixelOffset) {
        TourScreen screen = adapter.getItem(page);
        if (screen != null) {
            screen.onPageScrolled(pixelOffset);
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

        public void onPageScrolled(int pixelOffset) {
            // nop
        }
    }

    public class TourScreenZero extends TourScreen {

        @Bind({R.id.camera, R.id.pie_chart, R.id.analysis, R.id.music, R.id.quotes, R.id.location, R.id.dubya})
        List<ImageView> icons;

        int[] sequence = new int[] {1, 4, 3, 2, 6, 5, 4};
        float[] parallax = new float[] {0.2f, 0.3f, 0.3f, 0.3f, -0.2f, -1f, -0.2f};
        int pixelOffset;

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
            ButterKnife.apply(icons, FADEIN);
        }

        @Override
        public void onExit() {
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

        @Override
        public void onPageScrolled(int pixelOffset) {
            this.pixelOffset = pixelOffset;
            ButterKnife.apply(icons, PARALLAX);
        }

        ButterKnife.Action<View> PARALLAX = new ButterKnife.Action<View>() {
            @Override
            public void apply(final View view, int index) {
                view.setTranslationX(parallax[index] * pixelOffset);
            }
        };

    }

    public class TourScreenOne extends TourScreen {

        @Bind({R.id.blue_paragraph, R.id.red_paragraph, R.id.orange_paragraph, R.id.green_paragraph})
        List<ImageView> paragraphs;

        @Bind(R.id.orbit) OrbitWidget orbit;

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
            setupSatellites();
        }

        private void setupSatellites() {
            orbit.addSatellite(R.drawable.sun, 0f);
            orbit.addSatellite(R.drawable.half_moon, 0.5f);
        }


        public void onEnter() {
            ButterKnife.apply(paragraphs, FADEIN);
        }

        ButterKnife.Action<View> FADEIN = new ButterKnife.Action<View>() {
            @Override
            public void apply(final View view, int index) {
                view.animate()
                        .alpha(1.0f)
                        .setStartDelay((index + 1) * 250L);
            }
        };

        public void onExit() {
            ButterKnife.apply(paragraphs, TRANSPARENT);
        }

        ButterKnife.Action<View> TRANSPARENT = new ButterKnife.Action<View>() {
            @Override
            public void apply(final View view, int index) {
                view.setAlpha(0f);
            }
        };

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
