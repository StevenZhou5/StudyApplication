package study.zhouzhenwu.com.mydemo.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.CommonUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/9/4
 * 类简介：进程间通信测试:不用用"："开头的进程，属于全局进程，其他应用通过shareUID方式可以和他跑在同一个进程中
 */

public class IPCTestActivity3 extends BaseActivity {
    TextView mTvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_test);
        mTvTest = (TextView) findViewById(R.id.tv_test);


        mTvTest.setText(CommonUtils.getProcessName(this));
        mTvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
