package test.zhouzhenwu.com.mydemo.common.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

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
     *
     * @param color
     * @return
     */
    public static Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        return paint;
    }


    /**
     * 绘制一个被圆角矩形包裹的Text
     *
     * @param text       绘制文案
     * @param color      文案颜色
     * @param cX         文案中心在画布上的X轴坐标
     * @param cY         文案中心在画布上的Y轴坐标
     * @param radius     圆角半径
     * @param halfWidth  整个矩形的宽度的一半
     * @param halfHeight 整个矩形的高度的一半
     * @param canvas     画布
     * @param paint      画笔
     */
    public static void drawRoundText(String text, int color, float cX, float cY, int radius, int halfWidth, int halfHeight, Canvas canvas, Paint paint) {
        // step1：设置当前绘制时画笔需要的属性
        Paint.Style preStyle = paint.getStyle();
        int preColor = paint.getColor();
        paint.setColor(color);

        // step2:进行绘制
        float baseline = DrawUtils.getTextMiddleBaseline(cY - radius, cY + radius, paint); // 绘制日期文案的baseLine位置
        canvas.drawText(text, cX, baseline, paint);
        paint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(cX - halfWidth, cY - halfHeight, cX + halfWidth, cY + halfHeight);
        canvas.drawRoundRect(rectF, radius, radius, paint); // 绘制圆角矩形：参数2是

        // step3:还原画笔属性
        paint.setStyle(preStyle);
        paint.setColor(preColor);
    }
}
