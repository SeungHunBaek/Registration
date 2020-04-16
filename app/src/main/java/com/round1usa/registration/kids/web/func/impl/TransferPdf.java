package com.round1usa.registration.kids.web.func.impl;

import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.webkit.WebView;

import com.round1usa.registration.kids.R;
import com.round1usa.registration.kids.common.Constants;
import com.round1usa.registration.kids.print.adapter.RegistrationDocumentAdapter;
import com.round1usa.registration.kids.web.func.AsyncNativeCallBridge;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Transfer to pdf
 */
@SuppressWarnings("unused")
public class TransferPdf implements AsyncNativeCallBridge {
    private static final String TAG = "TransferPdf";

    @Override
    public void call(WebView webView, Context context, @NonNull JSONObject arg) {
        File rootDir = context.getFilesDir();

        JSONObject returns = new JSONObject();
        try {
            boolean result;

            returns.put("return", false);
            returns.put("caller", "transferPdf");

            String base64File = ((String) arg.get("file")).split(",")[1];
            byte[] bytes = Base64.decode(base64File, Base64.DEFAULT);

            saveFile(context, bytes, rootDir);
            doPrint(context, webView);

            returns.put("result", true);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveFile(final Context context, final byte[] bytes, final File rootDir) {
        final File pdfFile = new File(rootDir, Constants.PDF_FILE_NAME);

        try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(pdfFile))) {
            output.write(bytes);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void doPrint(Context context, WebView webView) {
        PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);

        String jobName = context.getString(R.string.app_name) + " Document";

        // Set Paper Info.
        PrintAttributes attributes = new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.NA_LETTER)
                .build();

        printManager.print(jobName, new RegistrationDocumentAdapter(context, webView), attributes);
    }
}
