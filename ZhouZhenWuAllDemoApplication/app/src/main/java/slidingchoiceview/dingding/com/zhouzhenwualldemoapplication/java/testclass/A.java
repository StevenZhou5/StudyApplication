package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.java.testclass;


import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/7/18
 * 类简介：
 */
public class A {

//    private B b = new B();
//    private static C c = new C();

    public A() {
        LogUtils.log("A的构造方法");
    }

    static {
        LogUtils.log("A的静态代码块");
    }

    public String getString() {
        return "string of A";
    }

}
