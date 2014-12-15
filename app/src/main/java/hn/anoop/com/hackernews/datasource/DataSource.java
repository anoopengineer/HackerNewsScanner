package hn.anoop.com.hackernews.datasource;

import android.util.Log;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import hn.anoop.com.hackernews.datafetcher.TopItemsFetcher;
import hn.anoop.com.hackernews.model.Item;
import hn.anoop.com.hackernews.utils.AsyncTaskResultEvent;
import hn.anoop.com.hackernews.utils.MyBus;

/**
 * Created by Akunju00c on 12/10/2014.
 */
public class DataSource {

    private static final DataSource INSTANCE = new DataSource();

    List<Item> mDataList;

    DataListener mListener;

    private DataSource() {
        mDataList = new ArrayList<Item>();
        MyBus.getInstance().register(this);
        Log.e("ANOOP", "DataSource constructor");
    }

    public static synchronized DataSource getInstance() {
        Log.e("ANOOP", "Datasource getInstance");
        return INSTANCE;
    }

    public DataListener getDataListener() {
        return mListener;
    }

    public void setDataListener(DataListener mListener) {
        this.mListener = mListener;
    }

    public List<Item> getmDataList() {
        return mDataList;
    }

    public void setmDataList(List<Item> mDataList) {
        this.mDataList = mDataList;
    }

    public boolean fetchData() {
        Log.e("ANOOP", "Datasource fetchData()");
        if (mDataList == null || mDataList.isEmpty()) {
            Log.e("ANOOP", "About to fetch data");
            new TopItemsFetcher().execute();
            return true;
        }else{
            mListener.dataUpdated();
        }
        return false;
    }

    public Item getByIndex(int index) {
        return mDataList.get(index);
    }

    public Item getById(int id) {
        for (Item item : mDataList) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getAll() {
        return mDataList;
    }

    @Subscribe
    public void onAsyncTaskResult(AsyncTaskResultEvent event) {
        Log.e("ANOOP", "In DataSource:onAyncTaskResult");
        Item[] items = event.getResult();
        if (items == null) {
            Log.e("ANOOP", "In DataSource:onAyncTaskResult is null");
        } else {
            for (Item item : items) {
                Log.e("ANOOP", item.getTitle());
                mDataList.add(item);
            }
        }
        if (mListener != null) {
            Log.e("ANOOP", "Calling dataUpdated");
            mListener.dataUpdated();
        }
    }


    public interface DataListener {
        public void dataUpdated();
    }
}
