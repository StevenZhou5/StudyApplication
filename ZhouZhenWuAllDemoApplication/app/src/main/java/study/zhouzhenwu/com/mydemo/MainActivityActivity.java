package study.zhouzhenwu.com.mydemo;

import android.content.Intent;

import study.zhouzhenwu.com.mydemo.algorithm.AlgorithmTestActivityActivity;
import study.zhouzhenwu.com.mydemo.android.AndroidTestMainActivity;
import study.zhouzhenwu.com.mydemo.cityplan.CityPlanActivity;
import study.zhouzhenwu.com.mydemo.common.activity.ActivityListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;
import study.zhouzhenwu.com.mydemo.designpattern.activity.DesignPatternActivityActivity;
import study.zhouzhenwu.com.mydemo.java.JavaTestActivityActivity;
import study.zhouzhenwu.com.mydemo.rxjava.RxJavaTestActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：程序主入口Activity
 */
public class MainActivityActivity extends ActivityListActivity {

    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("java相关测试", JavaTestActivityActivity.class),
            new ActivityListItemBean("RxJava相关测试", RxJavaTestActivity.class),
            new ActivityListItemBean("Android相关测试", AndroidTestMainActivity.class),
            new ActivityListItemBean("算法测试", AlgorithmTestActivityActivity.class),
            new ActivityListItemBean("设计模式", DesignPatternActivityActivity.class),
            new ActivityListItemBean("2018城市计划", CityPlanActivity.class)
    };


    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.log(getClass().getSimpleName() + ":onActivityResult");
    }
}
