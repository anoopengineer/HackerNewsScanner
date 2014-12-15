package hn.anoop.com.hackernews.utils;

import hn.anoop.com.hackernews.model.Item;

/**
 * Created by Akunju00c on 12/2/2014.
 */
public class AsyncTaskResultEvent {

    private Item[] result;

    public AsyncTaskResultEvent(Item[] result) {
        this.result = result;
    }

    public Item[] getResult() {
        return result;
    }
}