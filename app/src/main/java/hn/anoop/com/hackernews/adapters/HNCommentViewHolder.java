package hn.anoop.com.hackernews.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.text.Html;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;


import hn.anoop.com.hackernews.R;
import hn.anoop.com.hackernews.model.Item;
import hn.anoop.com.hackernews.utils.UI;

/**
 * Created by Akunju00c on 1/8/2015.
 */
public class HNCommentViewHolder extends RecyclerView.ViewHolder{

    private static final PrettyTime P = new PrettyTime();
    private TextView commentView;
    private TextView dateTextView;
    private TextView authorTextView;
    private Spanned htmlText;
    private String date;


    public HNCommentViewHolder(View itemView) {
        super(itemView);
        View view = null;
        view = itemView.findViewById(R.id.hn_comments_author);
        if(view != null) {
            authorTextView = (TextView) view;
        }
        view = itemView.findViewById(R.id.hn_comments_date);
        if(view != null) {
            dateTextView = (TextView) view;
        }
        view =  itemView.findViewById(R.id.hn_comments_view);
        if(view != null){
            commentView = (TextView)view;
        }
    }

    public void setData(Item item){
        if(item.getType().equalsIgnoreCase("comment")){
            authorTextView.setText(item.getBy());
            if(date == null) {
                date = "posted "+P.format(new Date(item.getTime() * 1000));
            }
            dateTextView.setText(date);
            if(htmlText == null) {
                htmlText = Html.fromHtml(item.getText());
            }
            commentView.setText(htmlText);
            commentView.setMovementMethod(LinkMovementMethod.getInstance());
        }else{
            UI.populateCardView(itemView.getContext(), item, itemView);
        }
    }

}
