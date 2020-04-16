package com.round1usa.registration.kids.web;

import android.content.Context;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.round1usa.registration.kids.web.func.AsyncNativeCallBridge;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

class WebkitWebChromeClient extends WebChromeClient {
    private static final String TAG = "WebChromeClient";
    private static final String LOG_FORMAT = "javascript: {0}, {1}";

    private static final String NATIVE_PREFIX = "native:";

    private static final String NATIVE_CALL_BRIDGE_IMPL_PACKAGE_NAME = "%s.web.func.impl";
    private Context mContext;

    private WebView mWebView;

    WebkitWebChromeClient(Context context, WebView webView) {
        mContext = context;
        mWebView = webView;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        result.cancel();

        // Alert message starts "native:"
        if (message.startsWith(NATIVE_PREFIX)) {

            // Parse arguments.
            String jsCall = message.replace(NATIVE_PREFIX, "");
            String functionName = jsCall.substring(0, jsCall.indexOf('('));
            String args = jsCall.substring(jsCall.indexOf('(') + 1, jsCall.lastIndexOf(')'));

            // Get the function name. It's meaning AsyncNativeCallbridge implements.
            // e.g.) transferPdf -> {mContext.packageName}.web.func.impl.TransferPdf.class
            String functionClassName = functionName.substring(0, 1).toUpperCase() + functionName.substring(1, functionName.length());
            String fullClassName = String.format(NATIVE_CALL_BRIDGE_IMPL_PACKAGE_NAME, mContext.getPackageName()) + "." + functionClassName;

            try {
                Class<?> functionClass = Class.forName(fullClassName);

                List<Class<?>> interfaces = Arrays.asList(functionClass.getInterfaces());

                // Native callback classes must be implementing AsyncNativeCallBridge.
                if (interfaces.contains(AsyncNativeCallBridge.class)) {
                    AsyncNativeCallBridge bridge = (AsyncNativeCallBridge) functionClass.newInstance();
                    bridge.call(mWebView, mContext, new JSONObject(args));
                } else {
                    throw new RuntimeException("Wrong Interface.");
                }
            } catch (ReflectiveOperationException | JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            // JsAlert is not native call, make toast message.
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    /**
     * Logcat with WebBrowser console message.
     * @param consoleMessage
     * @return
     */
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.d(TAG, MessageFormat.format(LOG_FORMAT, consoleMessage.messageLevel(), consoleMessage.message()));
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public void onPermissionRequest(PermissionRequest request) {
       request.grant(request.getResources());
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }
}
