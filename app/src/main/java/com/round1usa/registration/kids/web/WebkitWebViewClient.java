package com.round1usa.registration.kids.web;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class WebkitWebViewClient extends WebViewClient {

    /**
     * Bypass SSL Certificate checking.
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }
}
