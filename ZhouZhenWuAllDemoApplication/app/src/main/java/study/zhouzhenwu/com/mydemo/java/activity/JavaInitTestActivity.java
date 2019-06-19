package study.zhouzhenwu.com.mydemo.java.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * java初始化测试：子类的静态成员变量构造方法最先执行，然后是父类构造方法，然后是子类非静态成员变量的构造方法，最后是子类的构造方法;
 * 所以需要注意的时，如果在父类构造方法中去调用子类重载的父类方法，那么在子类重载此方法的执行时如果使用子类的非静态成员变量，那么此时的这个非静态成员变量还没有初始化
 */
public class JavaInitTestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Child child = new Child();
    }

    public static abstract class Parent {
        public Parent() {
            Log.d("ZZW", "父类构造方法执行");
            initData(); // 在构造函数去调用方法是非常错误的做法，因为方法执行时，子类的构造函数和非静态成员变量都未初始化
        }

        // 父类定义了一个抽象方法，此方法在父类的构造函数中被调用
        public abstract void initData();
    }

    public static class Child extends Parent {
        private TestUser1 testUser = new TestUser1();
        private static TestUser2 testUser2 = new TestUser2();

        public Child() {
            super();
            Log.d("ZZW", "子类构造方法执行");
        }

        @Override
        public void initData() {
            Log.d("ZZW", "子类重载的initData方法执行：此时子类的静态成员变量是否为空：" + (testUser2 == null)
                    + ";  此时子类的非静态成员变量是否为空：" + (testUser == null));
        }
    }

    public static class TestUser1 {

        public TestUser1() {
            Log.d("ZZW", "TestUser1类构造方法执行");
        }
    }

    public static class TestUser2 {

        public TestUser2() {
            Log.d("ZZW", "TestUser2类构造方法执行");
        }
    }

}
