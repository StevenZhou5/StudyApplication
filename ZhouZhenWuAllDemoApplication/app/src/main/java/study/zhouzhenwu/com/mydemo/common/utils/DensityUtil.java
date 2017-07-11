package study.zhouzhenwu.com.mydemo.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Pair;


/**
 * @author xiexinhong (xiexinhong@meituan.com) on 16/11/29.
 */

public class DensityUtil {

    private DensityUtil() {

    }

    /**
     * 获取屏幕宽高,返回Pair<Integer,Integer></>数组,第一个是屏幕宽,第二个是屏幕高
     */
    public static Pair<Integer, Integer> getScreenSize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return new Pair<>(metrics.widthPixels, metrics.heightPixels);
    }

    /**
     * 获取屏幕宽
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕高
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * dp转成为px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转成为dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将px值转换为sp值。
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值。
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
