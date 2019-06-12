package study.zhouzhenwu.com.mydemo.designpattern.activity;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：19/4/9
 * 类简介：命令模式测试Activity
 */
public class DesignPatternSingletonActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("ZZW", "开始调用单例");
                Singleton.getInstance();
                Singleton2.getInstance();
                Singleton3.getInstance();
                Singleton4.getInstance();
            }
        },5000);
    }


    /**
     * demo-1 懒汉式：线程不安全
     */
    public static class Singleton {
        /**
         * public class Singleton {
         * <p>
         * /**
         * 定义一个变量来存储创建好的类实例
         */

        private static Singleton uniqueInstance = null;

        /**
         * 私有化构造方法，好在内部控制创建实例的数目
         */

        private Singleton() {
            Log.d("ZZW", "Singleton实例化");
        }

        /**
         * 定义一个方法来为客户端提供类实例
         *
         * @return 一个Singleton的实例
         */

        public static synchronized Singleton getInstance() {
            //判断存储实例的变量是否有值
            if (uniqueInstance == null) {
                //如果没有，就创建一个类实例，并把值赋值给存储类实例的变量
                uniqueInstance = new Singleton();
            }
            //如果有值，那就直接使用
            return uniqueInstance;

        }

        /**
         * 示意方法，单例可以有自己的操作
         */

        public void singletonOperation() {
            //功能处理
        }

        /**
         * 示意属性，单例可以有自己的属性
         */

        private String singletonData;

        /**
         * 示意方法，让外部通过这些方法来访问属性的值
         *
         * @return 属性的值
         */

        public String getSingletonData() {
            return singletonData;
        }

    }


    /**
     * demo-2 懒汉式 （双重检查加锁）：线程安全
     */
    public static class Singleton2 {

        /**
         * 对保存实例的变量添加volatile的修饰
         */

        private volatile static Singleton2 instance = null;

        private Singleton2() {
            Log.d("ZZW", "Singleton2实例化");
        }

        public static Singleton2 getInstance() {
            //先检查实例是否存在，如果不存在才进入下面的同步块
            if (instance == null) {
                //同步块，线程安全的创建实例
                synchronized (Singleton2.class) {
                    //再次检查实例是否存在，如果不存在才真的创建实例
                    if (instance == null) {
                        instance = new Singleton2();
                    }
                }
            }
            return instance;
        }

    }


    /**
     * demo-3 饿汉式:线程安全
     */
    public static class Singleton3 {
        //4：定义一个静态变量来存储创建好的类实例
        //直接在这里创建类实例，只会创建一次
        private static Singleton3 instance = new Singleton3();

        //1：私有化构造方法，好在内部控制创建实例的数目
        private Singleton3() {
            Log.d("ZZW", "Singleton3实例化");
        }

        //2：定义一个方法来为客户端提供类实例
        //3：这个方法需要定义成类方法，也就是要加static
        //这个方法里面就不需要控制代码了
        public static Singleton3 getInstance() {
            //5：直接使用已经创建好的实例
            return instance;
        }
    }


    /**
     * demo-4 内部类：懒汉式：线程安全
     */
    public static class Singleton4 {
        /**
         * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
         * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
         */
        private static class SingletonHolder {
            /**
             * 静态初始化器，由JVM来保证线程安全
             */
            private static Singleton4 instance = new Singleton4();
        }

        /**
         * 私有化构造方法
         */
        private Singleton4() {
            Log.d("ZZW", "Singleton4实例化");
        }

        public static Singleton4 getInstance() {
            return SingletonHolder.instance;
        }
    }


}
