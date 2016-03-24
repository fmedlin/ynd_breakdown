package com.fmedlin.digest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TourScreenTwo extends TourScreen {

    private static final long ADVANCE_DELAY_MS = 60;
    private static final int ADVANCE_MSG = 1;

    @Bind(R.id.juggle) JuggleWidget juggle;
    boolean isJugglingEnabled;
    Handler handler;

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

    @Override
    public void onPageScrolled(float positionOffset, int pixelOffset) {
        Log.d("TS2", "position=" + positionOffset + ", pixel=" + pixelOffset);
        setJugglingEnabled(positionOffset == 0f);
    }

    private void setJugglingEnabled(boolean isJugglingEnabled) {
        if (this.isJugglingEnabled == isJugglingEnabled) {
            return;
        }

        this.isJugglingEnabled = isJugglingEnabled;
        if (isJugglingEnabled) {
            startJuggling();
        } else {
            stopJuggling();
        }
    }

    private void startJuggling() {
        Log.d("TS2", "startJugging");
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == ADVANCE_MSG) {
                    juggle.advance();
                    sendEmptyMessageDelayed(ADVANCE_MSG, ADVANCE_DELAY_MS);
                }
            }
        };

        handler.sendEmptyMessageDelayed(ADVANCE_MSG, ADVANCE_DELAY_MS);
    }

    private void stopJuggling() {
        Log.d("TS2", "stopJugging");
        handler.removeMessages(ADVANCE_MSG);
        handler = null;
    }
}
