package study.zhouzhenwu.com.mydemo.java.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/9/13
 * 类简介：泛型相关测试
 */

public class GenericsTestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class A = new ArrayList<String>().getClass();
        Class B = new ArrayList<Integer>().getClass();

        LogUtils.log(String.valueOf(A == B)); // 输出结果为true ，这是因为java的伪泛型
    }
}
