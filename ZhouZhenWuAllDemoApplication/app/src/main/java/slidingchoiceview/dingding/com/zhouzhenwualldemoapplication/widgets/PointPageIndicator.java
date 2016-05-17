package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.utils.CommonUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/3/11
 * 类简介：用于与ViewPage或者其他滑动控件配合使用的原点指示器控件
 */
public class PointPageIndicator extends View {
    /* -------------- 公共 ------------------*/
    private final float DEFAULT_DISTANCE = 10f;
    private float mDistance = 10; // 圆点之间的距离
    private int mAllCOunt = 3; // 所有点数量

    /* -------------- 背景原点 ----------------*/
    private final int DEFAULT_BACK_COLOR = Color.RED;
    private final float DEFAULT_BACK_RADIUS = 3f;

    private Paint mPaintbackGround; // 背景圆点的画笔
    private int mBackColor = DEFAULT_BACK_COLOR; // 背景圆点颜色
    private float mBackRadius = DEFAULT_BACK_RADIUS; // 背景圆半径

    /* -------------- 当前原点 ---------------*/
    private final int DEFAULT_CURRENT_COLOR = Color.BLACK;
    private final float DEFAULT_CURRENT_RADIUS = 4f;

    private Paint mPaintCurrent; // 画当前点的画笔
    private int mCurrentColor = DEFAULT_CURRENT_COLOR; // 背景圆点颜色
    private float mCurrentRadius = DEFAULT_CURRENT_RADIUS; // 当前圆半径
    private int mCurrentPosition; // 当前点位置（从零开始计）

    public PointPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaintbackGround = new Paint();
        mPaintbackGround.setAntiAlias(true);
        mPaintbackGround.setColor(mBackColor);

        mPaintCurrent = new Paint();
        mPaintCurrent.setAntiAlias(true);
        mPaintCurrent.setColor(mCurrentColor);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PointPageIndicator);
            mDistance = CommonUtils.dip2px(context, typedArray.getFloat(R.styleable.PointPageIndicator_pointDistance, DEFAULT_DISTANCE));
            mBackColor = typedArray.getColor(R.styleable.PointPageIndicator_backPointColor, DEFAULT_BACK_COLOR);
            mBackRadius = CommonUtils.dip2px(context, typedArray.getFloat(R.styleable.PointPageIndicator_backPointRadius, DEFAULT_BACK_RADIUS));
            mCurrentColor = typedArray.getColor(R.styleable.PointPageIndicator_currentPointColor, DEFAULT_CURRENT_COLOR);
            mCurrentRadius = CommonUtils.dip2px(context, typedArray.getFloat(R.styleable.PointPageIndicator_currentPointRadius, DEFAULT_CURRENT_RADIUS));
        }
    }

    /**
     * 数量是动态设置的，当数量大于0时才会进行绘制
     *
     * @param count
     */
    private void setCount(int count) {
        mAllCOunt = count;
        mCurrentPosition = 0;
        invalidate();
    }

    /**
     * 根据当前设置的位置绘制当前位置点
     *
     * @param currentPosition
     */
    private void setCurrentPosition(int currentPosition) {
        if (mCurrentPosition >= 0 && mCurrentPosition < mAllCOunt) {
            mCurrentPosition = currentPosition;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float cxStart = width / 2 - (mAllCOunt - 1) * mDistance / 2;
        float cy = height / 2;
        if (mAllCOunt <= 0) {
            return;
        }
        // 画背景圆
        for (int i = 0; i < mAllCOunt; i++) {
            canvas.drawCircle(cxStart + i * mDistance, cy, mBackRadius, mPaintbackGround);
        }

        // 画当前圆
        canvas.drawCircle(cxStart + mCurrentPosition * mDistance, cy, mCurrentRadius, mPaintCurrent);

    }


}
