package study.zhouzhenwu.com.mydemo.common.activity;

import android.content.Intent;
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


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.log(getClass().getSimpleName() + ":onNewIntent");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        log("onActivityResult:" + requestCode + ";" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
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
        LogUtils.log(getClass().getSimpleName() + ":onTrimMemory;" + "; 此时的level:" + level);
    }
}
