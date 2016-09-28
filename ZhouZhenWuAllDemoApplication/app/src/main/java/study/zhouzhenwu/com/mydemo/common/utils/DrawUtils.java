package study.zhouzhenwu.com.mydemo.common.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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


    /**
     * 绘制心形
     *
     * @param canvas
     * @param px     心形中心点的x坐标
     * @param py     心形中心点的y坐标
     * @param radius 心形圆的半径
     * @param paint  画笔
     * @param isFill true:实心；false:空心
     */
    public static void drawHeart(Canvas canvas, float px, float py, float radius, Paint paint, boolean isFill) {
        Paint.Style style = paint.getStyle();
        if (isFill) {
            paint.setStyle(Paint.Style.FILL);
            drawFillHeart(canvas, px, py, radius, paint);
        } else {
            paint.setStyle(Paint.Style.STROKE);
            drawStrokeHeart(canvas, px, py, radius, paint);
        }
        paint.setStyle(style);
    }

    /**
     * 花实心心形的算法(利用canvas的旋转进行绘制)
     *
     * @param canvas
     * @param px     心形中心点的x坐标
     * @param py     心形中心点的y坐标
     * @param radius 心形圆的半径
     * @param paint  画笔
     */
    public static void drawFillHeart(Canvas canvas, float px, float py, float radius, Paint paint) {
        // 第一种绘制方法:旋转区域绘制
        paint.setColor(Color.BLUE);
        canvas.rotate(-45, px, py); // 先以中心点逆时针45度
        RectF rectF1 = new RectF(px - radius, py - radius, px + radius, py + radius); // 初始化正方形区域
        canvas.drawRect(rectF1, paint); // 画正方形
        canvas.drawCircle(px, py - radius, radius, paint); // 画圆形
        canvas.rotate(90, px, py); // 再将画布顺势正旋转90度
        canvas.drawRect(rectF1, paint); // 画正方形
        canvas.drawCircle(px, py - radius, radius, paint); // 话圆形
        canvas.rotate(-45, px, py); // 在逆时针旋转45度将画布还原为初始位置

        // 第二种绘制方法:旋转Path路径绘制
        paint.setColor(Color.RED);
        canvas.rotate(-45, px, py); // 先以中心点逆时针45度
        Path leftPath = new Path();
        leftPath.moveTo(px - radius, py + radius);
        leftPath.lineTo(px - radius, py - radius);
        RectF leftRectF = new RectF(px - radius, py - 2 * radius, px + radius, py);
        leftPath.arcTo(leftRectF, 180, 180);// 第一个角度代表起点的角度,第二个不是代表中点角度,而是代表从起点要旋转多少度
        leftPath.close();
        canvas.drawPath(leftPath, paint);
        canvas.rotate(45, px, py); // 左边绘制完成,还原画布位置,顺时针旋转45度

        canvas.rotate(45, px, py); // 瞬时针旋转45度,绘制右边区域
        Path rightPath = new Path();
        RectF rightRectF = new RectF(px - radius, py - 2 * radius, px + radius, py);
        rightPath.arcTo(rightRectF, 180, 180);
        rightPath.lineTo(px + radius, py + radius);
        canvas.drawPath(rightPath, paint);
        canvas.rotate(-45, px, py);


        float f = (float) Math.sqrt(radius * radius * 2);
    }

    /**
     * 画空心心形的算法呢
     *
     * @param canvas
     * @param px     心形中心点的x坐标
     * @param py     心形中心点的y坐标
     * @param radius 心形圆的半径
     * @param paint  画笔
     */
    public static void drawStrokeHeart(Canvas canvas, float px, float py, float radius, Paint paint) {
        float paintStroke = paint.getStrokeWidth(); // 得到笔宽度


        // 第一种绘制方法:Canvas旋转,线条加圆弧加点(用短线条来充当点)
        paint.setColor(Color.BLUE);
        canvas.rotate(-45, px, py); // 先将画布逆时针旋转45度
        RectF rectF = new RectF(px - radius + paintStroke / 2, py - 2 * radius + paintStroke / 2, px + radius - paintStroke / 2, py - paintStroke / 2); // 心形圆弧区域确定
        canvas.drawArc(rectF, 180, 180, false, paint); // 画心形左半边的圆弧
        canvas.drawLine(px - radius + paintStroke / 2, py - radius - 1, px - radius + paintStroke / 2, py + radius, paint);// 画心形左边的线条
        canvas.drawLine(px + radius - paintStroke / 2, py - radius - 1, px + radius - paintStroke / 2 + 1, py - radius + paintStroke, paint); // 画心形的中心点
        canvas.rotate(90, px, py); // 将画布顺时针旋转90度
        canvas.drawArc(rectF, 180, 180, false, paint); // 画右边的圆弧
        canvas.drawLine(px + radius - paintStroke / 2, py - radius - 1, px + radius - paintStroke / 2, py + radius, paint); // 画右边的线条
        canvas.rotate(-45, px, py);


        // 第二种绘制方法:Canvas旋转,Path路径绘制加点
        paint.setColor(Color.RED);
        canvas.rotate(-45, px, py); // 先以中心点逆时针45度
        Path leftPath = new Path();
        leftPath.moveTo(px - radius, py + radius + paintStroke / 2);
        leftPath.lineTo(px - radius, py - radius);
        RectF leftRectF = new RectF(px - radius, py - 2 * radius, px + radius, py);
        leftPath.arcTo(leftRectF, 180, 180);// 第一个角度代表起点的角度,第二个不是代表中点角度,而是代表从起点要旋转多少度
        canvas.drawPath(leftPath, paint);
        canvas.drawLine(px + radius, py - radius, px + radius, py - radius + paintStroke / 2, paint); // 画心形的中心点
        canvas.rotate(45, px, py); // 左边绘制完成,还原画布位置,顺时针旋转45度

        canvas.rotate(45, px, py); // 瞬时针旋转45度,绘制右边区域
        Path rightPath = new Path();
        RectF rightRectF = new RectF(px - radius, py - 2 * radius, px + radius, py);
        rightPath.arcTo(rightRectF, 180, 180);
        rightPath.lineTo(px + radius, py + radius + paintStroke / 2);
        canvas.drawPath(rightPath, paint);
        canvas.rotate(-45, px, py);


    }
}
