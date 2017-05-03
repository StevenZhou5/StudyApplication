package study.zhouzhenwu.com.mydemo.common.utils;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/4/6
 * 类简介：
 */

public class ViewUtils {
    public static final int TAG_INT_TYPE_DEFAULT = -1; // 默认整型Tag值

    /**
     * 获取整型的Tag
     *
     * @param v
     * @return
     */
    public static int getTagToInt(View v) {
        Object tag = v.getTag();
        int TagType = TAG_INT_TYPE_DEFAULT;
        if (tag != null) {
            TagType = (int) v.getTag();
        }
        return TagType;
    }

    /**
     * 获取view在当前Activity根View中的所占的区域
     *
     * @param targetView
     * @return
     */
    public static Rect getViewRectInActivity(View targetView) {
        Rect resultRect = new Rect();
        targetView.getGlobalVisibleRect(resultRect);
        return resultRect;
    }

    /**
     * 获取view在当前Activity根View中的区域的距离Activty根View的X轴距离和Y轴距离
     *
     * @param targetView
     * @return
     */
    public static Point getViewOffsetInActivity(View targetView) {
        Rect globalRect = new Rect();
        Point globalOffset = new Point();
        targetView.getGlobalVisibleRect(globalRect, globalOffset);
        return globalOffset;
    }


    /**
     * 判断一个目标View在容器View中是否可见
     *
     * @param targetView    目标View
     * @param contentView   容器View
     * @param heightPercent 满足可见要求时显示高度占目标View总高度的百分比（必须是0-1之间）
     * @param widthPercent  满足可见要求时显示宽度占目标View总高度的百分比（必须是0-1之前）
     * @return
     */
    public static boolean checkViewIsVisible(View targetView, ViewGroup contentView, double heightPercent,
                                             double widthPercent) {
        // step1: 获取目标View相对与Activity的位置
        Rect targetRect = new Rect();
        targetView.getGlobalVisibleRect(targetRect);
//        log("targetRect:" + "left:" + targetRect.left + "; right:" + targetRect.right + "; top:"
//                + targetRect.top + "; bottom:" + targetRect.bottom);

        // step2: 获取包含目标View的容器View相对与Activity的位置
        Rect contentRect = new Rect();
        contentView.getGlobalVisibleRect(contentRect);
//        log("contentRect:" + "left:" + contentRect.left + "; right:" + contentRect.right + "; top:"
//                + contentRect.top + "; bottom:" + contentRect.bottom);

        // step3: 判断如果目标View的顶部在容器View的底部之下 或者 目标View的底部在容器View的顶部之上 或者
        // 目标View的左边在容器View的右边之后 或者 目标View的右边在容器View的左边之前 都说明View不可见
        if (targetRect.top >= contentRect.bottom || targetRect.bottom <= contentRect.top
                || targetRect.left >= contentRect.right || targetRect.right <= contentRect.left) {
            return false;
        }

        // step4:判断垂直方向是否满足可见要求
        heightPercent = heightPercent > 1 ? 1 : heightPercent; // 控制高度占比不能超过1
        heightPercent = heightPercent < 0 ? 0 : heightPercent; //  控制高度占比不能小于0 ；0代表始终可见，不一定符合实际业务
        int height = targetView.getHeight(); // 目标View的总高度
        int minHeight = (int) (height * heightPercent); // 认为目标View处于展示状态所要求的最小展示高度
        int visibleHeight = targetRect.bottom - targetRect.top;
        boolean isVerticalVisible = visibleHeight >= minHeight;

        // step6: 判断当前水平方向是否可见
        widthPercent = widthPercent > 1 ? 1 : widthPercent; // 控制宽度占比不能超过1
        widthPercent = widthPercent < 0 ? 0 : widthPercent; // 控制宽度占比不能小于0；0代表始终可见，不一定符合实际业务
        int width = targetView.getWidth(); // 目标View的总宽度
        int minWidth = (int) (width * widthPercent);
        int visibleWidth = targetRect.right - targetRect.left;
        boolean isHorizontalVisible = visibleWidth >= minWidth;

        // step7: 目标View在容器中是否可见
        boolean isVisible = isVerticalVisible && isHorizontalVisible; // 当垂直和水平方向都满足条件是才认为可见
        return isVisible;
    }

}
