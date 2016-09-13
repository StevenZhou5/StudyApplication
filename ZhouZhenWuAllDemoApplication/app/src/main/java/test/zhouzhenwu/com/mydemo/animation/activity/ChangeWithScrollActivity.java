package test.zhouzhenwu.com.mydemo.animation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import test.zhouzhenwu.com.mydemo.R;
import test.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import test.zhouzhenwu.com.mydemo.common.utils.LogUtils;
import test.zhouzhenwu.com.mydemo.common.widgets.MyScrollView;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/2/28
 * 类简介：根据sroll的滑动来进行动态布局改变，实现滑动吸顶效果
 */
public class ChangeWithScrollActivity extends BaseActivity {
    private MyScrollView mScrollView;

    private View mParentView;
    private View mChildrenView;
    private View mChildrenView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_with_scroll);
        init();
    }

    private void init() {
        mScrollView = (MyScrollView) findViewById(R.id.scroll_view);

        mParentView = findViewById(R.id.rl_parent);
        mChildrenView = findViewById(R.id.tv_children);
        mChildrenView2 = findViewById(R.id.tv_children2);

        initListener();
    }

    private boolean isFirst = true;
    private int minTop = 0;
    private int maxD = 0;

    private int scaleMax = 0;

    private void initListener() {
        mScrollView.setOnMyScrollChangedListener(new MyScrollView.OnMyScrollChangedListener() {
            @Override
            public void onMyScrollChanged(int left, int top, int oldlelf, int oldtop) {
                if (isFirst) {
                    RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) mChildrenView.getLayoutParams();
                    RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) mChildrenView2.getLayoutParams();
                    maxD = params1.topMargin - params2.topMargin;
                    minTop = oldtop;
                    scaleMax = params1.leftMargin;
                    isFirst = false;
                }
                int d = top - minTop;
                if (d >= maxD) {
                    mChildrenView.setVisibility(View.GONE);
                    mChildrenView2.setVisibility(View.VISIBLE);
                    int scaleD = top - maxD;
                    int margin = scaleMax - scaleD;
                    if (margin < 0) {
                        margin = 0;
                    }
                    RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) mChildrenView2.getLayoutParams();
                    params2.leftMargin = margin;
                    params2.rightMargin = margin;
                    LogUtils.log("margin值为：" + margin);
                    mChildrenView2.setLayoutParams(params2);
                } else {
                    mChildrenView.setVisibility(View.VISIBLE);
                    mChildrenView2.setVisibility(View.GONE);
                }
//                Log.d(TAG, "TopMargin为：" + params.topMargin + "; 差值为：" + (top - oldtop));
                /*params.topMargin += Math.abs((top - oldtop));
                mChildrenView.setLayoutParams(params);
                mParentView.invalidate();*/
            }
        });
    }
}
