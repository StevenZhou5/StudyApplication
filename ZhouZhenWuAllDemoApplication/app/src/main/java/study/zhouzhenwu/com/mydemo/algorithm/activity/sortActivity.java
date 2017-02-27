package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.algorithm.utils.DynamicProgrammingAlgorithmUtils;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.CommonUtils;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2016/12/1
 * 类简介：排序算法
 */

public class sortActivity extends BaseActivity implements View.OnClickListener {
    private int[] datas;

    @Bind(R.id.bt_quick_sort)
    Button mBtQuickSort;
    @Bind(R.id.bt_heap_sort)
    Button mBtHeapSort;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_layout);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        mBtQuickSort.setOnClickListener(this);
        mBtHeapSort.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        initData();
        long startTime = System.currentTimeMillis();
        log("初始数据输出:" + CommonUtils.intsToString(datas));
        switch (v.getId()) {
            case R.id.bt_quick_sort:
                quickSort(datas);
                break;
            case R.id.bt_heap_sort:
                heapSortTest(datas);
                break;
        }
        log("排序后输出:" + CommonUtils.intsToString(datas));
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        LogUtils.log("排序耗时为：" + duration);
    }

    private void initData() {
        if (datas != null) {
            datas = null;
        }
        datas = new int[10000];
        int i = 0;
        while (i < datas.length) {
            datas[i] = (int) (Math.random() * 100000000);
            i++;
        }
    }


    /**
     * 用base数组中的数组成数字total，最少需要多少个base里面的数
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
    private void quickSort(int[] datas) {
        DynamicProgrammingAlgorithmUtils.quickSortSingle(datas, 0, datas.length - 1);

    }

    /* ---------------------- 堆排序算法 ------------------*/

    public void heapSortTest(int[] datas) {
        // step1: 构建大根堆；
        buildMaxHeap(datas);
        // step2：进行排序：
        heapSort(datas);
    }

    /**
     * 进行对大根堆排序
     *
     * @param maxHeapDatas 已经构建好的大根堆
     */
    private void heapSort(int[] maxHeapDatas) {
        for (int i = maxHeapDatas.length - 1; i > 0; i--) {
            // step1:每一次将最大值移动到大根堆的结束位置：
            int number = maxHeapDatas[i];
            maxHeapDatas[i] = maxHeapDatas[0];
            maxHeapDatas[0] = number;
            // step2: 重新将堆调整为大根堆
            maxHeap(maxHeapDatas, 0, i - 1);
        }
    }

    /**
     * 构建大根堆
     *
     * @param datas
     */
    private void buildMaxHeap(int[] datas) {
        // step1: 找到开始顶点（从这个顶点开始，依次递减构造大根堆）
        int startIndex = getParentIndex(datas.length - 1);
        // step2: 循环依次让索引位置从0-startIndex的节点都比左右子节点大
        for (int i = startIndex; i >= 0; i--) {
            // 比较当前节点和他的子节点中的数的大小，让当前节点的数和其中最大的一个数交换
            maxHeap(datas, i, datas.length - 1);
        }

    }

    /**
     * 交换当前节点和子节点的数，是的当前父节点的中的数最大
     *
     * @param datas       原数据组
     * @param parentIndex 父节点索引
     * @param endIndex    结束点索引
     */
    private void maxHeap(int[] datas, int parentIndex, int endIndex) {
        // step1: 找到节点的左右子节点
        int leftChildIndex = getLeftChildIndex(parentIndex);
        int rightChildIndex = getRightChildIndex(parentIndex);
        // step2: 找到最大数的节点索引，初始假设父节点的数最大
        int maxIndex = parentIndex;
        if (leftChildIndex <= endIndex && datas[leftChildIndex] > datas[maxIndex]) { // 如果子节点存在，且其值大于maxIndex位置上数，则更新maxIndex
            maxIndex = leftChildIndex;
        }
        if (rightChildIndex <= endIndex && datas[rightChildIndex] > datas[maxIndex]) { // 如果子节点存在，且其值大于maxIndex位置上数，则更新maxIndex
            maxIndex = rightChildIndex;
        }
        if (maxIndex == parentIndex) { // 如果父节点的值就是最大值，那么不做交换
            return;
        }
        // step3：如果父节点的值不是最大值，那么要交换父节点的值为最大值
        int number = datas[maxIndex];
        datas[maxIndex] = datas[parentIndex];
        datas[parentIndex] = number;
        // step$:因为做了交换，所以交换后的那个节点不在满足大根堆，要递归的去重新构造大根堆，这是堆排序中最核心的递归
        maxHeap(datas, maxIndex, endIndex);
    }

    /**
     * 根据子节点的索引位置找到父节点的索引位置
     *
     * @param childIndex : 子节点的索引位置
     * @return
     */
    private int getParentIndex(int childIndex) {
        return (childIndex - 1) >> 1; // 子节点先减去1在除以2
    }

    /**
     * 根据父节点的索引位置找到左子节点的索引位置
     *
     * @param parentIndex
     * @return
     */
    private int getLeftChildIndex(int parentIndex) {
        return ((parentIndex + 1) << 1) - 1; // 父节点先+1在乘以2然后减去1
    }

    /**
     * 根据父节点的索引位置找到右子节点的索引位置
     *
     * @param parentIndex
     * @return
     */
    private int getRightChildIndex(int parentIndex) {
        return (parentIndex + 1) << 1;
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
