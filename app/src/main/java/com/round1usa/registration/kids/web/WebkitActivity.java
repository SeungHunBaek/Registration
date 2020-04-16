package com.round1usa.registration.kids.web;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import com.round1usa.registration.kids.R;
import com.round1usa.registration.kids.activity.ImmersiveActivity;
import com.round1usa.registration.kids.common.Constants;
import com.round1usa.registration.kids.web.util.NetworkCheckTask;

public class WebkitActivity extends ImmersiveActivity {

    public WebkitActivity() {
        super(new String[] { Manifest.permission.CAMERA, Manifest.permission.SYSTEM_ALERT_WINDOW });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webkit);

        WebView webView = (WebView) findViewById(R.id.webkit);

        WebkitSettings settings = new WebkitSettings(this, webView);
        settings.init();

        NetworkCheckTask task = new NetworkCheckTask(this, webView);
        task.loadSplash("loading.html");
        task.start(Constants.SINGLE_PAGE_URL_P01);
    }

    @Override
    public void onBackPressed() {
        // back button not works
    }
}
