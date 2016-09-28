package study.zhouzhenwu.com.mydemo.common.widgets.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import study.zhouzhenwu.com.mydemo.common.utils.DrawUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/9/22
 * 类简介：绘制贝塞尔曲线的测试View
 */

public class BezierTestView extends View {
    public BezierTestView(Context context) {
        super(context);
    }

    public BezierTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BezierTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Paint mBackPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBackPaint == null) {
            mBackPaint = DrawUtils.getPaint(Color.RED);
            mBackPaint.setStrokeWidth(5);
            mBackPaint.setStyle(Paint.Style.STROKE);
        }
        mBackPaint.setColor(Color.RED);
        Path path = new Path();
        path.moveTo(50, getHeight() - 100);
        path.rQuadTo(10, -200, 200, 0);
        canvas.drawPath(path, mBackPaint);

        path.reset();
        mBackPaint.setColor(Color.BLUE);
        path.moveTo(50, getHeight() - 100);
        path.rQuadTo(100, -100, 100, -200);
        canvas.drawPath(path, mBackPaint);

    }
}
