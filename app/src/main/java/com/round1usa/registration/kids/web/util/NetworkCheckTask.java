package com.round1usa.registration.kids.web.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;

import java.util.Timer;
import java.util.TimerTask;

public class NetworkCheckTask extends TimerTask {
    private WebView mWebView;

    private Handler  mHandler;
    private Timer    mTimer;
    private String   mUrl;

    private static final String ASSETS_URL_FORMAT = "file:///android_asset/html/%s";

    public NetworkCheckTask(Activity activity, WebView webView) {
        mWebView = webView;

        mTimer    = new Timer();
        mHandler  = new WebViewHandler(activity, webView, mTimer);
    }

    /**
     * Show static html page when network is ready.
     * @param htmlFile A Temporal page. It will be show when network is not avaliable.
     */
    public void loadSplash(String htmlFile) {
        mWebView.loadUrl(String.format(ASSETS_URL_FORMAT, htmlFile));
    }

    /**
     * Start watch network adapter. URL page shows when network is connected.
     * @param url
     */
    public void start(String url) {
        mTimer.scheduleAtFixedRate(this, 0L, 1000L);
        mUrl = url;
    }

    @Override
    public void run() {
        Message msg = Message.obtain(mHandler, 0, mUrl);
        mHandler.sendMessage(msg);
    }

    /**
     * A Hander what handles UI Component.
     * That works only network is avaliable.
     */
    private static class WebViewHandler extends Handler {
        private Activity mActivity;
        private WebView  mWebView;
        private Timer    mTimer;

        WebViewHandler(Activity activity, WebView webView, Timer timer) {
            mActivity = activity;
            mWebView  = webView;
            mTimer    = timer;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String url = (String) msg.obj;
                boolean available = WebkitUtil.isNetworkAvailable(mActivity);

                if (available) {
                    mWebView.loadUrl(url);
                    mTimer.cancel();
                }
            }
        }
    }
}
