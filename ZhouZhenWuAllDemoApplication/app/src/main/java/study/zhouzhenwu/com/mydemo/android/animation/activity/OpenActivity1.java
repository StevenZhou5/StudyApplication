package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.ActivityOpenFromSrcViewToTargetViewUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/3
 * 类简介：
 */

public class OpenActivity1 extends BaseActivity {

    View mTargetView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open1);
        mTargetView = findViewById(R.id.iv_target);

        mTargetView.post(new Runnable() {
            @Override
            public void run() {
                ActivityOpenFromSrcViewToTargetViewUtils.prepareAnimation(OpenActivity1.this, mTargetView);
                ActivityOpenFromSrcViewToTargetViewUtils.animate(OpenActivity1.this);
            }
        });
//        mTargetView.post(new Runnable() {
//            @Override
//            public void run() {
//                ActivityOpenFromSrcViewToTargetViewUtils.prepareAnimation(OpenActivity1.this, mTargetView);
//                ActivityOpenFromSrcViewToTargetViewUtils.animate(OpenActivity1.this);
//            }
//        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
}
