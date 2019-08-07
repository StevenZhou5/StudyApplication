package study.zhouzhenwu.com.mydemo.algorithm;

import android.content.Intent;

import study.zhouzhenwu.com.mydemo.algorithm.activity.AESTestActivity;
import study.zhouzhenwu.com.mydemo.algorithm.activity.HowManyWaActivity;
import study.zhouzhenwu.com.mydemo.algorithm.activity.LongestSubstringActivity;
import study.zhouzhenwu.com.mydemo.algorithm.activity.MatrixRotate90DegreeActivity;
import study.zhouzhenwu.com.mydemo.algorithm.activity.MedianOfTwoSortedArraysActivity;
import study.zhouzhenwu.com.mydemo.algorithm.activity.SimpleTestActivity;
import study.zhouzhenwu.com.mydemo.algorithm.activity.SingleLinkedListsActivity;
import study.zhouzhenwu.com.mydemo.algorithm.activity.SortActivity;
import study.zhouzhenwu.com.mydemo.algorithm.activity.SuperLongPlusTestActivity;
import study.zhouzhenwu.com.mydemo.algorithm.activity.UpstairsWithOneOrTwoStepActivity;
import study.zhouzhenwu.com.mydemo.common.activity.ActivityListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：算法学习相关Activity
 */
public class AlgorithmTestActivityActivity extends ActivityListActivity {

    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("简单算法测试", SimpleTestActivity.class),
            new ActivityListItemBean("排序算法比较", SortActivity.class),
            new ActivityListItemBean("真正的动态规划-经典阶梯问题", UpstairsWithOneOrTwoStepActivity.class),
            new ActivityListItemBean("两个单向链表相加", SingleLinkedListsActivity.class),
            new ActivityListItemBean("查找一个字符串的最长不重复子串", LongestSubstringActivity.class),
            new ActivityListItemBean("两个已排序的数组中值", MedianOfTwoSortedArraysActivity.class),
            new ActivityListItemBean("矩阵旋转90度", MatrixRotate90DegreeActivity.class),
            new ActivityListItemBean("超长大整数相加/减算法", SuperLongPlusTestActivity.class),
            new ActivityListItemBean("总共有多少种花销方式", HowManyWaActivity.class),
            new ActivityListItemBean("AES加密算法", AESTestActivity.class),
    };

    // 动态规划算法有三种


    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }

    /**
     * 在一个数组中找两个数，使的这两个数的加和等于目标数
     *
     * @param nums   输入数组
     * @param target 目标数
     * @return 返回结果
     */
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int indexOneValue = nums[i];
            int targetTwoValue = target - indexOneValue;
            for (int j = i + 1; j < nums.length; j++) {
                int indexTwoValue = nums[j];
                if (targetTwoValue == indexTwoValue) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        log(getClass().getSimpleName() + ":onNewIntent");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log(getClass().getSimpleName() + ":onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        log(getClass().getSimpleName() + ":onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log(getClass().getSimpleName() + ":onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log(getClass().getSimpleName() + ":onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log(getClass().getSimpleName() + ":onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log(getClass().getSimpleName() + ":onDestroy");
    }

}
