package study.zhouzhenwu.com.mydemo.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.utils.CommonUtils;

public class PercentTextView extends TextView {
    public static final int DEFAULT_BACK_COLOR = Color.BLUE;
    public static final int DEFAULT_BACK_ALPHA = 255;
    public static final int DEFAULT_PERCENT_COLOR = Color.RED;
    public static final int DEFAULT_PERCENT_ALPHA = 255;
    public static final float DEFAULT_PAINT_STOKE_WIDTH = 5f;

    private Paint mPaintBackGround = new Paint(); // 背景画笔
    private int mPaintBackColor; // 背景画笔的颜色
    private int mPaintBackAlpha; // 背景画笔的透明度

    private Paint mPaintPercent = new Paint(); // 百分比画笔
    private int mPaintPercentColor; // 背景画笔的颜色
    private int mPaintPercentAlpha; // 背景画笔的透明度

    private float mPaintStokeWidth; // 画笔宽度

    private float mLastPercent; // 最终百分比
    protected float mCurrentPercent; // 当前百分比，用于做动画

    public PercentTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        initTypeArray(context, attributeSet);
        initPaint();
    }

    /**
     * 初始化一些xml中定义的属性
     *
     * @param context
     * @param attributeSet
     */
    private void initTypeArray(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.PercentTextView);
        try {
            mPaintBackColor = typedArray.getColor(R.styleable.PercentTextView_back_paint_color, DEFAULT_BACK_COLOR);
            mPaintBackAlpha = typedArray.getInteger(R.styleable.PercentTextView_back_paint_alpha, DEFAULT_BACK_ALPHA);
            mPaintPercentColor = typedArray.getColor(R.styleable.PercentTextView_percent_paint_color, DEFAULT_PERCENT_COLOR);
            mPaintPercentAlpha = typedArray.getInteger(R.styleable.PercentTextView_percent_paint_alpha, DEFAULT_PERCENT_ALPHA);
            mPaintStokeWidth = CommonUtils.dip2px(context, typedArray.getFloat(R.styleable.PercentTextView_paint_stoke_width, DEFAULT_PAINT_STOKE_WIDTH));
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 前两个为可变值
        mPaintBackGround.setColor(mPaintBackColor); // 颜色
        mPaintBackGround.setAlpha(mPaintBackAlpha);
        // 默认属性
        mPaintBackGround.setAntiAlias(true); // 设置画笔抗锯齿
        mPaintBackGround.setStrokeCap(Paint.Cap.ROUND); // 笔触
        mPaintBackGround.setStrokeWidth(mPaintStokeWidth); // 线宽
        mPaintBackGround.setStrokeJoin(Paint.Join.ROUND); // 每笔直接的连接
        mPaintBackGround.setStyle(Paint.Style.STROKE); // 描边，还可以设置填充属性


        // 可变属性
        mPaintPercent.setColor(mPaintPercentColor); // 颜色
        mPaintPercent.setAlpha(mPaintPercentAlpha);
        // 默认属性
        mPaintPercent.setAntiAlias(true); // 设置画笔抗锯齿
        mPaintPercent.setStrokeCap(Paint.Cap.ROUND); // 笔触
        mPaintPercent.setStrokeWidth(mPaintStokeWidth); // 线宽
        mPaintPercent.setStrokeJoin(Paint.Join.ROUND); // 每笔直接的连接
        mPaintPercent.setStyle(Paint.Style.STROKE); // 描边
    }

    /**
     * 设置当前百分比
     *
     * @param percent
     */
    public void setPercent(double percent) {
        mLastPercent = (float) (360 * percent);
        mCurrentPercent = 0;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth(); // 当前View的宽度(如果确定View的宽高不会动态改变，可以不用每次onDraw的时候去拿宽高)
        int height = getHeight(); // 当前View的高度
        RectF oval = new RectF();                     //RectF对象
        oval.left = 0 + mPaintStokeWidth;                              //左边
        oval.top = 0 + mPaintStokeWidth;                                   //上边
        oval.right = width - mPaintStokeWidth;                             //右边
        oval.bottom = height - mPaintStokeWidth;                                //下边
        canvas.drawArc(oval, -90, 360, false, mPaintBackGround); // 参数1：区域oval；参数2：开始位置：o代表3点中方向，-90代表12点钟方向；参数3：从起点开始旋转多少度；参数4：话线还是花区域；参数5：画笔
        if (mLastPercent == 0) { // 如果是百分之零不画百分比圈
            return;
        }
        if (mCurrentPercent >= mLastPercent) { // 如果是绘画的最后状态，则画完后不再做动画
            canvas.drawArc(oval, -90, mLastPercent, false, mPaintPercent); // 画出最终状态
            return;
        }
        canvas.drawArc(oval, -90, mCurrentPercent, false, mPaintPercent);
        mCurrentPercent += 3; // 每次自增3度
        postInvalidateDelayed(1); // 每隔1毫秒通知绘画一次
    }

}
