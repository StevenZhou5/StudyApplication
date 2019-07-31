package study.zhouzhenwu.com.mydemo.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.utils.CommonUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/3/11
 * 类简介：用于与ViewPage或者其他滑动控件配合使用的原点指示器控件
 */
public class PointPageIndicator extends View {
    /* -------------- 公共 ------------------*/
    private final int DEFAULT_DISTANCE = 10;
    private float mDistance = 10; // 圆点之间的距离
    private int mAllCount = 3; // 所有点数量

    /* -------------- 背景原点 ----------------*/
    private final int DEFAULT_BACK_COLOR = Color.RED;
    private final int DEFAULT_BACK_RADIUS = 3;

    private Paint mPaintBackGround; // 背景圆点的画笔
    private int mBackColor = DEFAULT_BACK_COLOR; // 背景圆点颜色
    private float mBackRadius = DEFAULT_BACK_RADIUS; // 背景圆半径

    /* -------------- 当前原点 ---------------*/
    private final int DEFAULT_CURRENT_COLOR = Color.BLACK;
    private final int DEFAULT_CURRENT_RADIUS = 4;

    private Paint mPaintCurrent; // 画当前点的画笔
    private int mCurrentColor = DEFAULT_CURRENT_COLOR; // 背景圆点颜色
    private float mCurrentRadius = DEFAULT_CURRENT_RADIUS; // 当前圆半径
    private int mCurrentPosition; // 当前点位置（从零开始计）

    public PointPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PointPageIndicator);
            mDistance = typedArray.getDimensionPixelOffset(R.styleable.PointPageIndicator_pointDistance, DEFAULT_DISTANCE);
            mBackColor = typedArray.getColor(R.styleable.PointPageIndicator_backPointColor, DEFAULT_BACK_COLOR);
            mBackRadius = typedArray.getDimensionPixelOffset(R.styleable.PointPageIndicator_backPointRadius, DEFAULT_BACK_RADIUS);
            mCurrentColor = typedArray.getColor(R.styleable.PointPageIndicator_currentPointColor, DEFAULT_CURRENT_COLOR);
            mCurrentRadius = typedArray.getDimensionPixelOffset(R.styleable.PointPageIndicator_currentPointRadius, DEFAULT_CURRENT_RADIUS);
        }
        mPaintBackGround = new Paint();
        mPaintBackGround.setAntiAlias(true);
        mPaintBackGround.setColor(mBackColor);

        mPaintCurrent = new Paint();
        mPaintCurrent.setAntiAlias(true);
        mPaintCurrent.setColor(mCurrentColor);


    }

    /**
     * 数量是动态设置的，当数量大于0时才会进行绘制
     *
     * @param count
     */
    public void setCount(int count) {
        mAllCount = count;
        mCurrentPosition = 0;
        invalidate();
    }

    /**
     * 根据当前设置的位置绘制当前位置点
     *
     * @param currentPosition
     */
    public void setCurrentPosition(int currentPosition) {
        if (mCurrentPosition >= 0 && mCurrentPosition < mAllCount) {
            mCurrentPosition = currentPosition;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float cxStart = width / 2 - (mAllCount - 1) * mDistance / 2;
        float cy = height / 2;
        if (mAllCount <= 0) {
            return;
        }
        // 画背景圆
        for (int i = 0; i < mAllCount; i++) {
            canvas.drawCircle(cxStart + i * mDistance, cy, mBackRadius, mPaintBackGround);
        }

        // 画当前圆
        canvas.drawCircle(cxStart + mCurrentPosition * mDistance, cy, mCurrentRadius, mPaintCurrent);

    }


}
