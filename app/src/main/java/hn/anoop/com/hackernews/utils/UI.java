package hn.anoop.com.hackernews.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import hn.anoop.com.hackernews.R;
import hn.anoop.com.hackernews.activities.WebViewActivity;
import hn.anoop.com.hackernews.model.Item;

/**
 * Created by Akunju00c on 12/29/2014.
 */
public class UI {
    private static final PrettyTime p = new PrettyTime();

    public static void populateCardView(final Context context, final Item item, View convertView){
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


        ImageView favIconView = (ImageView) convertView.findViewById(R.id.favicon);
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

            final String finalDomain = item.getDomain();
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
        Resources res = context.getResources();
        int numKids;
        Integer[] kids = item.getKids();
        if(kids == null){
            numKids = 0;
        }else{
            numKids = kids.length;
        }
        TextView commentsText = (TextView) convertView.findViewById(R.id.comments);
        commentsText.setText(String.format(res.getString(R.string.hn_card_comments), numKids));

        TextView scoreText = (TextView) convertView.findViewById(R.id.score);
        scoreText.setText(String.format(res.getString(R.string.hn_card_score), item.getScore()));




    }
}
