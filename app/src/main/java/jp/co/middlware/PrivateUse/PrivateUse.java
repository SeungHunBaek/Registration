package jp.co.middlware.PrivateUse;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import jp.co.middlware.PrivateUse.common.PrivateUseConstants;

/**
 * 専用利用化サービス用クラス
 * @author Fsi
 *
 */
public class PrivateUse extends Service {

    private static final String TAG = "PrivateUse";

    IPrivateUseListener privateUseListener = null;
    LinearLayout topLayout;

    private long lastClickTime = 0;                          // 前回タップした時間
    private int areaValue;                                   // 抑止対象エリア値
    private int touchCount;                                  // 設定アプリを起動するタップ回数
    private long intervalTime;                               // タップする時間間隔

    private int setResultValue = PrivateUseConstants.RESULT_VALUE_NG;  // 抑止対象エリアデフォルト指定NG
    private int addCount = 0;                                // 設定アプリを起動する用カウント

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * サービスバインド開始、重ねわせViewを設定する
     */
    @Override
    public IBinder onBind(Intent intent) {

        setResultValue = PrivateUseConstants.RESULT_VALUE_NG;
        // デフォルトの抑止対象エリアが画面上部
        areaValue = intent.getIntExtra(PrivateUseConstants.ACTION_KEY_MASK_AREA, 1);
        // デフォルトの設定アプリを起動するタップ回数が10回
        touchCount = intent.getIntExtra(PrivateUseConstants.ACTION_KEY_TOUCH_COUNT, 10);
        // デフォルトのタップする時間間隔が0.5s
        intervalTime = intent.getLongExtra(PrivateUseConstants.ACTION_KEY_INTERVAL_TIME, 500);

        if (PrivateUseConstants.LOG_FLAG) {
            Log.d(TAG, "The input areaValue's value is ： " + areaValue);
            Log.d(TAG, "The input touchCount's value is ： " + touchCount);
            Log.d(TAG, "The input intervalTime's value is ： " + intervalTime);
        }

        if (!checkParament()) {
            return new ServiceImpl();
        }

        // 画面上部エリアを抑止処理
        if ((areaValue & PrivateUseConstants.MASK_AREA_TOP) == PrivateUseConstants.MASK_AREA_TOP) {

            if (PrivateUseConstants.LOG_FLAG) {
                Log.d(TAG, "Top View area is settinged.");
            }
            // 作成した重ね合わせするViewをWindowManager上にカバーする
            addView(Gravity.TOP, getStatusBarHeight());
        }

        return new ServiceImpl();
    }

    /**
     * 渡すパラメータをチェック
     * @return boolean
     */
    private boolean checkParament() {

        // 入力したパラメータはエラーがある
        if (3 < areaValue || areaValue <= 0 || touchCount <= 0 || intervalTime <= 0) {
            setResultValue = PrivateUseConstants.RESULT_VALUE_NG;  // パラメンータがNG
            return false;
        }

        if (getStatusBarHeight() == 0
                && ((areaValue & PrivateUseConstants.MASK_AREA_TOP) == PrivateUseConstants.MASK_AREA_TOP)) {
            // アクションバーがない
            setResultValue = PrivateUseConstants.RESULT_VALUE_NEGATIVE;
            return false;
        }

        // ナビバーの有無チェック
        if (checkDeviceHasNavigationBar()) {
            if (getNavigationBarHeight() == 0
                    || ((areaValue & PrivateUseConstants.MASK_AREA_BOTTOM) == PrivateUseConstants.MASK_AREA_BOTTOM)) {
                // ナビバーがあって、指定場合
                setResultValue = PrivateUseConstants.RESULT_VALUE_NG;
                return false;
            }
        } else {
            if ((areaValue & PrivateUseConstants.MASK_AREA_BOTTOM) == PrivateUseConstants.MASK_AREA_BOTTOM) {
                // ナビバーがなくて、指定場合
                setResultValue = PrivateUseConstants.RESULT_VALUE_NG;
                return false;
            }
        }
        setResultValue = PrivateUseConstants.RESULT_VALUE_OK;      // パラメータがOK
        return true;
    }

    /**
     * 作成した重ね合わせするViewをWindowManager上にカバーする
     * @param position
     * @param height
     */
    private void addView(int position, int height) {

        WindowManager.LayoutParams params;
        WindowManager windowManager;

        // 重ねわせレイアウトのパラメンータを取得
        params = getLayoutParams(Gravity.LEFT, position, height);

        // WindowManagerを取得する
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // 重ね合わせするViewを作成
        if (Gravity.TOP == position) {
            topLayout = createView();
            windowManager.addView(topLayout, params);
        }
    }

    /**
     * 重ねわせレイアウトのパラメンータを取得
     * @param posHorizontal 横位置
     * @param posVertical 縦位置
     * @param height 高度
     * @return params
     */
    private WindowManager.LayoutParams getLayoutParams(int posHorizontal, int posVertical, int height) {

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSPARENT);
        // 開始座標の設定
        params.gravity = posHorizontal | posVertical;
        params.x = 0;
        params.y = 0;
        // ステータスバーを隠すView高さの設定
        params.height = height;

        return params ;
    }

    /**
     * 重ね合わせするViewを作成
     * @return view
     */
    private LinearLayout createView() {

        LinearLayout view = new LinearLayout(this);
        view.setLayoutParams(new LayoutParams(0, 0));
//        view.setBackgroundColor(Color.RED); // 隠すViewのカラー


        // Settingアプリを起動リスナー
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    startSettingApp();
            }
        });

        return view;
    }

    /**
     * 設定アプリを起動する処理
     */
    private synchronized void startSettingApp() {
        long time = System.currentTimeMillis();

        if (lastClickTime == 0) {
            addCount = 1;
            lastClickTime = time;
            return;
        }
        // 今回タップ時間 - 前回タップ時間
        Log.d(TAG, "time - lastClickTime= "+(time - lastClickTime ));
        if (lastClickTime == 0 || time - lastClickTime <= intervalTime) {

            addCount++;
            lastClickTime = time;

            if (PrivateUseConstants.LOG_FLAG) {
                Log.d(TAG, "Clicked " + addCount + " times continuously");
            }

        } else {
            addCount = 1;
            lastClickTime = 0;
        }

        if (PrivateUseConstants.LOG_FLAG) {
            Log.d(TAG, "addCount =  " + addCount);
        }

        if (addCount == touchCount) {
            if (PrivateUseConstants.LOG_FLAG) {
                Log.d(TAG, "Clicked 10 times. Start up Setting App!");
            }
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            addCount = 0;
            lastClickTime = 0;
        }
    }

    /**
     * 専用利用化サービスと通信用レジスタ
     * @author Fsi
     *
     */
    public class ServiceImpl extends IPrivateUse.Stub {
        public void register(IPrivateUseListener listen) {
            Log.d(TAG, "Register listener.");
            try {
                privateUseListener = listen;
                if (privateUseListener != null) {
                    // ReturnMessageを経由して、抑止結果を返却
                    privateUseListener.ReturnMessage(setResultValue);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * バインド解除、サービスが破棄されるときには重ね合わせしていたViewを削除する
     */
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind()");

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // サービスが破棄されるときには重ね合わせしていたViewを削除する
        if (topLayout != null) {
            windowManager.removeView(topLayout);
        }

        return super.onUnbind(intent);
    }

    /**
     * ステータスバーを隠すレイヤー高さを取得
     * @return sbar
     */
    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getBaseContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return sbar;
    }

    /**
     * 持っているデバイスがナビバーがあるかをチェック処理
     * @return boolean
     */
    private boolean checkDeviceHasNavigationBar() {

        // デバイスのバックキー、メニューキー(持っているデバイスのHOME以外のキー)の有無を判断して、navigation barがあるかを判断する
        boolean hasMenuKey = ViewConfiguration.get(getBaseContext()).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 持っているデバイスがナビバーがある
            return true;
        }
        return false;
    }

    /**
     * ナビバーを隠すレイヤー高さを取得
     * @return height
     */
    private int getNavigationBarHeight() {
        Resources resources = getBaseContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        // navigationBarの高さを取得
        int height =resources.getDimensionPixelSize(resourceId);

        return height;
    }

}
