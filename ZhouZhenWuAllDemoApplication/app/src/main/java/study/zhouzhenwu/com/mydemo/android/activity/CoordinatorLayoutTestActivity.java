package study.zhouzhenwu.com.mydemo.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/10/13
 * 类简介：测试用协调器布局功能
 */

public class CoordinatorLayoutTestActivity extends BaseActivity {
    @Bind(R.id.fab_button)
    FloatingActionButton fabButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_test);
        ButterKnife.bind(this);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.contentView), "Snackbar", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}