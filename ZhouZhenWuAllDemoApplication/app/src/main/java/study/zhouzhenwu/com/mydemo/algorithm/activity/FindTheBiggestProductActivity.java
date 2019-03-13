package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

public class FindTheBiggestProductActivity extends BaseActivity {
    private TextView mTvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_the_biggest_product);

        mTvTest = (TextView) findViewById(R.id.tv_test);
    }

    /**
     * 找一组数中符合要求的情况下最大乘积：
     * 有 n 个学生站成一排，每个学生有一个能力值，牛牛想从这 n 个学生中按照顺序选取 k 名学生，
     * 要求相邻两个学生的位置编号的差不超过 d，使得这 k 个学生的能力值的乘积最大，你能返回最大的乘积吗？
     * 输入描述:
     * 每个输入包含 1 个测试用例。每个测试数据的第一行包含一个整数 n (1 <= n <= 50)，表示学生的个数，
     * 接下来的一行，包含 n 个整数，按顺序表示每个学生的能力值 ai（-50 <= ai <= 50）。
     * 接下来的一行包含两个整数，k 和 d (1 <= k <= 10, 1 <= d <= 50)。
     * 输出描述:
     * 输出一行表示最大的乘积。
     */
    private void test() {
        int[] inputs = {8, 7, 2, -7, 9, 5, 4, 10, -7, 1};
        int n = inputs.length; // 10
        int k = 3;
        int d = 3;
    }

    private int max(int[] inputs, int firstMin,int endIndex, int k, int d) {
        // step1:处理k=1的特殊情况
        if (k == 1) {
            int max = inputs[firstMin];
            for (int num : inputs) {
                int bigger = num > max ? num : max;
                max = bigger;
            }
            return max;
        }

        // step2:处理k=2的特殊情况
//        if (k == 2) {
//            int max = inputs[firstMin] * inputs[firstMin + 1];
//            int start,end;
//            while (firstMin<endIndex)
//            for (int start = firstMin; start <= firstMax; start++) {
//                for (int end = start + 1; (end < endIndex) && (end <= start + d); end++) {
//                    int currentProduct = start * end;
//                    int bigger = currentProduct > max ? currentProduct : max;
//                    max = bigger;
//                }
//            }
//            return max;
//        }
//
//        // step3:处理k>2的情况
//        int includeFirst;
//        if (inputs[firstMin] > 0) {
//            int lastMax = firstMin+(k-1)*d;
//
//            includeFirst = inputs[firstMin] * max(inputs,firstMin+1,lastMax >= inputs.length ? inputs.length-1:lastMax);
//        } else {
//            includeFirst = inputs[firstMin] * min();
//        }
//
//        int exFirst = max()

        return 0;
    }

    private int min() {
        return 0;
    }
}
