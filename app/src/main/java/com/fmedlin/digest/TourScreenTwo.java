package com.fmedlin.digest;

import android.content.Context;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TourScreenTwo extends TourScreen {

    @Bind(R.id.juggle) JuggleWidget juggle;

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
        setupSatellites();
    }

    private void setupSatellites() {
        juggle.addSatellite(R.drawable.juggle_ball_yellow)
                .addSatellite(R.drawable.juggle_ball_cyan)
                .addSatellite(R.drawable.juggle_ball_green)
                .addSatellite(R.drawable.juggle_ball_pink)
                .addSatellite(R.drawable.juggle_ball_orange)
                .addSatellite(R.drawable.juggle_ball_teal);
    }
}
