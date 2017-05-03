package study.zhouzhenwu.com.mydemo.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/4/27
 * 类简介：
 */

public class ScreenUtils {
    //获取屏幕宽度
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);//this指当前activity

        return dm.widthPixels;
    }

    //获取屏幕高度
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);//this指当前activity

        return dm.heightPixels;
    }

    public static int getScreenStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }
}
