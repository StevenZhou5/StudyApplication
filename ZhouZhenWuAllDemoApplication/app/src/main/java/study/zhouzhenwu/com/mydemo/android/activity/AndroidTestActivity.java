package study.zhouzhenwu.com.mydemo.android.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.algorithm.activity.AlgorithmTestActivity;
import study.zhouzhenwu.com.mydemo.animation.activity.AnimationMainActivity;
import study.zhouzhenwu.com.mydemo.common.activity.ListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;
import study.zhouzhenwu.com.mydemo.common.widgets.LoadingPopupWindow;
import study.zhouzhenwu.com.mydemo.designpattern.activity.DesignPatternActivity;
import study.zhouzhenwu.com.mydemo.java.activity.JavaTestActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/6/16
 * 类简介：
 */
public class AndroidTestActivity extends ListActivity {
    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("动画", AnimationMainActivity.class),
            new ActivityListItemBean("Handler相关测试", HandlerTestActivity.class),
            new ActivityListItemBean("PopupWindow跟Activity生命周期相关测试", PopupWindowTestActivity.class),
            new ActivityListItemBean("LayerListTest", LayerListTestActivity.class),
            new ActivityListItemBean("数据库操作", HandlerTestActivity.class),
            new ActivityListItemBean("广播操作", HandlerTestActivity.class),
            new ActivityListItemBean("ContentProvider测试", HandlerTestActivity.class),
            new ActivityListItemBean("handler相关测试", HandlerTestActivity.class),
    };

    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }

    private static Drawable sDrawable;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.log(getClass().getSimpleName() + ":onCreate");

        /*
        TextView textView = new TextView(this);
        if (sDrawable == null){
            sDrawable = getResources().getDrawable(R.mipmap.entrust_intordcut1); // 静态drawable对象会导致严重的内存占用
        }
        textView.setBackground(sDrawable);

        setContentView(textView);*/
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        log("onActivityResult:" + requestCode + ";" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
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
