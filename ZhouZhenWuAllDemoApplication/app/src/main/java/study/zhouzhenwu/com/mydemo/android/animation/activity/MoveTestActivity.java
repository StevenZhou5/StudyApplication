package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/3/4
 * 类简介：
 */

public class MoveTestActivity extends BaseActivity {
    @Bind(R.id.bt_test)
    Button mBtTest;
    @Bind(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_test);
        ButterKnife.bind(this);
        initView();
    }

    float y;
    float top;
    float translationY;

    private void initView() {
        refreshTestData();
        mTvTest.setText("Y:" + y + "; Top:" + top + "; translationY" + translationY);

//        mBtTest.setTranslationY((float) 70.6);
        mBtTest.setY((float) 100.6);
        refreshTestData();
        mBtTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("按钮被点击");
                mTvTest.setText("Y:" + y + "; Top:" + top + "; translationY" + translationY);
            }
        });

    }

    private void refreshTestData() {
        y = mBtTest.getY();
        top = mBtTest.getTop();
        translationY = mBtTest.getTranslationY();
    }
}
