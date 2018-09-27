package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/3/4
 * 类简介：
 */

public class MoveTestActivity extends BaseActivity {
    Button mBtTest;
    TextView mTvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_test);
        mBtTest = (Button) findViewById(R.id.bt_test);
        mTvTest = (TextView) findViewById(R.id.tv_test);

        initView();
    }

    float y;
    float top;
    float x;
    float left;
    float translationX;
    float translationY;

    private void initView() {
        refreshTestData();
        refreshTestData();
        mBtTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("按钮被点击");
                refreshTestData();
                mTvTest.setText("Y:" + y + "; Top:" + top + ";" + "\n" +
                        "X:" + x + "; left:" + left + ";" + "\n" +
                        "translationX:" + translationX + ";+translationY:" + translationY);
            }
        });
        mBtTest.setOnTouchListener(mTouchListener);

    }

    private float mLatestX;
    private float mLatestY;

    private View.OnTouchListener mTouchListener
            = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float eventX = event.getRawX();
            float eventY = event.getRawY();
            Log.d("ZZW", "rawX:" + event.getRawX() + "; rawY:" + event.getRawY() + ";\n" +
                    "x" + event.getX() + "; y" + event.getY());
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    float deltaX = eventX - mLatestX;
                    float deltaY = eventY - mLatestY;
                    float transX = mBtTest.getTranslationX() + deltaX;
                    float transY = mBtTest.getTranslationY() + deltaY;
                    mBtTest.setTranslationX(transX);
                    mBtTest.setTranslationY(transY);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                default:
                    break;
            }
            mLatestX = eventX;
            mLatestY = eventY;


            return false;
        }
    };

    private void refreshTestData() {
        y = mBtTest.getY();
        top = mBtTest.getTop();
        x = mBtTest.getX();
        left = mBtTest.getLeft();
        translationX = mBtTest.getTranslationX();
        translationY = mBtTest.getTranslationY();
    }
}
