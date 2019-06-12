package study.zhouzhenwu.com.mydemo.designpattern;

import android.content.Intent;

import study.zhouzhenwu.com.mydemo.android.AndroidTestMainActivity;
import study.zhouzhenwu.com.mydemo.common.activity.ActivityListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;
import study.zhouzhenwu.com.mydemo.designpattern.activity.DesignPatternCommendActivity;
import study.zhouzhenwu.com.mydemo.designpattern.activity.DesignPatternSingletonActivity;

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
            new ActivityListItemBean("面向对象的基本原则", AndroidTestMainActivity.class),
            new ActivityListItemBean("策略模式", AndroidTestMainActivity.class),
            new ActivityListItemBean("观察者模式", AndroidTestMainActivity.class),
            new ActivityListItemBean("装饰者模式", AndroidTestMainActivity.class),
            new ActivityListItemBean("工厂模式", AndroidTestMainActivity.class),
            new ActivityListItemBean("单例模式", DesignPatternSingletonActivity.class),
            new ActivityListItemBean("命令模式", DesignPatternCommendActivity.class),
            new ActivityListItemBean("适配器模式", AndroidTestMainActivity.class),
            new ActivityListItemBean("外观模式", AndroidTestMainActivity.class),
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
