package study.zhouzhenwu.com.mydemo.java;

import android.os.Bundle;
import android.view.View;

import java.util.HashSet;

import study.zhouzhenwu.com.mydemo.android.animation.AnimationMainActivityActivity;
import study.zhouzhenwu.com.mydemo.algorithm.AlgorithmTestActivityActivity;
import study.zhouzhenwu.com.mydemo.common.activity.ActivityListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;
import study.zhouzhenwu.com.mydemo.designpattern.activity.DesignPatternActivityActivity;
import study.zhouzhenwu.com.mydemo.java.activity.TypeChangeTestActivity;
import study.zhouzhenwu.com.mydemo.java.testclass.A;
import study.zhouzhenwu.com.mydemo.java.testclass.B;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：和Java相关的一些Test
 */
public class JavaTestActivityActivity extends ActivityListActivity {
    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("类型转换测试", TypeChangeTestActivity.class),
            new ActivityListItemBean("java相关测试", JavaTestActivityActivity.class),
            new ActivityListItemBean("算法测试", AlgorithmTestActivityActivity.class),
            new ActivityListItemBean("设计模式", DesignPatternActivityActivity.class),
            new ActivityListItemBean("动画", AnimationMainActivityActivity.class)
    };


    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 迭代器移除测试
       /* Log.d("ZZW", "开始");
        Iterator iterator = datas.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
            for (Object data : datas) {
                ActivityListItemBean bean = (ActivityListItemBean) data;
                Log.d("ZZW", bean.getName());
            }
        }*/
    }

    /**
     * 测试按钮被点击
     *
     * @param v
     */
    public void javaTest(View v) {
        showToast("javaTest开始");
        fatherOrSon();
//
    }

    /**
     * 父类与子类方法调用测试
     */
    private void fatherOrSon() {
        A a = new B();
        LogUtils.log(a.getString());
    }

    /**
     * 静态变量/代码块和非静态变量/代码块的创建顺序与次数测试
     */
    private void staticInitTest() {
        A a;
        a = new A();
        a = new A();
    }

    /**
     * java自增陷阱测试
     */
    private void selfPlusTest() {
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < 10; i++) {
            count1 = ++count1;
            count2 = count2++;
            LogUtils.log("count1:" + count1 + ";" + "count2:" + count2); // count为0，并没有增加
        }
    }

    /**
     * java中String类型引用测试：JVM栈内存（这里是JVN栈，不包含本地的native栈），堆内存，方法区（常量区属于方法区里面的一部分）的使用关系
     */
    private void StringReferenceTest() {
        String a = "abc";
        String b = new String("abc");
        LogUtils.log("abc的HashCode：" + "abc".hashCode() + "; a的HashCode：" + a.hashCode() + "; b的HashCode：" + b.hashCode()
                + (a == b) + (a == "abc") + ("abc" == b));
    }


    /**
     * JAVA相关引用测试
     */
    private void testRefrence() {
        String s = new String("abc");
        char[] c = {'a', 'b', 'c'};
        String s1 = "abc";
        String s2 = "abc";
        Bean b = new Bean("a");
        Bean b2 = new Bean("a");
        LogUtils.log("equls结果为：" + b.equals(b2) + "; 各自HashCode值为：b:" + b.hashCode() + "; b2:" + b2.hashCode());
        change(s, c, s1, s2, b);

        LogUtils.log("Test结果为：" + s + "; " + c[0] + ";" + s1 + ";" + s2 + "; " + b.getS());
    }

    private class Bean {
        private String s;

        public Bean(String s) {
            this.s = s;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }
    }

    private void change(String s, char[] chars, String s1, String s2, Bean b) {
        s = "gbc";
        chars[0] = 'g';
        s1 = new String("gbc");
        s2 = "gbc";
        b = new Bean("g");
    }

    /**
     * 测试用HashSet添加不重复的随机数
     */
    private void hashSetTest() {
        HashSet<Integer> set = new HashSet<>();
        while (set.size() < 99) {
            int i = (int) (Math.random() * 100);
            set.add(i);
        }
        int i = 1;
        for (int a : set) {
            LogUtils.log("第" + i + "个数为：" + a);
            i++;
        }

    }
}
