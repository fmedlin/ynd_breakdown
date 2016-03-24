package com.fmedlin.digest;

public class TourModel {
    static public final int UNDEFINED_PAGE = -1;

    int page = UNDEFINED_PAGE;
    int lastPage = UNDEFINED_PAGE;

    public int getLastPage() {
        return lastPage;
    }

    public int getPage() {
        return page;
    }

    public boolean setPage(int page) {
        lastPage = page;
        this.page = page;

        return isPageAdvancing();
    }

    public boolean isPageAdvancing() {
        return page >= lastPage;
    }
}
