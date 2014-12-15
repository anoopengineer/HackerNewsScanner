package hn.anoop.com.hackernews.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hn.anoop.com.hackernews.R;
import hn.anoop.com.hackernews.model.Item;

/**
 * Created by Akunju00c on 12/2/2014.
 */
public class HNAdapter extends BaseAdapter {

    private final Item[] items;
    private final Activity context;
    private final LayoutInflater inflater;

    public HNAdapter(Activity context, List<Item> items) {
        this.context = context;
        this.items = items.toArray(new Item[items.size()]);
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
        Item item = items[position];
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
        TextView authorText = (TextView) convertView.findViewById(R.id.author);
        authorText.setText(item.getBy());

        TextView commentText = (TextView) convertView.findViewById(R.id.comments);
        int numComments = 0;
        Integer[] kids = item.getKids();
        if (kids != null) {
            numComments = kids.length;
        }
        commentText.setText(numComments + " comments");

        TextView scoreText = (TextView) convertView.findViewById(R.id.score);
        Integer score = item.getScore();
        if (score == null) {
            score = 0;
        }
        scoreText.setText("Score: " + score);

        TextView linkView = (TextView)convertView.findViewById(R.id.link);
        if(item.getUrl() != null){
            linkView.setText(item.getUrl());
        }

        return convertView;
    }
}
