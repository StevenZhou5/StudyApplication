package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.content.Intent;

import study.zhouzhenwu.com.mydemo.common.activity.ListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：算法学习相关Activity
 */
public class AlgorithmActivity extends ListActivity {

    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("排序算法比较", sortActivity.class)
    };


    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        log(getClass().getSimpleName() + ":onNewIntent");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log(getClass().getSimpleName() + ":onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        log(getClass().getSimpleName() + ":onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log(getClass().getSimpleName() + ":onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log(getClass().getSimpleName() + ":onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log(getClass().getSimpleName() + ":onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log(getClass().getSimpleName() + ":onDestroy");
    }

}
