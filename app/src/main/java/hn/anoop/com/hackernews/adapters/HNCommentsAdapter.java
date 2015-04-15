package hn.anoop.com.hackernews.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Subscribe;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hn.anoop.com.hackernews.R;
import hn.anoop.com.hackernews.datafetcher.AsyncTaskResultEvent;
import hn.anoop.com.hackernews.datafetcher.ItemFetcher;
import hn.anoop.com.hackernews.datasource.DataSource;
import hn.anoop.com.hackernews.model.Item;
import hn.anoop.com.hackernews.utils.MyBus;
import hn.anoop.com.hackernews.utils.Utils;

/**
 * Created by Akunju00c on 12/31/2014.
 */
public class HNCommentsAdapter extends RecyclerView.Adapter<HNCommentViewHolder> {

    List<Item> data = new ArrayList<Item>();

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public HNCommentsAdapter(Integer itemId) {
        //get the data here...
        Item item = DataSource.getInstance().getById(itemId);
        Integer[] kids = item.getKids();
        data.add(item);
        if (kids != null && kids.length > 0) {
            MyBus.getInstance().register(this);
            OkHttpClient client = new OkHttpClient();
            Gson gson = new Gson();
            URL[] urls = new URL[kids.length];
            for (int i = 0; i < kids.length; i++) {
                urls[i] = Utils.getItemURL(kids[i]);
                Log.e("ANOOP", "On HNCommentsAdapter:HNCommentsAdapter "+urls[i]);
            }
            new ItemFetcher(client, gson).execute(urls);
        }


    }

    @Subscribe
    public void onAsyncTaskResult(AsyncTaskResultEvent event) {
        Log.e("ANOOP", "On HNCommentsAdapter:onAsyncTaskResult ");

        List<Item> items = (List<Item>) event.getResult();
        Log.e("ANOOP", "On HNCommentsAdapter:onAsyncTaskResult "+items.size());
        //TODO:handle null
        data.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public HNCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("ANOOP", "On onCreateViewHolder " + viewType);
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hn_comments, parent, false);
            return new HNCommentViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card, parent, false);
            return new HNCommentViewHolder(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(HNCommentViewHolder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            Log.e("ANOOP", "returning 'TYPE_HEADER'");
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }
}
