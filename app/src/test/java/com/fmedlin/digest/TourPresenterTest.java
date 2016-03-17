package com.fmedlin.digest;

import com.fmedlin.digest.TourView.PageSelectedEvent;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TourPresenterTest {

    TourPresenter presenter;
    @Mock TourModel model;
    @Mock TourView view;
    @Mock Bus bus;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        presenter = new TourPresenter(model, view, bus);
    }

    @Test
    public void itShouldStartScreenZero() {
        verify(view).startScreenZero();
    }

    @Test
    public void itShouldChangePages() {
        changePage(-1, 0);
        changePage(0, 1);
        changePage(1, 2);
        changePage(2, 1);
        changePage(1, 0);
    }

    private void changePage(int from, int to) {
        when(model.getLastPage()).thenReturn(from);
        presenter.onPageSelected(new PageSelectedEvent(to));

        if (from >= 0) {
            verify(view).exitScreen(from);
        }
        verify(view).enterScreen(to);
        reset(view);
    }
}