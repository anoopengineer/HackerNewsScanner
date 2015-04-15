package hn.anoop.com.hackernews.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import hn.anoop.com.hackernews.R;
import hn.anoop.com.hackernews.activities.WebViewActivity;
import hn.anoop.com.hackernews.adapters.HNCommentsAdapter;
import hn.anoop.com.hackernews.datasource.DataSource;
import hn.anoop.com.hackernews.model.Item;
import hn.anoop.com.hackernews.utils.UI;

public class ItemDetailFragment extends Fragment {
    public static final String KEY_ID = "item_id";

    private Item mItem;
    private RecyclerView mCommentsView;

    public ItemDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int id = bundle.getInt(KEY_ID);
            mItem = DataSource.getInstance().getById(id);
        }
        mCommentsView = (RecyclerView) rootView.findViewById(R.id.comments_recycler_view);
        mCommentsView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCommentsView.setLayoutManager(mLayoutManager);
        HNCommentsAdapter adapter = new HNCommentsAdapter(mItem.getId());
        mCommentsView.setAdapter(adapter);
        return rootView;
    }
}
