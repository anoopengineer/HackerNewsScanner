package hn.anoop.com.hackernews.datafetcher;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

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
//        return null;
//        Item item = new Item();
//        item.setId(0);
        return item;
    }

    @Override
    protected void onPostExecute(Item[] items) {
        super.onPostExecute(items);
        MyBus.getInstance().post(new AsyncTaskResultEvent(items));
    }
}
