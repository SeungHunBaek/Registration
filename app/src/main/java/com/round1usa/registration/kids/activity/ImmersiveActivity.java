package com.round1usa.registration.kids.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.round1usa.registration.kids.camera.NullStateCallback;

import java.io.DataOutputStream;
import java.io.IOException;

import jp.co.middlware.PrivateUse.IPrivateUse;
import jp.co.middlware.PrivateUse.IPrivateUseListener;
import jp.co.middlware.PrivateUse.PrivateUse;
import jp.co.middlware.PrivateUse.common.PrivateUseConstants;

/**
 * Force Set Immersive but Keyboard
 * just extends
 */
public abstract class ImmersiveActivity extends Activity {
    private boolean binded;
    private IPrivateUse myManager;
    private String[] permissions;

    /** Request code for manifest.xml permission */
    private static final int REQUEST_CODE_PERMISSION = 0x4113;

    /** Request code for FSI Middleware.  */
    private static final int REQUEST_CODE_OVERLAY = 0x4114;

    public ImmersiveActivity(String[] permissions) {
        this.permissions = permissions;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getRootAuth();

        View decorView = getWindow().getDecorView();

        // Watch System UI visibility updates.
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                setToImmersiveMode();
            }
        });

        setToImmersiveMode();

        // for ANDROID 6.0 and later
        // Overlay wrapping requires User agreement.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION);
            }
        } else {
            bindService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Request From Permission
        // SYSTEM_ALERT_WINDOW is Special Permission. It will be open other alert window.
        if (requestCode == REQUEST_CODE_PERMISSION) {
            for (String permission: permissions) {
                if (Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permission) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestOverlayPermission();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToImmersiveMode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();

        // permission check needed android 6.0 and later
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
           return;
        }

        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        // OpenCamera will be close itself.
        try {
            cameraManager.openCamera("test", new NullStateCallback(), null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /* Copied from Middleware sample. */

    private void unbindService() {
        if (binded) {
            unbindService(conn);
            binded = false;
        }
    }

    private void bindService() {
        Intent intent = new Intent(this, PrivateUse.class);
        intent.setAction(PrivateUseConstants.ACTION_SETTINGS_CONTROL);
        intent.putExtra(PrivateUseConstants.ACTION_KEY_MASK_AREA, PrivateUseConstants.MASK_AREA_TOP);
        intent.putExtra(PrivateUseConstants.ACTION_KEY_TOUCH_COUNT, 10);
        intent.putExtra(PrivateUseConstants.ACTION_KEY_INTERVAL_TIME, 500L);

        this.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("aixq", "@@@ service.hashCode() = " + service.hashCode());
            binded = true;
            myManager = IPrivateUse.Stub.asInterface(service);
            try {
                myManager.register(new myListener());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        class myListener extends IPrivateUseListener.Stub {
            public void ReturnMessage(int result) throws RemoteException {
                // サービス側実行結果を返却値resultString
                if (result == 0) {
                    // 指定対象エリアOK
                    Toast.makeText(ImmersiveActivity.this, "Private Use turned ON.", Toast.LENGTH_SHORT).show();
                } else if (result == 1) {
                    // 未指定抑止対象エリア
                    Toast.makeText(ImmersiveActivity.this, "Private Use Error 1.", Toast.LENGTH_SHORT).show();
                } else if (result == 2) {
                    // 指定抑止対象エリアNG
                    Toast.makeText(ImmersiveActivity.this, "Private Use Error 2.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /* Copied from Middleware sample. */

    /**
     * Android 6.0 Usage: Open Android system alert.
     */
    public void requestOverlayPermission() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            this.startActivityForResult(intent, REQUEST_CODE_OVERLAY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_OVERLAY) {
            bindService();
        }
    }

    /**
     * Set Immersive Mode.
     */
    private void setToImmersiveMode() {
        // set to immersive

        // 参考: フルスクリーンモード(FULLSCREEN)の場合、入力ボックスをタッチしても自動スクロールされない。
        // LAYOUT_STABLE          : バーを隠す前に、枠分の空間を確保しておく。
        // LOW_PROFILE            : 表示中にバーのアイコンが・に表示される。( キーボードが表示されている場合はアイコンが表示される)
        //                          HIDE_NAVIGATIONを入れないと下端バーが隠れない。
        // LAYOUT_HIDE_NAVIGATION : 上端バーが表示されている分の領域をアプリ側で使える。
        // HIDE_NAVIGATION        : バーを隠す。(上端バーを隠すにはFULLSCREENが必要)　※自動スクロールされない。
        // IMMERSIVE_STICKY       : 強制没入モード(下端バーが消える)にする。
        // 解決策は現在調査中
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void getRootAuth() {
        Process p;
        try {
            p = Runtime.getRuntime().exec("su");
            DataOutputStream dos = new DataOutputStream(p.getOutputStream());
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
