package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.utils;

import android.content.Context;

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
}
