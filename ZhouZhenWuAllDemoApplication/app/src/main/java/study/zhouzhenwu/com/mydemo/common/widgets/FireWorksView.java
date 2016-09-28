package study.zhouzhenwu.com.mydemo.common.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import study.zhouzhenwu.com.mydemo.common.utils.DrawUtils;

/**
 * Created by zhouzhenwu on 15/12/22.
 */
public class FireWorksView extends View {
    private Paint mPaint = new Paint();
    private boolean mIsFinish = false;

    private long mLastTime; // 上一次放烟花的时间

    private List<MarkerInfo> mMarkers = new ArrayList<MarkerInfo>();

    public FireWorksView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        mLastTime = System.currentTimeMillis();
//        int i = 0;
//        while (i < 20) {
//            addMarker();
//            i++;
//        }
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(30);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            boolean isVaild = x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight();
            if (isVaild) { // 在当前View所触摸范围
                addMarker(x, y);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 在随机位置添加Marker点
     */
    private void addMarker() {
        float x = (float) (Math.random() * getWidth());
        float y = (float) (Math.random() * getHeight());
        addMarker(x, y);
    }

    /**
     * 在指定坐标添加marker
     *
     * @param cx
     * @param cy
     */
    private void addMarker(float cx, float cy) {
        MarkerInfo marker = new MarkerInfo();
        marker.mCx = cx;
        marker.mCy = cy;
        marker.mRadius = 0; // 初始半径
        marker.mMaxRadius = 200; // 当前marker的最大半径
        marker.mAlpha = 255; // 画笔透明度
        mMarkers.add(marker);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        if (System.currentTimeMillis() - mLastTime > 2000) {
//            int i = 0;
//            while (i < 20) {
//                addMarker();
//                i++;
//            }
//            mLastTime = System.currentTimeMillis();
//        }
        for (int i = 0; i < mMarkers.size(); i++) {
            MarkerInfo marker = mMarkers.get(i);
//            mPaint.setAlpha(marker.mAlpha); // 设置画笔的透明度
            if (marker.mRadius < marker.mMaxRadius) {
                DrawUtils.drawHeart(canvas, marker.mCx, marker.mCy, marker.mRadius, mPaint, false);
                marker.mRadius += 10;
                marker.mAlpha -= 10;
            } else {
                DrawUtils.drawHeart(canvas, marker.mCx, marker.mCy, marker.mMaxRadius, mPaint, false); // 画最后一笔
                mMarkers.remove(marker);
                i--;
            }
        }
        postInvalidateDelayed(50);
    }

    /**
     * 用于存储每一个烟花点的信息实体类
     */
    private class MarkerInfo {
        public float mCx; // 圆心X轴坐标
        public float mCy; // 圆心Y轴坐标
        public float mRadius; // 半径
        public float mMaxRadius; // 当前点的最大半径
        public int mAlpha; // 透明度

    }
}
