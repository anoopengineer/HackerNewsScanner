package hn.anoop.com.hackernews.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Akunju00c on 12/23/2014.
 */
public class Utils {
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void openInBrowser(Context context, String url){
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        context.startActivity(viewIntent);
    }

    public static URL getItemURL(Integer id) {
        String url = "https://hacker-news.firebaseio.com/v0/item/" + id + ".json";
        try {
            Log.e("ANOOP", url);
            return new URL(url);
        } catch (MalformedURLException e) {
            Log.e("ANOOP", " Exception on converting " + id + " to URL", e);
            return null;
        }

    }

}
