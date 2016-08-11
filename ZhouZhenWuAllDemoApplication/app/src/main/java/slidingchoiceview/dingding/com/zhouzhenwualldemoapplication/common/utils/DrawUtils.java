package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.utils;

import android.graphics.Paint;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/9
 * 类简介：与Canvas绘制相关的工具类
 */
public class DrawUtils {

    /**
     * 在Canvas上绘制Text时，得到在某一局域中让Text垂直居中的baseLine（及drawText中的Y值）的值
     *
     * @param top    绘制Text区域的顶部位置
     * @param bottom 绘制Text区域的底部位置
     * @param paint  绘制Text时使用的画笔
     * @return
     */
    public static float getTextMiddleBaseline(float top, float bottom, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (top + bottom - fontMetrics.bottom - fontMetrics.top) / 2;
    }

    /**
     * 在Canvas上绘制Text时，得到在某一局域中让Text水平居中的startX的值
     *
     * @param left  绘制Text区域左边界位置
     * @param right 绘制Text区域右边界位置
     * @param paint 绘制Text时使用的paint
     * @param text  当前绘制的Text的文案字符串
     * @return
     */
    public static float getTextMiddleStartX(float left, float right, Paint paint, String text) {
        float middleX = (right + left) * 0.5f;
        return getStartX(middleX, paint, text);
    }

    /**
     * 在Canvas上绘制Text时，得到在某一局域中让Text水平居中的startX的值
     *
     * @param middleX 绘制Text区域中间点X轴（水平）的坐标位置
     * @param paint   绘制Text时使用的paint
     * @param text    当前绘制的Text的文案字符串
     * @return
     */
    public static float getStartX(float middleX, Paint paint, String text) {
        return middleX - paint.measureText(text) * 0.5f;
    }

    /**
     * 获得一个指定颜色的抗锯齿画笔
     * @param color
     * @return
     */
    public static Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        return paint;
    }

}
