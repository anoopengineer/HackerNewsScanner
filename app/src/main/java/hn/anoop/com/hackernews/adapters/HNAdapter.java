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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import hn.anoop.com.hackernews.R;
import hn.anoop.com.hackernews.activities.WebViewActivity;
import hn.anoop.com.hackernews.model.Item;
import hn.anoop.com.hackernews.utils.DownloadImageTask;

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
        if (items != null) {
            this.items = items.toArray(new Item[items.size()]);
        }
        inflater = context.getLayoutInflater();
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
            String domain = null;
            String favIcon = null;
            try {
                URL url = new URL(item.getUrl());
                domain = url.getHost();
                int port = url.getPort();
                String portSt = (port == -1) ? "" : ":" + port;
                favIcon = url.getProtocol() + "://" + url.getHost() + portSt + "/favicon.ico";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            TextView domainView = (TextView) convertView.findViewById(R.id.domain);
            if (TextUtils.isEmpty(domain)) {
                domainView.setVisibility(View.GONE);
            } else {
                domainView.setText(domain);
            }
            if (!TextUtils.isEmpty(favIcon)) {
                new DownloadImageTask(favIconView).execute(favIcon);
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
