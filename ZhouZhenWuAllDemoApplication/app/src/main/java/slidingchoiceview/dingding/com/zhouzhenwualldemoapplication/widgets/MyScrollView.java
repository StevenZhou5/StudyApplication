package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/2/28
 * 类简介：用于监听ScrollView的滑动
 */
public class MyScrollView extends ScrollView {
    private OnMyScrollChangedListener mListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnMyScrollChangedListener(OnMyScrollChangedListener listener) {
        mListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        Log.d("ZZW", l + ";" + t + ";" + oldl + ";" + oldt);
        if (mListener != null) {
            mListener.onMyScrollChanged(l, t, oldl, oldt);
        }
    }

    public interface OnMyScrollChangedListener {
        public abstract void onMyScrollChanged(int left, int top, int oldlelf, int oldtop);
    }
}
