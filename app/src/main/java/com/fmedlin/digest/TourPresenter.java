package com.fmedlin.digest;

public class TourPresenter {

    TourView view;

    public TourPresenter(TourView view) {
        this.view = view;
        view.startScreenZero();
    }
}
