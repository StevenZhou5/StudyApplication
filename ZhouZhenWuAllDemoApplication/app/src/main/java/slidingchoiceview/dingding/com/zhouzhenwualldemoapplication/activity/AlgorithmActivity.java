package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.activity;

import android.os.Bundle;
import android.util.Log;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.utils.dynamicUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：算法学习相关Activity
 */
public class AlgorithmActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMinCount();
    }

    /**
     * 用base数组中的数组成数字total，最少需要多少个base里面的书
     */
    public void getMinCount() {
        int total = (int) ((Math.random() * 100));
        int[] base = {1, 3, 5, 10, 20, 50, 100};
        String result = "total为:" + total + "; minCount:" + dynamicUtils.getMinCount(40, base);
        Log.d(TAG, result);
        showToast(result);
    }
}
