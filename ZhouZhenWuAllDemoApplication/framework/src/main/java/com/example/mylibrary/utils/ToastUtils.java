package com.example.mylibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/3/27
 * 类简介：处理统一Toast相关的工具类
 */

public class ToastUtils {

    public static void showShortToast(Context context, String toastString) {
        Toast.makeText(context.getApplicationContext(), toastString, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(Context context, int stringResId) {
        String toastString = context.getString(stringResId);
        showShortToast(context, toastString);
    }

    public static void showLongToast(Context context, String toastString) {
        Toast.makeText(context.getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(Context context, int stringResId) {
        String toastString = context.getString(stringResId);
        showShortToast(context, toastString);
    }

}
