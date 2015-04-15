package hn.anoop.com.hackernews.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;

import hn.anoop.com.hackernews.R;
import hn.anoop.com.hackernews.activities.WebViewActivity;
import hn.anoop.com.hackernews.model.Item;
import hn.anoop.com.hackernews.utils.UI;

/**
 * Created by Akunju00c on 12/2/2014.
 */
public class HNAdapter extends BaseAdapter {

    private Item[] items;
    private final Context context;
    private final LayoutInflater inflater;

    public HNAdapter(Activity context, List<Item> items) {
        this.context = context;
        setItems(items);
        inflater = context.getLayoutInflater();
    }

    public void setItems(List<Item> items){
        if (items != null) {
            this.items = items.toArray(new Item[items.size()]);
        }
    }

    @Override
    public int getCount() {
        if (items == null) {
            return 0;
        }
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_card, parent, false);
        }
        final Item item = items[position];
        UI.populateCardView(context, item, convertView);
        return convertView;
    }


}
