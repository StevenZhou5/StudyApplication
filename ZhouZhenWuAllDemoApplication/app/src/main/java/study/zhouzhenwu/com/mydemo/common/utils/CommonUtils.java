package study.zhouzhenwu.com.mydemo.common.utils;

import android.content.Context;

import java.lang.reflect.Field;

import study.zhouzhenwu.com.mydemo.MyApplication;
import study.zhouzhenwu.com.mydemo.common.module.SingleLinkedListNode;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/3/14
 * 类简介：
 */
public class CommonUtils {

    //dp转化为px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //px转化为dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将数组转换为用“，”分割的字符串
     *
     * @return
     */
    public static String intsToString(int[] input) {
        if (input == null || input.length < 1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i : input) {
            sb.append(i + ",");
        }
        return sb.subSequence(0, sb.length() - 1).toString();
    }

    /**
     * 通过资源Id获取字符串
     *
     * @param resId ：字符串的资源Id
     * @return
     */
    public static String getString(int resId) {
        String resultString = null;
        if (resId > 0) {
            MyApplication.instance.getResources().getString(resId);
        }
        return resultString;
    }

    /**
     * 获取状态栏高度，此方法在onCreate中调用也能获取到
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int resId = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(resId);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }


}
