package com.fmedlin.digest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    public void onPageScrolled(float positionOffset, int pixelOffset) {
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
