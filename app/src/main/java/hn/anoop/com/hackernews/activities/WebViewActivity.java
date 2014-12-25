package hn.anoop.com.hackernews.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import hn.anoop.com.hackernews.R;
import hn.anoop.com.hackernews.fragment.WebViewFragment;

public class WebViewActivity extends Activity implements WebViewFragment.OnFragmentInteractionListener {

    public static final String URL = "hn.anoop.com.hackernew.activities.WebViewActivity.URL_KEY";
    public static final String DOMAIN = "hn.anoop.com.hackernew.activities.WebViewActivity.DOMAIN_KEY";

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setTitle(R.string.loading);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.show();
            actionBar.setIcon(android.R.color.transparent);
        }

        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    private Fragment createFragment() {
        String url = getIntent().getStringExtra(URL);
        String domain = getIntent().getStringExtra(DOMAIN);
        return WebViewFragment.newInstance(url,domain);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        //do nothing
    }
}
