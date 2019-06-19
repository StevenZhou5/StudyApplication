package study.zhouzhenwu.com.mydemo.rxjava;

import study.zhouzhenwu.com.mydemo.common.activity.ActivityListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;
import study.zhouzhenwu.com.mydemo.rxjava.activity.RxJavaSimpleTestActivity;

/**
 * Created by Steven on 2018/7/5.
 */

public class RxJavaTestActivity extends ActivityListActivity {
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("RxJava的简单功能测试", RxJavaSimpleTestActivity.class),
    };

    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }
}
