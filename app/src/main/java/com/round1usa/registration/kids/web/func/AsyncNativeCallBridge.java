package com.round1usa.registration.kids.web.func;

import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.WebView;

import org.json.JSONObject;

public interface AsyncNativeCallBridge {
    void call(WebView webView, Context context, @NonNull JSONObject arg);
}
