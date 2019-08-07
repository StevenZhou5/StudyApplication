package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2019-08-07
 * 类简介：超长大整数相加
 */
public class SuperLongPlusTestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bigNumubtractionS();
    }

    /**
     * 两个超长整数相减，不能用java的大整数工具类
     */
    private void bigNumubtractionS() {
        String line = "1231231237812739878951331231231237812739878951331231231237812739878951331230000000000000000000000001-331231231237812739878951331231231";
        String[] nums = line.split("-");
        char[] numChars1 = nums[0].toCharArray();
        char[] numChars2 = nums[1].toCharArray();
        int currentCarry = 0; // 当前的借位
        String result = "";
        for (int i = 0; i < numChars1.length; i++) {
            Log.d("ZZW", "i=" + i + "时的result为：" + result);
            if (i > numChars2.length) {
                result = numChars1[numChars1.length - 1 - i] + result;
                continue;
            }
            if (i == numChars2.length) {
                int subtrahend = Character.getNumericValue(numChars1[numChars1.length - 1 - i]); // 减数
                result = String.valueOf(subtrahend - currentCarry) + result;
                continue;
            }
            int subtrahend = Character.getNumericValue(numChars1[numChars1.length - 1 - i]); // 减数

            int minuend = Character.getNumericValue(numChars2[numChars2.length - 1 - i]); // 被减数

            if ((subtrahend - currentCarry - minuend) >= 0) {
                result = String.valueOf(subtrahend - currentCarry - minuend) + result;
                currentCarry = 0;
            } else {
                result = String.valueOf(subtrahend + 10 - currentCarry - minuend) + result;
                currentCarry = 1;
            }
        }
        Log.d("ZZW", "最终结果为=" + result);
    }
}
