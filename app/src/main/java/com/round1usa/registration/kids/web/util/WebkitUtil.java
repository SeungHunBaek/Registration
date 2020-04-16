package com.round1usa.registration.kids.web.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.WebView;

public class WebkitUtil {
    /**
     * Checks connectivity of your network adapter.
     */
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null) {
                return false;
            } else {
                NetworkInfo.State state = networkInfo.getState();

                if (state == NetworkInfo.State.CONNECTED) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    /**
     * Move page through Javascript callback function.
     */
    public static void forward(WebView webView, String url) {
        String template = "javascript:bridge.forward('%s');";
        String _url = url.replace("'", "\\'");

        webView.loadUrl(String.format(template, _url));
    }
}
