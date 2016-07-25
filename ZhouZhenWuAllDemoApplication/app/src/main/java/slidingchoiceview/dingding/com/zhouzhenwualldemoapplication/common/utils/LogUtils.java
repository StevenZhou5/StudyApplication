package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.utils;

import android.util.Log;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.BuildConfig;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/7/25
 * 类简介：用来进行Lag日志输出的工具类
 */
public class LogUtils {
    private static String LOG_TAG = BuildConfig.LOG_TAG;


    public static void log(String contentString) {
        Log.d(LOG_TAG, contentString);
    }

    public static void log(int StringResId) {
        log(CommonUtils.getString(StringResId));
    }
}
