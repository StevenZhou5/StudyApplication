package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/2/29
 * 类简介：用于监听ScrollView的滑动
 */
public class ListenScrollScrollView extends ScrollView implements GestureDetector.OnGestureListener {
    private OnMyScrollChangedListener mScrollChangedListener;
    /**
     * 记录是否移动过,如果没有移动过（按下抬起/非手动滑动），则不认为滑动过，不触发滑动结束的监听
     */
    private boolean isMoveed = false;
    /**
     * 手指是否抬起
     */

    private boolean isActionUp = false;
    /**
     * 当前Y轴滑动位置.
     */
    private int mCurrentScrollY;
    private int mLastScrollY;

    private OnScrollFinishListener mScrollFinishListener;

    private GestureDetector mGestureDetector;
    // 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
    private boolean isFling = false;

    public ListenScrollScrollView(Context context) {
        super(context);
        mGestureDetector = new GestureDetector(context.getApplicationContext(), this);
    }

    public ListenScrollScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context.getApplicationContext(), this);
    }

    /**
     * 滑动监听：为了得到滑动的位置信息
     *
     * @param listener
     */
    public void setOnMyScrollChangedListener(OnMyScrollChangedListener listener) {
        mScrollChangedListener = listener;
    }

    /**
     * 滑动结束监听（首页猜你喜欢，滑动结束时要进行统计）
     *
     * @param listener
     */
    public void setOnScrollFinishListener(OnScrollFinishListener listener) {
        mScrollFinishListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollChangedListener != null) {
            mScrollChangedListener.onMyScrollChanged(l, t, oldl, oldt);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                isMoveed = true;
                break;
            case MotionEvent.ACTION_UP:
                isActionUp = true;
                if (!isFling) {
                    computeScroll();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (getChildCount() > 0) {
            if (getScrollY() == getChildAt(0).getMeasuredHeight() - getHeight() && isMoveed && isActionUp) {
                scrollFinished();
            }
        }
        mCurrentScrollY = super.computeVerticalScrollOffset();
        if (mCurrentScrollY == mLastScrollY && isMoveed && isActionUp) {
            scrollFinished();
        }
        mLastScrollY = mCurrentScrollY;
        super.computeScroll();
    }

    /**
     * 滑动结束的监听：一次性滑动到底部/ 慢慢滑动停止/ fling滑动停止
     */
    private void scrollFinished() {
        if (mScrollFinishListener != null) {
            mScrollFinishListener.onScrollFinished(getScrollY());
        }
        isMoveed = false;
        isActionUp = false;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        isFling = false;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        isFling = false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        isFling = false;
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        Log.d("ZZW", "onScroll:" + e1.getY() + ";" + e2.getY());
        isFling = false;
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        isFling = false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        isFling = true;
        mLastScrollY -= 20;
        return false;
    }

    public interface OnMyScrollChangedListener {
        public abstract void onMyScrollChanged(int left, int top, int oldlelf, int oldtop);
    }

    public interface OnScrollFinishListener {
        public abstract void onScrollFinished(int scrollY);
    }
}
