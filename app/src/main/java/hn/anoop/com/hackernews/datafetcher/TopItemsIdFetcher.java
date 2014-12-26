package hn.anoop.com.hackernews.datafetcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;

import hn.anoop.com.hackernews.model.Item;
import hn.anoop.com.hackernews.utils.MyBus;

/**
 * Created by Akunju00c on 12/2/2014.
 */
public class TopItemsIdFetcher extends AsyncTask<Void, Void, Integer[]> {

    private final OkHttpClient mClient;
    private final Gson mGson;

    public TopItemsIdFetcher(OkHttpClient client, Gson gson) {
        this.mClient = client;
        this.mGson = gson;
    }

    @Override
    protected Integer[] doInBackground(Void... params) {
        try {
            Log.e("ANOOP", "In TopItemsFetcher doInBackground");
            String url = "https://hacker-news.firebaseio.com/v0/topstories.json";
            Request request = new Request.Builder().url(url).build();
            Response response = mClient.newCall(request).execute();
            String body = response.body().string();
            Log.e("ANOOP", body);
            Integer[] ids = mGson.fromJson(body, Integer[].class);
            if (ids == null) {
                Log.e("ANOOP", "Items is null");
            } else {
                Log.e("ANOOP", "Items length = " + ids.length);
            }

            return ids;
        } catch (Exception ex) {
            Log.e("ANOOP", "Exception on fetching top items " + ex.getMessage());
            return null;
        }

    }

    @Override
    protected void onPostExecute(Integer[] ids) {
        super.onPostExecute(ids);
        MyBus.getInstance().post(new AsyncTaskResultEvent(AsyncTaskResultEvent.ResultType.ID, ids));
    }
}
