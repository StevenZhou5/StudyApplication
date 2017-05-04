package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.ActivityOpenFromSrcViewToTargetViewUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/3
 * 类简介：用于测试Activity打开的
 */

public class OpenActivityFromSrcViewToTargetViewActivity extends BaseActivity {
    @Bind(R.id.iv_src)
    View mSrcView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_activity_from_src_view_to_target_view);
        ButterKnife.bind(this);

        mSrcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenActivityFromSrcViewToTargetViewActivity.this, OpenActivity1.class);
                ActivityOpenFromSrcViewToTargetViewUtils.startActivity(OpenActivityFromSrcViewToTargetViewActivity.this, intent, mSrcView);
            }
        });
    }
}
