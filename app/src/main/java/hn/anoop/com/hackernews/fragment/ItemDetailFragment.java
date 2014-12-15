package hn.anoop.com.hackernews.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hn.anoop.com.hackernews.R;
import hn.anoop.com.hackernews.model.Item;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link hn.anoop.com.hackernews.activities.MainActivity}
 * in two-pane mode (on tablets) or a {@link hn.anoop.com.hackernews.activities.ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The data this fragment is presenting.
     */
    private Item mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {

    }

    public void setItem(Item item) {
        this.mItem = item;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            //TODO: Remove the hard codings
            ((TextView) rootView.findViewById(R.id.item_detail)).setText("Anoop");
        }

        return rootView;
    }
}
