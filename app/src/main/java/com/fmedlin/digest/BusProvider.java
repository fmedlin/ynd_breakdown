package com.fmedlin.digest;

import com.squareup.otto.Bus;

public class BusProvider {

    static private Bus bus = new Bus();

    static Bus getInstance() {
        return bus;
    }

    public void register(Object... args) {
        for (Object o : args) {
            bus.register(o);
        }
    }

    public void unregister(Object... args) {
        for (Object o : args) {
            bus.unregister(o);
        }
    }

}
