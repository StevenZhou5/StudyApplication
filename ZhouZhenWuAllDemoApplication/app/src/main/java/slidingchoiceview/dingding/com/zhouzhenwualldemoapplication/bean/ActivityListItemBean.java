package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.bean;

import android.app.Activity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/4/11
 * 类简介：用于Activity跳转的列表实体Bean
 */
public class ActivityListItemBean {
    private String name;//
    private Class<? extends Activity> activity;

    public ActivityListItemBean(String name, Class<? extends Activity> activity) {
        this.name = name;
        this.activity = activity;
    }

    public Class<? extends Activity> getActivity() {
        return activity;
    }

    public void setActivity(Class<? extends Activity> activity) {
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
