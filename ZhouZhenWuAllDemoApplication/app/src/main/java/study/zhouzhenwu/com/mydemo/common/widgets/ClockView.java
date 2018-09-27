package study.zhouzhenwu.com.mydemo.common.widgets;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhouzhenwu on 15/12/18.
 */
public class ClockView extends View {
    private float mStrokeWidth = 20;
    private Paint mPaint = new Paint();

    private Paint mTextPaint = new Paint();


    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint.setAntiAlias(true); // 去锯齿
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 设置圆角笔触效果
        mPaint.setStrokeWidth(mStrokeWidth); // 设置笔宽度
        mPaint.setStyle(Paint.Style.STROKE); // 只描边
        // 设置画笔遮罩滤镜  ,传入度数和样式
//        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER));


        // 初始化文本画笔
        mTextPaint.setTextSize(mStrokeWidth * 2);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD); // 字体字体样式
        mTextPaint.setTypeface(Typeface.SERIF); // 字体字体样式
    }

    private float mCurrentHourHandDegress = 0; // 当前时针的旋转角度
    private float mCurrentMinuteHandDegress = 0; // 当前分针的旋转角度
    private float mCurrentSecondHandDegress = 0; // 当前指针的旋转角度


    private float mMinute = 0;
    private float mHour = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float cx = width / 2;
        float cy = height / 2;
        float radius = Math.min(cx - mStrokeWidth * 3, cy - mStrokeWidth * 3);

        /**
         * 画表盘
         */
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx, cy, radius, mPaint);  // 画圆
        int count = 60;
        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(cx, cy - radius - mStrokeWidth / 2, cx, cy - radius - mStrokeWidth / 2 - mStrokeWidth * 2, mPaint);
                int number = 12; // 初始的第一个数字为12
                if (i != 0) {
                    number = i / 5;
                }
                canvas.drawText(String.valueOf(number), cx, cy - radius + mStrokeWidth * 3, mTextPaint);
            } else {
                canvas.drawLine(cx, cy - radius - mStrokeWidth / 2, cx, cy - radius - mStrokeWidth / 2 - mStrokeWidth, mPaint);
            }

            canvas.rotate(360 / count, cx, cy);
        }
        initPaint();

        /**
         * 通过path来绘制文字
         */
        Path path = new Path();
        RectF rectF = new RectF(0, cy - radius - 4 * mStrokeWidth, width, cy + radius + 4 * mStrokeWidth);
        path.addArc(rectF, 180, 180);// 第一个角度代表起点的角度,第二个不是代表中点角度,而是代表从起点要旋转多少度
        canvas.drawTextOnPath("2016年", path, 0, 0, mTextPaint);

        /**
         * 指针绘画
         */
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(cx, cy, mStrokeWidth, mPaint);
        if (mCurrentSecondHandDegress == 360) {
            mCurrentSecondHandDegress = 0;
            mMinute += 6;
            mCurrentMinuteHandDegress = mMinute;
        }
        if (mMinute == 360) {
            mMinute = 0;
            mHour += 6;
            mCurrentHourHandDegress = mHour;
        }

        //画秒针
        canvas.rotate(mCurrentSecondHandDegress, cx, cy);
        canvas.drawLine(cx, cy + mStrokeWidth * 3, cx, cy - radius + mStrokeWidth * 4, mPaint);
        canvas.rotate(-mCurrentSecondHandDegress, cx, cy);
        mCurrentSecondHandDegress += 6;
        // 画分针
        mPaint.setStrokeWidth((float) (mStrokeWidth * 1.2)); // 加宽分针宽度
        canvas.rotate(mCurrentMinuteHandDegress, cx, cy);
        canvas.drawLine(cx, cy + mStrokeWidth * 2, cx, cy - radius + mStrokeWidth * 8, mPaint);
        canvas.rotate(-mCurrentMinuteHandDegress, cx, cy);
        mCurrentMinuteHandDegress += 6.0f / 60f;

        // 画时针
        mPaint.setStrokeWidth((float) (mStrokeWidth * 1.4)); // 加宽时针宽度
        canvas.rotate(mCurrentHourHandDegress, cx, cy);
        canvas.drawLine(cx, (float) (cy + mStrokeWidth * 1.5), cx, cy - radius + mStrokeWidth * 12, mPaint);
        canvas.rotate(-mCurrentHourHandDegress, cx, cy);
        mCurrentHourHandDegress += 6.0f / 60f / 60f;
        initPaint();

        postInvalidateDelayed(1000);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
