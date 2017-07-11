package study.zhouzhenwu.com.mydemo.common.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import study.zhouzhenwu.com.mydemo.R;


public class PagerIndicator extends LinearLayout {
    public PagerIndicator(Context context) {
        super(context);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 同步子项被选中状态
     *
     * @param position
     */
    public void setSelect(int position) {
        int childCount = getChildCount();
        if (position > childCount - 1) {
            return;
        }

        for (int i = 0; i < childCount; ++i) {
            if (position == i) {
                setSelectStatus(getChildAt(i), true);
            } else {
                setSelectStatus(getChildAt(i), false);
            }
        }
    }

    private void setSelectStatus(View view, boolean selected) {
        if (view == null) {
            return;
        }
        view.setSelected(selected);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int childCount = group.getChildCount();
            for (int i = 0; i < childCount; ++i) {
                setSelectStatus(group.getChildAt(i), selected);
            }
        }
    }

    private void initIndicators(int count) {
        removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < count; ++i) {
            View view = inflater.inflate(R.layout.view_home_banner_indicator, this, false);
            addView(view);
        }
    }

    public void bindViewPager(ViewPager viewPager, final int indicatorCount) {
        initIndicators(indicatorCount);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PagerIndicator.this.setSelect(position % indicatorCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
