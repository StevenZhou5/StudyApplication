package study.zhouzhenwu.com.mydemo.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.algorithm.activity.AlgorithmActivity;
import study.zhouzhenwu.com.mydemo.android.activity.AndroidTestActivity;
import study.zhouzhenwu.com.mydemo.animation.activity.AnimationMainActivity;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.activity.ListActivity;
import study.zhouzhenwu.com.mydemo.common.adapter.ActivityListAdapter;
import study.zhouzhenwu.com.mydemo.common.adapter.ListDataBaseAdapter;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;
import study.zhouzhenwu.com.mydemo.designpattern.activity.DesignPatternActivity;
import study.zhouzhenwu.com.mydemo.java.activity.JavaTestActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：程序主入口Activity
 */
public class MainActivity extends ListActivity {

    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("Android相关测试", AndroidTestActivity.class),
            new ActivityListItemBean("java相关测试", JavaTestActivity.class),
            new ActivityListItemBean("算法测试", AlgorithmActivity.class),
            new ActivityListItemBean("设计模式", DesignPatternActivity.class),
            new ActivityListItemBean("动画", AnimationMainActivity.class)
    };


    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.log(getClass().getName() + ":onNewIntent");
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
        LogUtils.log(getClass().getSimpleName() + ":onTrimMemory");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.log(getClass().getSimpleName() + ":onActivityResult");
    }
}
