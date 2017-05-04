package study.zhouzhenwu.com.mydemo.designpattern.activity;

import android.content.Intent;

import study.zhouzhenwu.com.mydemo.android.AndroidTestActivityActivity;
import study.zhouzhenwu.com.mydemo.common.activity.ActivityListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：和设计模式学习相关的Activity
 */
public class DesignPatternActivityActivity extends ActivityListActivity {
    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("面向对象的基本原则", AndroidTestActivityActivity.class),
            new ActivityListItemBean("策略模式", AndroidTestActivityActivity.class),
            new ActivityListItemBean("观察者模式", AndroidTestActivityActivity.class),
            new ActivityListItemBean("装饰者模式", AndroidTestActivityActivity.class),
            new ActivityListItemBean("工厂模式", AndroidTestActivityActivity.class),
            new ActivityListItemBean("单例模式", AndroidTestActivityActivity.class),
            new ActivityListItemBean("命令模式", AndroidTestActivityActivity.class),
            new ActivityListItemBean("适配器模式", AndroidTestActivityActivity.class),
            new ActivityListItemBean("外观模式", AndroidTestActivityActivity.class),
    };


    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
