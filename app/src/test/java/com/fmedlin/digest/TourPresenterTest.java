package com.fmedlin.digest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class TourPresenterTest {

    TourPresenter presenter;
    @Mock TourView view;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        presenter = new TourPresenter(view);
    }

    @Test
    public void itShouldStartScreenZero() {
        verify(view).startScreenZero();
    }
}