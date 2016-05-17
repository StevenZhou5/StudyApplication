package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication;

import android.app.Application;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/4/9
 * 类简介：
 */
public class MyApplication extends Application {
    public static MyApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
    }
}
