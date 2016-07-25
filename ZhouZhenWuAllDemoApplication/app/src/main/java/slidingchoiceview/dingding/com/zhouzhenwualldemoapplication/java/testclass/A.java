package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.java.testclass;

import android.util.Log;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/7/18
 * 类简介：
 */
public class A {

//    private B b = new B();
//    private static C c = new C();

    public A() {
        Log.d("ZZW", "A的构造方法");
    }

    static {
        Log.d("ZZW", "A的静态代码块");
    }

    public String getString() {
        return "string of A";
    }

}
