package hn.anoop.com.hackernews.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import hn.anoop.com.hackernews.R;
import hn.anoop.com.hackernews.datasource.DataSource;
import hn.anoop.com.hackernews.fragment.ItemDetailFragment;


public class ItemDetailActivity extends Activity {

    private DataSource mDataSource = DataSource.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        int id = getIntent().getIntExtra(ItemDetailFragment.KEY_ID, -1);
        if (savedInstanceState == null) {
            ItemDetailFragment fragment = new ItemDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ItemDetailFragment.KEY_ID, id);
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
