package com.fmedlin.digest;

import com.squareup.otto.Bus;

public class BusProvider {

    static private Bus instance;

    public static Bus getInstance() {
        if (instance == null) {
            instance = new Bus();
        }
        return instance;
    }

    public static void register(Object... args) {
        for (Object o : args) {
            instance.register(o);
        }
    }

    public static void unregister(Object... args) {
        for (Object o : args) {
            instance.unregister(o);
        }
    }

}
