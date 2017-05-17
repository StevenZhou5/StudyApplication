package study.zhouzhenwu.com.mydemo.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/17
 * 类简介：
 */

public class GoogleZXingScanActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.bt_test)
    Button mBtTest;

    @Bind(R.id.tv_scan_result)
    TextView mTvScanResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_zxing_scan_activity);
        ButterKnife.bind(this);

        mBtTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_test:
                scanTest();
                break;
        }
    }

    private void scanTest() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("请扫描"); //底部的提示文字，设为""可以置空
        integrator.setCameraId(0); //前置或者后置摄像头
        integrator.setBeepEnabled(false); //扫描成功的「哔哔」声，默认开启
        integrator.setCaptureActivity(MyScanActivity.class);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != IntentIntegrator.REQUEST_CODE) {
            return;
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result == null) {
            return;
        }

        if (result.getContents() == null) {
            mTvScanResult.setText("扫描取消");
            log("Cancelled");
        } else {
            mTvScanResult.setText("扫描结果：" + result.getContents());
            log("Scanned: " + result.getContents());
        }
    }
}
