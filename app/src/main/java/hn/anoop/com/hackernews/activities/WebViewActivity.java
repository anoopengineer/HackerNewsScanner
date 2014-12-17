package hn.anoop.com.hackernews.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import hn.anoop.com.hackernews.R;

public class WebViewActivity extends Activity {

    public static final String KEY = "hn.anoop.com.hackernew.activities.WebViewActivity.URL_KEY";
    private ProgressBar progressBar;

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.show();
            actionBar.setIcon(android.R.color.transparent);
        }
        setTitle(R.string.loading);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        initializeWebView();
    }

    private void initializeWebView() {
        progressBar.setVisibility(View.VISIBLE);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        String url = getIntent().getStringExtra(KEY);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        //if ROM supports Multi-Touch
        webView.getSettings().setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM

        // Sets the Chrome Client, and defines the onProgressChanged
        // This makes the Progress bar be updated.
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                progressBar.setProgress(progress); //Make the bar disappear after URL is loaded
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title)) {
                    WebViewActivity.this.setTitle(stripHtml(title));
                }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
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
        }else if(id == android.R.id.home){
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }
}
