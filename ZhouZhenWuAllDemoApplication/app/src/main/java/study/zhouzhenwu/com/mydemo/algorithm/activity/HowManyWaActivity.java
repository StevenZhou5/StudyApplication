package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2019-08-07
 * 类简介：有多少种花销方式
 */
public class HowManyWaActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        totalMoney = 100;
        priceList = new int[]{2, 1, 4};
        likeList = new int[]{3, 1, 2};
        n = priceList.length;
        result = 0;
        costMoney(totalMoney, 0, totalMoney / priceList[likeList[0] - 1]);
        Log.d("ZZW", "总共有几种花销方式：" + result + "");
    }

    int totalMoney; // 总花销
    int[] priceList; // 商品价格列表
    int[] likeList; // 商品喜好度总高到底的对应的商品索引列表
    int n;
    int result = 0;// 最后结果

    /**
     * @param availableMoney 可用的钱
     * @param index          商品索引
     * @param maxCount       此商品最大可以购买数量
     */
    private void costMoney(int availableMoney, int index, int maxCount) {
        if (availableMoney <= 0 || index >= n || (maxCount < 1 && availableMoney != totalMoney)) {
            return;
        }

        for (int i = 0; i <= maxCount; i++) {
            int lastMoney = availableMoney - priceList[likeList[index] - 1] * i; // 剩余的钱= 可用的钱-买第i喜欢的商品的价钱*个数
            if (lastMoney == 0) { // 钱花完了，那么后续商品都买不了了，此次循环结束
                Log.d("ZZW", "第" + (index+1) + "喜欢的商品买了" + i + "件");
                result++;
                break;
            }
            if (index == n - 1) { // 如果买的是最后一件商品，而且前没有画完，那么就只能继续买这种商品，而不可能把钱拿去卖其他商品了
                continue;
            }

            int newMaxCount;
            if (lastMoney == totalMoney) { // 如果钱一分都没花在买前面的物品上，那重新调整下一件商品可买的最大数量
                newMaxCount = lastMoney / priceList[likeList[index + 1] - 1];
            } else {
//                newMaxCount = i; // 不管买了多少件商品，后面的都不能比前面的多，如果可以一样多
                if (i <= 0) {
                    break;
                }
                newMaxCount = i - 1; // 不管买了多少件商品，后面的都不能比前面的多，如果可以不能一样多
            }

            costMoney(lastMoney, index + 1, newMaxCount);
        }
    }

}
