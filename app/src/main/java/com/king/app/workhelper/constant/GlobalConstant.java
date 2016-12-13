package com.king.app.workhelper.constant;

import android.support.annotation.IntDef;

/**
 * 全局常量类
 * Created by HuoGuangxu on 2016/9/29.
 */

public class GlobalConstant {
    public interface BUNDLE_PARAMS_KEY {
        String EXTRA_KEY = "EXTRA_KEY";
    }

    public interface INTENT_PARAMS_KEY {
        String FILE_DOWNLOAD_URL = "FILE_DOWNLOAD_URL";
    }

    /** 未知网络 */
    public static final int TYPE_NONE = -1;
    /** See:{@link android.net.ConnectivityManager#TYPE_WIFI wifi网络} */
    public static final int TYPE_WIFI = 0;
    /** See:{@link android.net.ConnectivityManager#TYPE_MOBILE 手机网络} */
    public static final int TYPE_MOBILE = 1;

    @IntDef({TYPE_NONE, TYPE_WIFI, TYPE_MOBILE})
    public @interface NETWORK_TYPE {

    }

    //SharedPreference的key
    public interface SP_PARAMS_KEY {
        //app更新是否下载完成
        String APP_UPDATE_SUCCESS = "APP_UPDATE_SUCCESS";
        //app下载的版本号
        String APP_UPDATE_VERSION = "APP_UPDATE_VERSION";
        /** app下载服务是否存在 */
        String DOWNLOAD_SERVER_EXISTS = "DOWNLOAD_SERVER_EXISTS";
        //是否显示过刷新账户向导 统计次数，第二次再弹出
        String REFRESH_ACCOUNT_GUIDE_COUNT = "REFRESH_ACCOUNT_GUIDE_COUNT";
    }
}