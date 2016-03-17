package com.fmedlin.digest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;

public class TourActivity extends AppCompatActivity {

    TourPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        createPresenter();
    }

    private void createPresenter() {
        Bus bus = BusProvider.getInstance();
        presenter = new TourPresenter(
               new TourModel(),
               new TourView(this, bus),
               bus
       );
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.unregister(presenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.register(presenter);
    }
}
