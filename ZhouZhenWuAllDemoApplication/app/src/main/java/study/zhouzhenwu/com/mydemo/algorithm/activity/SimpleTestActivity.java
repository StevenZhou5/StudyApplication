package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * Created by Steven on 2018/1/29.
 */

public class SimpleTestActivity extends BaseActivity {
    TextView mTvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_test);
        mTvTest = (TextView) findViewById(R.id.tv_test);

        mTvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findTheLongestContinuousLength();
            }
        });
    }


    /**
     * 描述
     * 输入一个乱序的连续数列，输出其中最长连续数列长度，要求算法复杂度为 O(n) 。
     * <p>
     * 输入
     * 54,55,300,12,56
     * <p>
     * 输出
     * 3
     * <p>
     * 输入样例
     * 100,4,200,1,3,2
     * 54,55,300,12
     * 1
     * 5,4,3,2,1
     * 1,2,3,4,5,6
     * <p>
     * 输出样例
     * 4
     * 2
     * 1
     * 5
     * 6
     */
    private void findTheLongestContinuousLength() {
        String line = "100,4,200,1,3,2";
        String[] nums = line.split(",");
        int[] numInt = new int[nums.length];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int value;
        for (int i = 0; i < nums.length; i++) {
            value = Integer.parseInt(nums[i]);
            numInt[i] = value;
            if (value < min) {
                min = value;
            }

            if (value > max) {
                max = value;
            }
        }

        int[] sortNumInt = new int[max - min + 1];
        for (int i = 0; i < numInt.length; i++) {
            value = numInt[i];
            sortNumInt[value - min] = value;
        }

        int result = 1;
        int currentContinueLength;
        int continueStartIndex = 0;
        int continueEndIndex = 0;
        for (int i = 0; i < sortNumInt.length - 1; i++) {
            if (sortNumInt[i + 1] - sortNumInt[i] == 1) { // 如果连续
                continueEndIndex++; // 结束指针后移
                if (i != sortNumInt.length - 2) { // 如果不是倒数第二个数，那么直接进行下一轮循环
                    continue;
                }
            }
            currentContinueLength = continueEndIndex - continueStartIndex + 1;
            if (currentContinueLength > result) {
                result = currentContinueLength;
            }
            continueStartIndex = i + 1;
            continueEndIndex = i + 1;
        }

        Log.d("ZZW", "最长连续长度：" + result);
    }


    /**
     * 两个长字符串数字的求和
     */
    private void longNumStrSum() {
        String line = "4294967294 4294967294";
        String[] nums = line.split("\\s+");
        String strResult;
        if (nums == null || nums.length == 0) {
            strResult = "0";
        } else if (nums.length == 1) {
            strResult = nums[0];
        } else {
            long intResult = Long.parseLong(nums[0]) + Long.parseLong(nums[1]);
            strResult = intResult + "";
        }
        mTvTest.setText(strResult);
    }

    /**
     * 找到只出现一次的那个数字
     */
    private void findTheOnlyAppearOnceNum() {
        String s = "10 10 11 12 12 11 16 22 22";
        s = s + " ";
        String[] nums = s.split("\\s+");

        for (String num : nums) {
            String[] current = s.split(num);
            Log.d("ZZW", num + "的分割后size:" + current.length);
            if (current.length == 2) {
                mTvTest.setText("当前值出现一次的数字是：" + num);
                return;
            }
        }
    }





    /**
     * 描述
     * 小爱后台对于每次请求服务都有一次服务质量评分告警，其数值 score 范围为 0~10, 比如：当服务出现抖动,延迟增大或者返回不合预期时，
     * score 会增大，当服务延迟较小且返回正常时，score 会减小， 现在有一批某天的小爱服务日志，格式为：id (每次请求 id ，全局唯一),
     * start_time (服务开始时间，inclusive), end_time(服务结束时间,exclusive),score(服务打分)，
     * 后台系统的某一刻t的告警分数 total_socre 为 t 时刻所有处于连接状态的服务分数之和 为了计算简便，
     * 已经将 start_time 与 end_time 转换为 unix timestamp 时间戳, 现在运维想从日志中找出一天中整个系统服务的告警分数 total_score 最大值, 要求申请的存储为常量.
     * <p>
     * 输入
     * 输入多个以空格分隔的整数，每 4 个整数为一组（组数\lt 10^7<10
     * 7
     * ），这 4 个整数分别代表 id, start_time, end_time, score，其值均小于 10^610
     * 6
     * .
     * <p>
     * 输出
     * 输出一个整数，表示 total_score 的最大值
     * <p>
     * 输入样例
     * 1 6 10 4 2 3 8 3 3 7 9 1 4 5 6 2
     * 复制样例
     */
    private void xiaoAiTotalScore() {
        String line = "1 6 10 4   2 3 8 3   3 7 9 1  4 5 6 2";
        String[] nums = line.split("\\s+");
        int[] numi = new int[nums.length * 3 / 4];
        int min = 0;
        int max = 0;
        int index = 0;
        for (int i = 1; i < nums.length - 2; i += 4) {
            int start = Integer.parseInt(nums[i]);
            int end = Integer.parseInt(nums[i + 1]);
            int value = Integer.parseInt(nums[i + 2]);
            numi[index] = start;
            numi[index + 1] = end;
            numi[index + 2] = value;
            index += 3;
            // 计算最小开始时间
            if (i == 1) {
                min = start;
            }
            if (start < min) {
                min = start;
            }

            // 计算最大结束时间
            if (end > max) {
                max = end;
            }

        }

        Log.d("ZZW", "min:" + min + "; max" + max);

        int[] numInts = new int[max - min];
        Log.d("ZZW", numInts.length + "");
        for (int i = 0; i < numi.length - 2; i += 3) {
            int start = numi[i];
            int end = numi[i + 1] - 1;
            int value = numi[i + 2];
            for (int j = start; j <= end; j++) {
                numInts[j - min] += value;
            }
        }

        int result = numInts[0];
        for (int currentNum : numInts) {
            if (currentNum > result) {
                result = currentNum;
            }
        }
        Log.d("ZZW", result + "");
    }

    private void xiaoAiTotalScore2() {
        String line = "1 6 10 4  2 3 8 3   3 7 9 1  4 5 6 2";
        String[] nums = line.split("\\s+");
        int[] map = new int[1000000];
        int start = 0;
        int end = 0;
        int value = 0;

        int minIndex = 0;
        int maxIndex = 0;
        for (int i = 1; i < nums.length; i += 4) {
            start = Integer.parseInt(nums[i]);
            end = Integer.parseInt(nums[i + 1]);
            value = Integer.parseInt(nums[i + 2]);

            if (minIndex == 0) {
                minIndex = start;
            } else if (start < minIndex) {
                minIndex = start;
            }

            if (end > maxIndex) {
                maxIndex = end;
            }

            for (int j = start; j < end; j++) {
                map[j] += value;
            }
        }

        int result = 0;
        for (int i = minIndex; i < maxIndex; i++) {
            int num = map[i];
            if (num > result) {
                result = num;
            }
        }
        map = null;
        Log.d("ZZW", result + "");
    }

    /**
     * 小爱和小冰是一对好闺蜜，她们都是世界上最聪明的人工智能之一。某一天，他们俩一起玩数数字游戏，规则如下：
     * <p>
     * 首先小爱和小冰各说一个目标数字num1num1,num2num2；
     * 小爱和小冰轮流报数（小冰报数方法与小爱相同），每次只报一个数，报数者可以选择将这个数报给谁；
     * 小爱先开始报数字，把这个数给自己或小冰都行，小爱和小冰各自得到的所有数之和不能超过自己的目标数字；
     * 最终，谁再也报不出符合条件的数字谁就算输，另一个人就赢（即谁报完数后，两人所得数字之和都达到了各自的目标数字，谁就赢）；
     * 由于两人智商都是非常的高，所以觉得游戏太简单了，于是两人决定每次报的数只能是斐波那契数列中的元素（例如每次取1，2，3，5，8.......) 。
     * 现在两人各说一个目标数字后请你判断谁会赢。如果小爱赢则输出"Xiaoai Win"，反之小冰赢输出"Xiaobing Win"。两人都很聪明，都会使用最优策略（每次报数是最优的）。
     * <p>
     * 规定：斐波那契数列F(1)=1,F(2)=2,F(N)=F(N-1)+F(N-2) F(1)=1,F(2)=2,F(N)=F(N−1)+F(N−2)
     * <p>
     * 输入
     * 两个正整数，用空格隔开，分别表示小爱和小冰的目标数字num1num1,num2num2。
     * 数据范围：num1,num2 &lt; =10000num1,num2<=10000
     * <p>
     * 输出
     * 输出 "Xiaoai Win" 或 "Xiaobing Win"，分别表示小爱赢或小冰赢。
     * <p>
     * 输入样例
     * 1 4
     * 3 4
     * 4 4
     * 1 5
     * <p>
     * 输出样例
     * Xiaoai Win
     * Xiaoai Win
     * Xiaobing Win
     * Xiaobing Win
     *
     * @return
     */
    private String xiaoAiOrXiaoBing() {
        String line = "1 4";
        String s[] = line.split(" ");
        int a[] = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            a[i] = Integer.parseInt(s[i]);
        }
        int fb[] = new int[21];
        fb[0] = 1;
        fb[1] = 1;
        for (int i = 2; i < fb.length; i++) {
            fb[i] = fb[i - 1] + fb[i - 2];
        }
        // 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584,
        // 4181, 6765, 10946
        int sg[] = new int[10001];
        for (int i = 1; i < sg.length; i++) { // i代表选择的目标数
            boolean fl[] = new boolean[sg.length];
            for (int j = 1; fb[j] <= i; j++) { // fb[j] 代表可以选择的每一个小于目标数i的斐波那契数
                int sgIndex = i - fb[j]; //
                int flIndex = sg[sgIndex];
                fl[flIndex] = true;
            }
            int j;
            for (j = 0; fl[j]; j++) ;
            sg[i] = j;

        }
        if ((sg[a[0]] ^ sg[a[1]]) != 0) {
            return "Xiaoai Win";
        }
        return "Xiaobing Win";
    }
}
