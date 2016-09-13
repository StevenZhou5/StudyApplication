package study.zhouzhenwu.com.mydemo.java.testclass;

import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/7/18
 * 类简介：
 */
public class B extends A {

    public B() {
        LogUtils.log("B的构造方法");
    }

    public String getString() {
        return "string of B";
    }
}
