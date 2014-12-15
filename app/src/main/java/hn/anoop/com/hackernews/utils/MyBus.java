package hn.anoop.com.hackernews.utils;

import com.squareup.otto.Bus;

/**
 * Created by Akunju00c on 12/2/2014.
 */
public class MyBus {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }
}
