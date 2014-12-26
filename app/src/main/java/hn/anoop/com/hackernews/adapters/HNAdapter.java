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

/**
 * Created by Akunju00c on 12/2/2014.
 */
public class HNAdapter extends BaseAdapter {

    private Item[] items;
    private final Context context;
    private final LayoutInflater inflater;
    private final PrettyTime p = new PrettyTime();

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
//        String id = item.getTitle();
        String text = item.getTitle();
        TextView titleText = (TextView) convertView.findViewById(R.id.title);
        titleText.setText(text);

        TextView messageText = (TextView) convertView.findViewById(R.id.message);
        String message = item.getText();
        if (message == null || message.isEmpty()) {
            messageText.setVisibility(View.GONE);
        } else {
            messageText.setText(message);
        }

        StringBuilder subtitle = new StringBuilder();
        subtitle.append("by ").append(item.getBy());
        subtitle.append("  posted ").append(p.format(new Date(item.getTime() * 1000)));


        TextView subtitleTextView = (TextView) convertView.findViewById(R.id.subtitle);
        subtitleTextView.setText(subtitle);


//        TextView timeView = (TextView)convertView.findViewById(R.id.time);
//        if(item.getTime() != null){
//            timeView.setText(p.format(new Date(item.getTime()*1000)));
//        }
        ImageView favIconView = (ImageView) convertView.findViewById(R.id.favicon);
        favIconView.setImageResource(android.R.color.transparent);
        LinearLayout linkArea = (LinearLayout) convertView.findViewById(R.id.linkArea);
        if (!TextUtils.isEmpty(item.getUrl())) {
            TextView linkView = (TextView) convertView.findViewById(R.id.link);
            linkView.setText(item.getUrl());
            TextView domainView = (TextView) convertView.findViewById(R.id.domain);
            String domain = item.getDomain();
            if (TextUtils.isEmpty(domain)) {
                domainView.setVisibility(View.GONE);
            } else {
                domainView.setText(domain);
            }
            if(item.getFavIcon() != null){
                favIconView.setImageBitmap(item.getFavIcon());
            }else{
                favIconView.setImageResource(R.drawable.ic_broken_link);
            }

            final String finalDomain = domain;
            linkArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, WebViewActivity.class);
                    i.putExtra(WebViewActivity.URL, item.getUrl());
                    i.putExtra(WebViewActivity.DOMAIN, finalDomain);
                    context.startActivity(i);
                }
            });
        } else {
            linkArea.setVisibility(View.GONE);
        }

        return convertView;
    }
}
