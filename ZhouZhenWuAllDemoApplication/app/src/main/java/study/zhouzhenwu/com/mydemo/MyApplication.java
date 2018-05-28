package study.zhouzhenwu.com.mydemo;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/4/9
 * 类简介：自定义的application
 */
public class MyApplication extends Application {
    public static MyApplication instance = null;

    @Override
    public void onCreate() {
        Log.d("ZZW", "onCreate of MyApplication is called ");
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
        LeakCanary.install(this);

    }
}
