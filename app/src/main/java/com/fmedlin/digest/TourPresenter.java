package com.fmedlin.digest;

import android.app.Activity;

import com.fmedlin.digest.TourView.PageScrolledEvent;
import com.fmedlin.digest.TourView.PageSelectedEvent;
import com.fmedlin.digest.TourView.SkipEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class TourPresenter {

    TourModel model;
    TourView view;
    Bus bus;

    public TourPresenter(TourModel model, TourView view, Bus bus) {
        this.model = model;
        this.view = view;
        view.setPagerAdapter(new TourPagerAdapter(view.getActivity()));
        view.startScreenZero();
    }

    @Subscribe
    public void onPageSelected(PageSelectedEvent event) {
        int lastPage = model.getLastPage();
        if (lastPage >= 0) {
            view.exitScreen(lastPage);
        }

        view.enterScreen(event.getPage());
    }

    @Subscribe
    public void onPageScrolled(PageScrolledEvent event) {
        int page = event.getPage();

        view.scrollPage(page, event.getPositionOffset(), event.getPixelOffset());
        if (page == 0) {
            view.scrollPage(1, event.getPositionOffset(), 0);
        }
    }

    @Subscribe
    public void onSkip(SkipEvent event) {
        Activity activity = view.getActivity();
        if (activity != null) {
            activity.finish();
        }
    }
}
