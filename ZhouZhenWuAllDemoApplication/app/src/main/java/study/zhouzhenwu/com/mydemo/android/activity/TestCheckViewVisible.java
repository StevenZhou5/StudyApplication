package study.zhouzhenwu.com.mydemo.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.widgets.MyScrollView;
import com.example.mylibrary.utils.ViewUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/4/6
 * 类简介：测试一个View在可以滑动的viewGroup中是否可见
 */

public class TestCheckViewVisible extends BaseActivity {
    @Bind(R.id.tv_test)
    TextView mTvTest;
    @Bind(R.id.scroll_view_test)
    MyScrollView mScrollViewTest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_view_visible);
        ButterKnife.bind(this);

        mScrollViewTest.setOnMyScrollChangedListener(new MyScrollView.OnMyScrollChangedListener() {
            @Override
            public void onMyScrollChanged(int left, int top, int oldlelf, int oldtop) {
//                log("MyScrollView滑动监听：" + "left：" + left + "；top：" + top + "； oldlelf：" + oldlelf
//                        + "；oldtop：" + oldtop);
                boolean isVisible = ViewUtils.checkViewIsVisible(mTvTest, mScrollViewTest, 0.5, 0.5);
                log("当前测试的的TextView是否可见：" + isVisible);
            }
        });
    }

}
