package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.utils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/4/1
 * 类简介：动态规划算法工具类
 */
public class dynamicUtils {
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
}
