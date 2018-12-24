package study.zhouzhenwu.com.mydemo.android;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import study.zhouzhenwu.com.mydemo.android.activity.AirbnbLottieAnimationTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.CoordinatorLayoutTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.DanmakuViewTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.GoogleZXingScanActivity;
import study.zhouzhenwu.com.mydemo.android.activity.HandlerTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.IPCTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.LayerListTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.MultiTypeActivity;
import study.zhouzhenwu.com.mydemo.android.activity.NotificationShowProgressTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.PopupWindowTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.TabTextIndicatorTest;
import study.zhouzhenwu.com.mydemo.android.activity.TestCheckViewVisible;
import study.zhouzhenwu.com.mydemo.android.activity.TextureViewTestActivity;
import study.zhouzhenwu.com.mydemo.android.animation.AnimationMainActivityActivity;
import study.zhouzhenwu.com.mydemo.common.activity.ActivityListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/6/16
 * 类简介：Android测试相关的主Activity
 */
public class AndroidTestMainActivity extends ActivityListActivity {
    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("动画", AnimationMainActivityActivity.class),
            new ActivityListItemBean("Handler相关测试", HandlerTestActivity.class),
            new ActivityListItemBean("PopupWindow跟Activity生命周期相关测试", PopupWindowTestActivity.class),
            new ActivityListItemBean("LayerListTest", LayerListTestActivity.class),
            new ActivityListItemBean("获得一个View在一个可滑动viewGroup中是否可见", TestCheckViewVisible.class),
            new ActivityListItemBean("GoogleZXing扫码", GoogleZXingScanActivity.class),
            new ActivityListItemBean("MultiTypeRecycleView测试", MultiTypeActivity.class),
            new ActivityListItemBean("用semaphore实现对象池，防止内存抖动", MultiTypeActivity.class),
            new ActivityListItemBean("进程间IPC策似", IPCTestActivity.class),
            new ActivityListItemBean("协调器布局测试", CoordinatorLayoutTestActivity.class),
            new ActivityListItemBean("Text指示器设置测试", TabTextIndicatorTest.class),
            new ActivityListItemBean("通知栏展示下载进度测试", NotificationShowProgressTestActivity.class),
            new ActivityListItemBean("TextureView使用测试", TextureViewTestActivity.class),
            new ActivityListItemBean("AirbnbLottieAnimation使用测试", AirbnbLottieAnimationTestActivity.class),
            new ActivityListItemBean("B站弹幕库View测试", DanmakuViewTestActivity.class)
    };

    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.log(getClass().getSimpleName() + ":onCreate");

    }
}
