package study.zhouzhenwu.com.mydemo.java.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/2/26
 * 类简介：
 */

public class TypeChangeTestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B b = new B();
        C c = new C();
//        C c = (C) b;  子类不能强制转换成父类
        // 子类（或者子类的子类）都可以直接转换成父类
        A a1 = b;
        A a2 = c;


        // 8大基本类型的转换
        byte byte1 = 3;
        char char1 = 's';
        boolean boolean1 = true;

        int i1 = 3; // 数字默认的是int类型
        short short1 = (short) i1; // 高位数向地位数转换会出现精度失真，同事必须加上强制转换符号
        long long1 = i1; // 低位数可以自动转换为高位数

        double double1 = 235.876; // 浮点数默认的是double类型，不是float类型，这点一定要记住
        float float1 = (float) double1; // 高--->低，要强制转换

        i1 = byte1;
        i1 = char1;
        byte1 = (byte) i1;
        char1 = (char) i1;
//        boolean1 = i1; // int类型不能转换成boolean类型, 同事boolean类型也不能转换成int类型


        byte byte_a = 127;
        byte byte_b = 127;
//        byte_b = (byte) (byte_a + byte_b);
        byte_b += byte_a;

        switch ("wsff"){

        }

    }

    class A {

    }

    class B extends A {

    }

    class C extends B {

    }
}
