package hn.anoop.com.hackernews.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import hn.anoop.com.hackernews.R;
import hn.anoop.com.hackernews.adapters.HNAdapter;
import hn.anoop.com.hackernews.datasource.DataSource;
import hn.anoop.com.hackernews.fragment.ItemDetailFragment;
import hn.anoop.com.hackernews.fragment.ItemListFragment;
import hn.anoop.com.hackernews.model.Item;


public class MainActivity extends Activity implements ItemListFragment.Callbacks, DataSource.DataListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private DataSource mDataSource = DataSource.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ANOOP", "In MainActivity onCreate");
        setContentView(R.layout.activity_main);
        getActionBar().show();

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ItemListFragment) getFragmentManager().findFragmentById(R.id.item_list)).setActivateOnItemClick(true);

        }
        // TODO:  handle intents here for deep links into your app,

        //TODO: Change to a singleton instance here and in ItemDetailActivity


        mDataSource.setDataListener(this);
        mDataSource.fetchData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        if (!mNavigationDrawerFragment.isDrawerOpen()) {
//            // Only show items in the action bar relevant to this screen
//            // if the drawer is not showing. Otherwise, let the drawer
//            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.navigation_drawer, menu);
//            restoreActionBar();
//            return true;
//        }
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
//            Bundle arguments = new Bundle();
//            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setItem(mDataSource.getById(Integer.parseInt(id)));
//            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public void dataUpdated() {
        Log.e("ANOOP", "In MainActivity dataUpdated");
        if (mTwoPane) {
//TODO:
        }else{
            ItemListFragment itemListFragment = (ItemListFragment) getFragmentManager().findFragmentById(R.id.item_list);
            BaseAdapter adapter = new HNAdapter(this, mDataSource.getAll());
            itemListFragment.setListAdapter(adapter);
        }
    }
}