package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.algorithm.utils.DynamicProgrammingAlgorithmUtils;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2016/12/1
 * 类简介：排序算法
 */

public class sortActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_layout);
    }


    /**
     * 用base数组中的数组成数字total，最少需要多少个base里面的书
     */
    private void getMinCount() {
        int total = (int) ((Math.random() * 100));
        int[] base = {1, 3, 5, 10, 20, 50, 100};
        String result = "total为:" + total + "; minCount:" + DynamicProgrammingAlgorithmUtils.getMinCount(40, base);
        LogUtils.log(result);
        showToast(result);
    }

    /**
     * 快速排序
     */
    private void quickSort() {
        int[] ints = new int[1000000];
        int i = 0;
        while (i < ints.length) {
            ints[i] = (int) (Math.random() * 1000000);
            i++;
        }
        long startTime = System.currentTimeMillis();
//        Log.d(TAG, "输入:" + CommonUtils.intsToString(ints));
        DynamicProgrammingAlgorithmUtils.quickSortSingle(ints, 0, ints.length - 1);
//        Log.d(TAG, "输出:" + CommonUtils.intsToString(ints));
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        LogUtils.log("排序耗时为：" + duration);
    }


    /**
     * 古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？ 
     *    //这是一个菲波拉契数列问题
     *
     * @param month
     * @return
     */
    private int getRabbitCount(int month) {
        int x = 1; // >=3个月的
        int y = 0; // 第二个月的
        int z = 0; // 第一个月的，刚出生的
        int count = x + y + z;
        if (month < 3) {
            return count;
        }
        for (int i = 3; i < month; i++) {
            int x1, y1, z1;
            x1 = x + y; // 本月大于等于三个月的数量是上个月的x和y相加的值
            y1 = z; // 本月第二个月大的等于上个月的z
            z1 = x + y; // 本月刚出生的等于上个月的x
            x = x1;
            y = y1;
            z = z1;
            int countI = x + y + z;
            LogUtils.log("第" + i + "个月的兔子总量为：" + countI);
        }
        count = x + y + z;
        return count;
    }

    private int getRabbitCount2(int month) {
        int x = 1; // >=3个月的
        int y = 1; // 第二个月的
        int z = 0; // 第一个月的，刚出生的
        int count = x + y + z;
        if (month < 3) {
            return count;
        }
        for (int i = 3; i < month; i++) {
            int x1, y1, z1;
            z = y; // 记录上个月的y值
            y = x + y; // 等于上个月的x+y
            x = z; // 等于上个月的y
            int countI = x + y + z;
            LogUtils.log("第" + i + "个月的兔子总量为：" + y);
        }
        count = x + y + z;
        return count;
    }
}
