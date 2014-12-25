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
import java.net.MalformedURLException;
import java.net.URL;

import hn.anoop.com.hackernews.model.Item;
import hn.anoop.com.hackernews.utils.AsyncTaskResultEvent;
import hn.anoop.com.hackernews.utils.MyBus;

/**
 * Created by Akunju00c on 12/2/2014.
 */
public class TopItemsFetcher extends AsyncTask<Void, Void, Item[]> {


    @Override
    protected Item[] doInBackground(Void... params) {
        try {
            Log.e("ANOOP", "In TopItemsFetcher doInBackground");
            String url = "https://hacker-news.firebaseio.com/v0/topstories.json";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            Log.e("ANOOP", body);
            Gson gson = new Gson();
            Integer[] ids = gson.fromJson(body, Integer[].class);
            if (ids == null) {
                Log.e("ANOOP", "Items is null");
            } else {
                Log.e("ANOOP", "Items length = " + ids.length);
            }

//            for(Integer id:ids){
//                Item item = getItem(id, client);
//            }
//            return items;


            int length = ids.length;
            if (length > 5) {
                length = 5;
            }

            Item[] items = new Item[length];
            for (int i = 0; i < items.length; i++) {
                items[i] = getItem(ids[i], client, gson);
            }
            return items;
        } catch (Exception ex) {
            Log.e("ANOOP", "Exception on fetching top items " + ex.getMessage());
            return null;
        }

    }

    private Item getItem(Integer id, OkHttpClient client, Gson gson) throws IOException {
        Log.e("ANOOP", "In getItem() "+ id);
        String url = "https://hacker-news.firebaseio.com/v0/item/" + id + ".json";
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String body = response.body().string();
        Log.e("ANOOP", body);
        Item item = gson.fromJson(body, Item.class);

        if(!TextUtils.isEmpty(item.getUrl())){
            fetchFavIcon(item, client);
        }
//        return null;
//        Item item = new Item();
//        item.setId(0);
        return item;
    }

    private void fetchFavIcon(Item item, OkHttpClient client) {
        String domain = null;
        String favIcon = null;
        try {
            URL url = new URL(item.getUrl());
            domain = url.getHost();
            int port = url.getPort();
            String portSt = (port == -1) ? "" : ":" + port;
            favIcon = url.getProtocol() + "://" + url.getHost() + portSt + "/favicon.ico";
            Request request = new Request.Builder().url(favIcon).build();
            Response response = client.newCall(request).execute();
            Bitmap bitMap = BitmapFactory.decodeStream(response.body().byteStream());
            item.setFavIcon(bitMap);
            item.setDomain(domain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPostExecute(Item[] items) {
        super.onPostExecute(items);
        MyBus.getInstance().post(new AsyncTaskResultEvent(items));
    }
}
