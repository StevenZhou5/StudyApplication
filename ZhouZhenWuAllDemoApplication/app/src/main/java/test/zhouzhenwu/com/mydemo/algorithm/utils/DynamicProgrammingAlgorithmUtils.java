package test.zhouzhenwu.com.mydemo.algorithm.utils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/4/1
 * 类简介：动态规划算法工具类
 */
public class DynamicProgrammingAlgorithmUtils {
    private static final String TAG = "ZZW";

    /**
     * @param total : 组合后得到的总数
     * @param base  : 单个面值的数组{1,3,5,10,20,50,100}
     * @return 用base数组中的数组成总数为tatal的值最少需要几个数
     * 分析：getMinCount(i)=min{getMinCount(i-base[0]),getMinCount(i-base[1]),getMinCount(i-base[2])......,getMinCount(i-base[base.length-1])}+1;
     * getMinCount(0)=0;
     */
    public static int getMinCount(int total, int[] base) {
        // 如果total = 0，返回0
        if (total < 1) {
            return 0;
        }
        // 如果total>0,返回
        int minCount = getMinCount(total - base[0], base) + 1;
        for (int i = 1; i < base.length; i++) {
            int j = total - base[i]; // 用total减去base数组里面的一个面值的数
            if (j < 0) { // 如果当前面值大于等于总的total，则不再去循环
                break;
            }
            int count = getMinCount(j, base) + 1;
            if (minCount > count) {
                minCount = count;
            }
        }
        return minCount;
    }

    /**
     * 单方向交替查找替换快速排序
     *
     * @param nums
     * @param leftIndex
     * @param rightIndex
     */
    public static void quickSortSingle(int[] nums, int leftIndex, int rightIndex) {
        int targerNumber = nums[leftIndex];
        int startIndex = leftIndex;
        int endIndex = rightIndex;
        while (startIndex < endIndex) {
            while (startIndex < endIndex && nums[endIndex] >= targerNumber) {
                endIndex--;
            }
            if (startIndex < endIndex) {
                int a = nums[startIndex];
                nums[startIndex] = nums[endIndex];
                nums[endIndex] = a;
                startIndex++;
            }

            while (startIndex < endIndex && nums[startIndex] <= targerNumber) {
                startIndex++;
            }
            if (startIndex < endIndex) {
                int a = nums[startIndex];
                nums[startIndex] = nums[endIndex];
                nums[endIndex] = a;
                endIndex--;
            }
        }

        // 最后startIndex一定比endIndex大一
        if (leftIndex < startIndex) {
            quickSortSingle(nums, leftIndex, startIndex - 1);
        }
        if (endIndex < rightIndex) {
            quickSortSingle(nums, endIndex + 1, rightIndex);
        }
    }

    /**
     * 双向同时查找交换快速排序
     *
     * @param nums
     * @param leftIndex
     * @param rightIndex
     */
    public static void quickSortDouble(int[] nums, int leftIndex, int rightIndex) {
        int targetNumber = nums[leftIndex];
        int startIndex = leftIndex;
        int endInDex = rightIndex;
        // step1: 前后一起寻找，然后交换位置
        while (startIndex < endInDex) {
            if (nums[endInDex] == nums[startIndex] && nums[endInDex] == targetNumber) {
                startIndex++;
                endInDex--;
                continue;
            }
            if (nums[endInDex] <= targetNumber && nums[startIndex] >= targetNumber) { // 快排是一种不稳定排序，这个和目标相等的值也一定要交换
                int a = nums[startIndex];
                nums[startIndex] = nums[endInDex];
                nums[endInDex] = a;
            }
            if (nums[endInDex] > targetNumber) {
                endInDex--;
            }
            if (nums[startIndex] < targetNumber) {
                startIndex++;
            }
//            Log.d(TAG, "start:" + startIndex + "; end:" + endInDex + "; 数组为:" + CommonUtils.intsToString(nums));
        }
        // step2:根据交换后最后的索引值得差值来确定下次排序的起终点索引值
        if (endInDex - startIndex == 0) { //
            if (nums[startIndex] > targetNumber) { // 如果中间值大于所选目标值，则应该将其划分到右边，所以startIndex需要减一，反之endIndex需要加一
                startIndex--;
            } else {
                endInDex++;
            }
        } else if (endInDex - startIndex == -1) {
            startIndex--;
            endInDex++;
        }
//        Log.d(TAG, "此时的start:" + startIndex + "; end:" + endInDex + "; 数组为:" + CommonUtils.intsToString(nums));

        // step3:进行递归调用
        if (leftIndex < startIndex) { // 只有两个数的时候任然要进行一次排序
            quickSortDouble(nums, leftIndex, startIndex);
        }
        if (endInDex < rightIndex) {
            quickSortDouble(nums, endInDex, rightIndex);
        }
    }
}
