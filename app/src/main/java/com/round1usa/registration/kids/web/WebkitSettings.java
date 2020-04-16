package com.round1usa.registration.kids.web;


import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

class WebkitSettings {
    private Context     mContext;

    private WebView     mWebView;
    private WebSettings mWebSettings;

    WebkitSettings(Context context, WebView webView) {
        mContext = context;
        mWebView = webView;
        mWebSettings = mWebView.getSettings();
    }

    /**
     * initialize the web view.
     */
    void init() {
        // Set Cache Mode
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // Enable Javascript
        mWebSettings.setJavaScriptEnabled(true);

        // Enable Web Storage(Local, Database, ...)
        // Angular.JS 必須
        mWebSettings.setDomStorageEnabled(true);

        // Allows File Access
        mWebSettings.setAllowFileAccess(true);

        // User Action requires Media play
        mWebSettings.setMediaPlaybackRequiresUserGesture(false);

        mWebView.setWebViewClient(new WebkitWebViewClient());
        mWebView.setWebChromeClient(new WebkitWebChromeClient(mContext, mWebView));
    }
}
