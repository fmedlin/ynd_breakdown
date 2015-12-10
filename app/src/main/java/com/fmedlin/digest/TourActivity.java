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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TourActivity extends AppCompatActivity {

    @Bind(R.id.viewpager) ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        ButterKnife.bind(this);

        pager.setAdapter(new TourPagerAdapter(this));
    }

    @OnClick(R.id.skip)
    public void onClick() {
        finish();
    }

    class TourPagerAdapter extends PagerAdapter {

        Context context;

        public TourPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TourScreen screen = null;

            switch(position) {
                case 0:
                    screen = new TourScreenOne(context);
                    break;

                case 1:
                    screen = new TourScreenTwo(context);
                    break;

                case 2:
                    screen = new TourScreenThree(context);
                    break;

                default:
                    break;
            }

            ViewGroup layout = (ViewGroup) LayoutInflater.from(context).inflate(screen.getLayoutResId(), container, false);
            screen.bindLayout(layout);
            container.addView(layout);

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
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
            ButterKnife.bind(this, layout);
        }

    }

    public class TourScreenThree extends TourScreen {

        public TourScreenThree(Context context) {
            super(context);
        }

        @Override
        public int getLayoutResId() {
            return R.layout.view_tour_page3;
        }

        @Override
        public void bindLayout(ViewGroup layout) {
        }
    }
}
