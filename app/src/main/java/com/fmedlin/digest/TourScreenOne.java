package com.fmedlin.digest;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TourScreenOne extends TourScreen {

    @Bind({R.id.blue_paragraph, R.id.red_paragraph, R.id.orange_paragraph, R.id.green_paragraph})
    List<ImageView> paragraphs;

    @Bind(R.id.orbit) OrbitWidget orbit;
    float positionOffset = 0f;

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
        orbit.addSatellite(R.drawable.sun, 0.65f);
        orbit.addSatellite(R.drawable.half_moon, 0.15f);
    }

    @Override
    public void onEnter(boolean isAdvancing) {
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

    @Override
    public void onExit(boolean isAdvancing) {
        ButterKnife.apply(paragraphs, TRANSPARENT);
    }

    ButterKnife.Action<View> TRANSPARENT = new ButterKnife.Action<View>() {
        @Override
        public void apply(final View view, int index) {
            view.setAlpha(0f);
        }
    };

    @Override
    public void onPageScrolled(float positionOffset, int pixelOffset) {
        orbit.setPositionOffset(positionOffset);
    }
}
