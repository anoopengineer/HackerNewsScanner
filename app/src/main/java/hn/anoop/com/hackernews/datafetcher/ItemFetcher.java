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
import java.util.ArrayList;
import java.util.List;

import hn.anoop.com.hackernews.model.Item;
import hn.anoop.com.hackernews.utils.MyBus;

/**
 * Created by Akunju00c on 12/26/2014.
 */
public class ItemFetcher extends AsyncTask<URL, Void, List<Item>> {

    private final OkHttpClient mClient;
    private final Gson mGson;

    public ItemFetcher(OkHttpClient client, Gson gson) {
        this.mClient = client;
        this.mGson = gson;
    }

    @Override
    protected List<Item> doInBackground(URL... params) {
        List<Item> retVal = new ArrayList<Item>();

        for (URL url : params) {
            Item item = null;
            try {
                item = getItem(url, mClient, mGson);
            } catch (IOException e) {
                Log.e("ANOOP", "Exception on getting item "+url, e);
            }
            if(item != null) {
                retVal.add(item);
            }
        }
        return retVal;
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        super.onPostExecute(items);
        MyBus.getInstance().post(new AsyncTaskResultEvent(AsyncTaskResultEvent.ResultType.ITEM, items));
    }


    private Item getItem(URL url, OkHttpClient client, Gson gson) throws IOException {
        Log.e("ANOOP", "In getItem() " + url);
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String body = response.body().string();
        Log.e("ANOOP", body);
        Item item = gson.fromJson(body, Item.class);

        if (!TextUtils.isEmpty(item.getUrl())) {
            fetchFavIcon(item, client);
        }
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
}
