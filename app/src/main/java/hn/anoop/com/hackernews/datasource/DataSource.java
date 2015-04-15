package hn.anoop.com.hackernews.datasource;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Subscribe;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hn.anoop.com.hackernews.datafetcher.AsyncTaskResultEvent;
import hn.anoop.com.hackernews.datafetcher.ItemFetcher;
import hn.anoop.com.hackernews.datafetcher.TopItemsIdFetcher;
import hn.anoop.com.hackernews.model.Item;
import hn.anoop.com.hackernews.utils.MyBus;
import hn.anoop.com.hackernews.utils.Utils;

/**
 * Created by Akunju00c on 12/10/2014.
 */
public class DataSource {

    private static final int NUMBER_OF_ITEMS_TO_LOAD_ON_START = 10;

    private static final int FETCH_BLOCK_SIZE = 10;

    private static final DataSource INSTANCE = new DataSource();

    /**
     * This is the Integer ID list of the top 100 items *
     */
    private Integer[] mIDList;

    /**
     * This is the datastore that backs the list view
     */
    List<Item> mDataList;

    DataListener mListener;

    private final OkHttpClient mClient;
    private final Gson mGson;

    private DataSource() {
        mDataList = new ArrayList<Item>();
        MyBus.getInstance().register(this);
        mClient = new OkHttpClient();
        mGson = new Gson();
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
            new TopItemsIdFetcher(mClient, mGson).execute();
            return true;
        } else {
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
        if (event.getResultType() == AsyncTaskResultEvent.ResultType.ID) {
            Integer[] ids = (Integer[]) event.getResult();
            this.mIDList = ids;
            //TODO: handle null
            this.mDataList.clear();

            //Once we get the integer id, we have the fetch couple of them first and show in the list.
            // The rest of the fetch happens when the user scrolls down
            int length = NUMBER_OF_ITEMS_TO_LOAD_ON_START;
            if (ids.length < NUMBER_OF_ITEMS_TO_LOAD_ON_START) {
                length = ids.length;
            }

            fetchIndex(0, length);

        } else {
            List<Item> items = (List<Item>) event.getResult();
            //TODO:handle null
            mDataList.addAll(items);
            mListener.dataUpdated();
        }

    }

    private void fetchIndex(int startIndex, int length) {
        URL[] urls = new URL[length];
        for (int i = startIndex; i < length; i++) {
            urls[i] = Utils.getItemURL(mIDList[i]);
            //TODO:handle null
        }
        new ItemFetcher(mClient, mGson).execute(urls);
    }

    public void appendData() {
        if(mDataList == null || mDataList.size() == 0 || mIDList == null || mIDList.length == 0){
            mListener.dataUpdated();
            return;
        }
        Log.e("ANOOP", "In DataSource.appendData()");

        int currentFetchSize = mDataList.size();
        if (currentFetchSize >= mIDList.length) {
            Log.w("ANOOP", "Nothing more to fetch");
            mListener.dataUpdated();
            return;
        }
        Log.e("ANOOP", "In DataSource.appendData() currentFetchSize = "+currentFetchSize + " mIDList.length = "+mIDList.length);

        int length = FETCH_BLOCK_SIZE;
        if ((currentFetchSize + length) > (mIDList.length - 1)) {
            length = mIDList.length - currentFetchSize;
        }

        Log.e("ANOOP", "In DataSource.appendData() length = "+length );

        URL[] urls = new URL[length];
        for (int i = 0; i < length; i++) {
            urls[i] = Utils.getItemURL(mIDList[currentFetchSize + i]);
            //TODO:handle null
        }
        new ItemFetcher(mClient, mGson).execute(urls);

    }


    public interface DataListener {
        public void dataUpdated();
    }


}
