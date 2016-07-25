package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.utils;

import android.content.Context;

import java.lang.reflect.Field;

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
        sb.subSequence(0, sb.length() - 2);
        return sb.toString();
    }


    /**
     * 获取状态栏高度，此方法在onCreate中调用也能获取到
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
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
