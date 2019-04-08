package study.zhouzhenwu.com.mydemo.common.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：19/4/4
 * 类简介：利用scroler来实现ViewPage类似的控件
 */
public class MyScrollerLayout extends ViewGroup {
    private Scroller mScroller;

    private int mTouchSlop; // 判定位拖动操作的最小移动距离:单位Px

    public MyScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        // step1:初始化scroller
        mScroller = new Scroller(context);

        // step2：初始化mTouchSlop的值
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        // 为MyScrollerLayout的每一个子View测量大小
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    private int leftBorder; // 布局的左边界
    private int rightBorder; // 布局的右边界

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            // 为每一个子View进行横向布局
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
            // 初始化左右边界值
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(childCount - 1).getRight();
        }
    }

    private float mXDown;
    private float mXMove;
    private float mXLastMove;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d("ZZW", "onInterceptTouchEvent:eventAction:" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = event.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE: // 注意如果要触发此Action的分发，自View必须都是clickAble的，否则不会走到此分发步骤上
                mXMove = event.getRawX();
                float diff = Math.abs(mXMove - mXLastMove);
                mXLastMove = mXMove;
                // 当移动距离大于mTouchSlop，视为滑动切换操作，拦截事件，不要让子View响应
                Log.d("ZZW", "onInterceptTouchEvent:diff:" + diff + "; mTouchSlop:" + mTouchSlop);
//                if (diff > mTouchSlop) {
//                    return true;
//                }
                return true; // 默认返回true，mTouchSlop的值太大了
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("ZZW", "onTouchEvent:eventAction:" + event.getAction());
        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                return true;
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrolledX = (int) (mXLastMove - mXMove);
                Log.d("ZZW", "onTouchEvent:scrolledX:" + scrolledX);
                if (getScrollX() + scrolledX < leftBorder) { //滑动到了左边界
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + scrolledX + getWidth() > rightBorder) { // 滑动到了右边界
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // 重写computeScroll()方法，完成平滑滚动逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }

    }
}
