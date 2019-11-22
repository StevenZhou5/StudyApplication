package com.xiaomi.translate.framework.utils;

import android.util.Log;

import com.xiaomi.translate.framework.BuildConfig;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2019-11-18
 * 类简介：通用Log打点类
 */
public class LogUtils {
    public static final String DEFAULT_TAR_NAME = BuildConfig.VERSION_NAME;

    public static void d(String s) {
        Log.d(DEFAULT_TAR_NAME, s);
    }
}
