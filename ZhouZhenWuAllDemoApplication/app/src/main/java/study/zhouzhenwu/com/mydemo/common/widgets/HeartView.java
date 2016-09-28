package study.zhouzhenwu.com.mydemo.common.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import study.zhouzhenwu.com.mydemo.common.utils.DrawUtils;


/**
 * Created by zhouzhenwu on 15/12/22.
 */
public class HeartView extends View {
    private Paint mPaint = new Paint();

    private float mStrokeWidth = 30;

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID)); // 设置外部阴影

    }


    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int cx = width / 2;
        int cy = height / 2;
        canvas.translate(cx, cy); // 该表坐标系，使中心点作为坐标原点的

        DrawUtils.drawHeart(canvas, 100, 100, 100, mPaint, true);
        DrawUtils.drawHeart(canvas, -100, -100, 100, mPaint, false);
    }
}
