package study.zhouzhenwu.com.mydemo.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

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


    public static String getProcessName(Context context) {
        // get by ams
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
        if (processes != null) {
            int pid = android.os.Process.myPid();
            for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
                if (processInfo.pid==pid && !TextUtils.isEmpty(processInfo.processName)) {
                    return processInfo.processName;
                }
            }
        }

        // get from kernel
        String ret = getProcessName(android.os.Process.myPid());
        if (!TextUtils.isEmpty(ret) && ret.contains(context.getPackageName())) {
            return ret;
        }

        return null;
    }

    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Exception e) {
            Log.e("ZZW", "getProcessName read is fail. exception=" + e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Log.e("ZZW", "getProcessName close is fail. exception=" + e);
            }
        }
        return null;
    }


}
