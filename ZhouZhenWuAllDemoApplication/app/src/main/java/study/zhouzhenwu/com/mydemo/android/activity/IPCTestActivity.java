package study.zhouzhenwu.com.mydemo.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.CommonUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/9/4
 * 类简介：进程间通信测试的默认进程Activity
 */

public class IPCTestActivity extends BaseActivity {
    @Bind(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_test);
        ButterKnife.bind(this);
        mTvTest.setText(CommonUtils.getProcessName(this));
        mTvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IPCTestActivity.this, IPCTestActivity2.class);
                startActivity(intent);
            }
        });
    }
}