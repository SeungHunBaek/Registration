package jp.co.middlware.PrivateUse.common;

public class PrivateUseConstants {

    public static final boolean LOG_FLAG = true;
    /**
     * 抑止対象のエリアの上部
     */
    public static final int MASK_AREA_TOP = 1;
    /**
     * 抑止対象のエリアの下部
     */
    public static final int MASK_AREA_BOTTOM = 2;
    /**
     * 専用利用化サービスを起動用アクション
     */
    public static final String ACTION_SETTINGS_CONTROL = "jp.co.privateusemiddle.action.setttings.control";
    /**
     * 専用利用化サービスを起動用アクションのキー
     */
    public static final String ACTION_KEY_MASK_AREA = "MASK_AREA";
    /**
     * 設定アプリを起動するタップ回数
     */
    public static final String ACTION_KEY_TOUCH_COUNT = "TOUCH_COUNT";
    /**
     * タップする時間間隔、超えた場合タップ回数初期化になる
     */
    public static final String ACTION_KEY_INTERVAL_TIME = "INTERVAL_TIME";
    /**
     * InputパラメンータはOK場合、0値を返却
     */
    public static final int RESULT_VALUE_OK = 0;
    /**
     * InputパラメンータはNG場合、1値を返却
     */
    public static final int RESULT_VALUE_NG = 1;
    /**
     * Inputパラメンータは0又は負の数場合、2値を返却
     */
    public static final int RESULT_VALUE_NEGATIVE = 2;
}
