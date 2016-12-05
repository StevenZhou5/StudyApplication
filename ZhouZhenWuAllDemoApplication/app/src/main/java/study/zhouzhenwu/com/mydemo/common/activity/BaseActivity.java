package study.zhouzhenwu.com.mydemo.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/2/28
 * 类简介：
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showToast(String string) {
        Toast.makeText(this.getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }

    protected void log(String text){
        LogUtils.log(text);
    }

    protected void log(int resId){
        LogUtils.log(resId);
    }
}
