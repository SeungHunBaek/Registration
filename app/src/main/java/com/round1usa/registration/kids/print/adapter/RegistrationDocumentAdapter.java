package com.round1usa.registration.kids.print.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import android.webkit.WebView;

import com.round1usa.registration.kids.common.Constants;
import com.round1usa.registration.kids.web.util.WebkitUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegistrationDocumentAdapter extends PrintDocumentAdapter {
    private static final String AFTER_PRINT_PAGE = Constants.SINGLE_PAGE_AFTER_PRINT;

    private Context mContext = null;
    private WebView mWebView = null;

    public RegistrationDocumentAdapter(Context context, WebView webView) {
        mContext = context;
        mWebView = webView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    /**
     * On Printer Preview is opening.
     */
    @Override
    public void onLayout(PrintAttributes oldAttributes,
                         PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback,
                         Bundle extras) {

        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            gotoCompletePage();
            return;
        }

        PrintDocumentInfo info = new PrintDocumentInfo
                .Builder(Constants.PDF_FILE_NAME)
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .build();
        callback.onLayoutFinished(info, true);
        gotoCompletePage();
    }

    /**
     * On document is writing.
     */
    @Override
    public void onWrite(PageRange[] pages,
                        ParcelFileDescriptor destination,
                        CancellationSignal cancellationSignal,
                        WriteResultCallback callback) {
        File pdfFile = new File(mContext.getFilesDir(), Constants.PDF_FILE_NAME);

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        if (cancellationSignal.isCanceled()) {
            callback.onWriteCancelled();
            return;
        }

        try {
            input = new BufferedInputStream(new FileInputStream(pdfFile));

            // OutputStream from Printer Service.
            output = new BufferedOutputStream(new FileOutputStream(destination.getFileDescriptor()));

            byte[] buf = new byte[Constants.WRITE_BUFFER_SIZE];
            int bytesRead;

            // Copy PDF file to printer.
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
            output.flush();

            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                // do nothing
            }
        }
        String rotation = getRotation(mContext);
        execTouchEvent(rotation);
    }

    // Move to Complete Page
    private void gotoCompletePage() {
        WebkitUtil.forward(mWebView, AFTER_PRINT_PAGE);
    }

    private void execTouchEvent(String rotation) {
        final String TAG = "fsi_test";
        Log.d("DocumentAdapter", "execTouchEvent_click");
        Process p;
        String tap;
        // 送信するeventの情報を設定
        if(rotation.equals("landscape")) {
            tap = "sendevent /dev/input/event0 3 47 1\n"
                    + "sendevent /dev/input/event0 3 57 375\n"
                    + "sendevent /dev/input/event0 3 53 613\n"
                    + "sendevent /dev/input/event0 3 54 1223\n"
                    + "sendevent /dev/input/event0 3 48 14\n"
                    + "sendevent /dev/input/event0 3 58 140\n"
                    + "sendevent /dev/input/event0 1 330 1\n"
                    + "sendevent /dev/input/event0 1 325 1\n"
                    + "sendevent /dev/input/event0 3 47 9\n"
                    + "sendevent /dev/input/event0 3 57 4294967295\n"
                    + "sendevent /dev/input/event0 0 0 0\n"
                    + "sendevent /dev/input/event0 3 47 1\n"
                    + "sendevent /dev/input/event0 3 48 9\n"
                    + "sendevent /dev/input/event0 3 58 100\n"
                    + "sendevent /dev/input/event0 0 0 0\n"
                    + "sendevent /dev/input/event0 3 57 4294967295\n"
                    + "sendevent /dev/input/event0 0 0 0\n"
                    + "sendevent /dev/input/event0 1 330 0\n"
                    + "sendevent /dev/input/event0 1 325 0\n"
                    + "sendevent /dev/input/event0 0 0 0\n"
                    + "sleep 0.000\n";
        } else if (rotation.equals("reverse landscape")) {
            tap = "sendevent /dev/input/event0 3 57 2584\n"
                    + "sendevent /dev/input/event0 3 53 186\n"
                    + "sendevent /dev/input/event0 3 54 49\n"
                    + "sendevent /dev/input/event0 3 48 16\n"
                    + "sendevent /dev/input/event0 3 58 216\n"
                    + "sendevent /dev/input/event0 1 330 1\n"
                    + "sendevent /dev/input/event0 1 325 1\n"
                    + "sendevent /dev/input/event0 0 0 0\n"
                    + "sendevent /dev/input/event0 3 58 217\n"
                    + "sendevent /dev/input/event0 0 0 0\n"
                    + "sendevent /dev/input/event0 3 53 185\n"
                    + "sendevent /dev/input/event0 3 54 48\n"
                    + "sendevent /dev/input/event0 3 58 210\n"
                    + "sendevent /dev/input/event0 0 0 0\n"
                    + "sendevent /dev/input/event0 3 53 184\n"
                    + "sendevent /dev/input/event0 3 48 15\n"
                    + "sendevent /dev/input/event0 3 58 122\n"
                    + "sendevent /dev/input/event0 0 0 0\n"
                    + "sendevent /dev/input/event0 3 57 4294967295\n"
                    + "sendevent /dev/input/event0 0 0 0\n"
                    + "sleep 0.000\n";
        } else {
            return;
        }

        try {
            p = Runtime.getRuntime().exec("su");
            DataOutputStream dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes(tap);
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRotation(Context context){
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return "portrait";
            case Surface.ROTATION_90:
                return "landscape";
            case Surface.ROTATION_180:
                return "reverse portrait";
            default:
                return "reverse landscape";
        }
    }

}
