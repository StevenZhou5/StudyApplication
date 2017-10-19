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

import study.zhouzhenwu.com.mydemo.android.activity.CoordinatorLayoutTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.GoogleZXingScanActivity;
import study.zhouzhenwu.com.mydemo.android.activity.HandlerTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.IPCTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.LayerListTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.MultiTypeActivity;
import study.zhouzhenwu.com.mydemo.android.activity.PopupWindowTestActivity;
import study.zhouzhenwu.com.mydemo.android.activity.TestCheckViewVisible;
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
            new ActivityListItemBean("协调器布局测试", CoordinatorLayoutTestActivity.class)
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

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("ZZW", "handler开始执行任务");
                List<ActivityListItemBean> datas = initDatas();
//                mAdapter.addAllItems(8,datas);
//                mAdapter.addItem(datas.get(0));
                mAdapter.addItem(5, datas.get(0));
//                mAdapter.removeItem(datas.get(0));
                mAdapter.removeItem(5);
//                datas.remove(0);
//                datas.remove(5);
//                mAdapter.removeAllItems(datas);
            }
        };
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.d("ZZW", "开始执行task任务");
                handler.sendMessage(new Message());

            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 3000);
        /*
        TextView textView = new TextView(this);
        if (sDrawable == null){
            sDrawable = getResources().getDrawable(R.mipmap.entrust_intordcut1); // 静态drawable对象会导致严重的内存占用
        }
        textView.setBackground(sDrawable);

        setContentView(textView);*/

//        while (true){
//            String s = new String("a");
//        }
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
