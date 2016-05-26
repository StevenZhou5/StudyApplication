package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.activity;

import android.os.Bundle;
import android.util.Log;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：和Java相关的一些Test
 */
public class JavaTestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            Log.d(TAG, "count1:" + count1 + ";" + "count2:" + count2); // count为0，并没有增加
        }
    }

    /**
     * java中String类型引用测试：JVM栈内存（这里是JVN栈，不包含本地的native栈），堆内存，方法区（常量区属于方法区里面的一部分）的使用关系
     */
    private void StringReferenceTest() {
        String a = "abc";
        String b = new String("abc");
        Log.d(TAG, "abc的HashCode：" + "abc".hashCode() + "; a的HashCode：" + a.hashCode() + "; b的HashCode：" + b.hashCode()
                + (a == b) + (a == "abc") + ("abc" == b));
    }

}
