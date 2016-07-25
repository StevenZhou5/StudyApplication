package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;

import butterknife.Bind;
import butterknife.ButterKnife;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.activity.BaseActivity;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/6/16
 * 类简介：
 */
public class AndroidTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_test);
        LogUtils.log(getClass().getSimpleName() + ":onCreate");
    }

    /**
     * 测试按钮被点击
     *
     * @param view
     */
    public void androidTest(View view) {
        showToast("AndroidTest开始");
        popupWindowTest(view);
    }

    @Bind(R.id.layout_pop_container)
    View mPopContainer;

    @Bind(R.id.view_background)
    View mViewBackground;

    private void popupWindowTest(View view) {
        // 自定义的Pop
//        OneImageViewPopupWindow oneImageViewPopupWindow = new OneImageViewPopupWindow(this,R.drawable.leak_canary_icon);
//        oneImageViewPopupWindow.showAsDropDown(view);

        // 带有动画效果的popupWindow
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popupwindow_layout, null);
        ButterKnife.bind(this, contentView);
        PopupWindow popup = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popup.setFocusable(true);
        popup.setAnimationStyle(R.style.AppTheme_PopupOverlay);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(view);

        mPopContainer.setVisibility(View.VISIBLE);
        mViewBackground.setVisibility(View.VISIBLE);

        mPopContainer.measure(0, 0);
        LogUtils.log("height:" + mPopContainer.getMeasuredHeight());
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -mPopContainer.getMeasuredHeight(), 0);
        translateAnimation.setDuration(2000);
        mPopContainer.startAnimation(translateAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.1f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        mViewBackground.startAnimation(alphaAnimation);
    }

    /**
     * 线程与handle回调测试
     */
    private void handlerThreadTest() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    this.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                                showToast("线程休眠结束");
                                break;
                            default:
                                break;
                        }
                        super.handleMessage(msg);
                    }
                };
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        thread.start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.log(getClass().getSimpleName() + ":onNewIntent");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.log(getClass().getSimpleName() + ":onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.log(getClass().getSimpleName() + ":onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.log(getClass().getSimpleName() + ":onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.log(getClass().getSimpleName() + ":onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.log(getClass().getSimpleName() + ":onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.log(getClass().getSimpleName() + ":onDestroy");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtils.log(getClass().getSimpleName() + ":onTrimMemory;" + "; 此时的level:" + level);
    }
}
