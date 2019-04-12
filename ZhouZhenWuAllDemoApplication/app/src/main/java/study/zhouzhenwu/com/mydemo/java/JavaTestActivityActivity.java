package study.zhouzhenwu.com.mydemo.java;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashSet;

import study.zhouzhenwu.com.mydemo.common.activity.ActivityListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;
import study.zhouzhenwu.com.mydemo.java.activity.AnnotationTestActivity;
import study.zhouzhenwu.com.mydemo.java.activity.GenericsTestActivity;
import study.zhouzhenwu.com.mydemo.java.activity.TypeChangeTestActivity;
import study.zhouzhenwu.com.mydemo.java.testclass.A;
import study.zhouzhenwu.com.mydemo.java.testclass.B;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：和Java相关的一些Test
 */
public class JavaTestActivityActivity extends ActivityListActivity {
    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("类型转换测试", TypeChangeTestActivity.class),
            new ActivityListItemBean("泛型相关测试", GenericsTestActivity.class),
            new ActivityListItemBean("注解相关测试", AnnotationTestActivity.class),
    };


    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String line = "228 858 186 957 195 905 646 147 384 744 246 919 210 562 102 199 962 928 631 605 521 942 142 49 235 601 730 136 730 832 413 998 998 321 860 682 196 115 209 93 410 472 719 500 645 132 489 386 800 449 678 63 928 988 514 753 868 285 682 431 699 919 375 723 705 321 51 480 779 718 631 672 532 370 345 563 942 365 16 856 533 756 644 604 143 304 262 994 895 210 60 415 341 772 652 217 996 113 835 917 733 204 126 433 980 907 327 659 808 96 64 228 551 49 850 222 901 616 110 895 700 67 319 468 110 653 970 723 883 935 554 987 102 24 923 326 249 727 785 775 127 281 403 902 110 472 431 168 215 704 756 913 200 729 927 440 358 21 86 162 690 562 859 693 655 938 501 945 307 75 678 293 825 561 30 921 51 844 835 492 628 457 827 952 587 140 911 896 458 322 669 274 910 238 929 431 489 364 748 624 922 584 407 241 233 31 85 138 860 403 393 668 550 986 416 967 654 312 321 618 546 609 129 753 607 275 771 536 935 3 374 520 163 788 87 3 105 555 482 488 89 921 616 406 195 311 206 510 878 772 74 970 102 330 838 810 333 860 177 679 632 79 289 456 990 665 206 410 456 293 122 909 820 911 355 110 28 156 628 521 564 161 309 128 275 582 738 202 493 35 839 645 564 376 28 272 222 588 432 192 162 218 373 28 577 411 624 309 731 474 431 461 667 721 229 964 138 235 13 101 184 430 996 872 457 145 481 161 989 818 80 971 316 374 914 353 731 408 719 283 977 26 876 746 557 288 151 523 323 363 609 193 137 920 44 507 581 917 656 158 353 855 52 445 884 307 657 747 375 533 471 398 305 897 102 311 79 181 720 702 138 569 275 665 928 109 818 964 149 776 85 428 813 840 93 730 909 977 914 409 177 941 342 466 69 916 615 432 347 312 109 496 170 923 740 849 410 399 930 379 8 715 12 977 890 831 805 255 498 509 387 832 561 859 694 123 818 180 580 962 587 862 804 49 487 139 370 828 719 834 399 909 958 239 75 917 246 841 669 136 450 88 828 836 40 840 137 385 603 606 641 826 587 130 674 36 720 883 922 92 460 55 951 196 897 644 174 96 154 300 188 752 436 537 869 299 102 749 240 455 148 464 946 989 370 860 332 193 740 262 799 995 658 349 460 928 807 940 269 577 645 442 7 715 933 538 508 486 972 850 971 801 495 735 935 504 174 848 838 48 919 274 42 852 355 991 124 671 265 492 251 931 340 398 394 331 416 334 310 765 958 774 672 909 521 677 7 214 852 895 340 235 645 960 280 533 673 365 408 760 968 536 162 780 453 787 884 178 440";
        String[] strings = line.split(" ");
        int[] inputs = new int[strings.length];
        for (int i = 0; i < inputs.length; i++) {
            int num = Integer.parseInt(strings[i]);
            inputs[i] = num;
        }

        quickSortSingle(inputs, 0, inputs.length - 1);
        int max = 0;
        for (int i = 0; i < inputs.length; i++) {
            int numValue = inputs[i] * (inputs.length - i);
            if (numValue > max) {
                max = numValue;
            }
        }
//        Map<Integer, Integer> map = new HashMap();
//        int max = 0;
//        for (int i = 0; i < inputs.size(); i++) {
//            int mapKey = inputs.get(i);
//            int value = inputs.get(i);
//
//            if (map.containsKey(mapKey)) {
//                value = map.get(mapKey)+value;
//            }
//            map.put(mapKey, value);
//            if (value > max) {
//                max = value;
//            }
//        }
        Log.d("ZZW", "最大值为：" + max);

        // 迭代器移除测试
       /* Log.d("ZZW", "开始");
        Iterator iterator = datas.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
            for (Object data : datas) {
                ActivityListItemBean bean = (ActivityListItemBean) data;
                Log.d("ZZW", bean.getName());
            }
        }*/
    }

    /**
     * 单方向交替查找替换快速排序
     *
     * @param nums
     * @param leftIndex
     * @param rightIndex
     */
    private void quickSortSingle(int[] nums, int leftIndex, int rightIndex) {
        int targetNumber = nums[leftIndex];
        int startIndex = leftIndex;
        int endIndex = rightIndex;
        while (startIndex < endIndex) {
            while (startIndex < endIndex && nums[endIndex] >= targetNumber) {
                endIndex--;
            }
            if (startIndex < endIndex) {
                int a = nums[startIndex];
                nums[startIndex] = nums[endIndex];
                nums[endIndex] = a;
                startIndex++;
            }

            while (startIndex < endIndex && nums[startIndex] <= targetNumber) {
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
     * 测试按钮被点击
     *
     * @param v
     */
    public void javaTest(View v) {
        showToast("javaTest开始");
        fatherOrSon();
//
    }

    /**
     * 父类与子类方法调用测试
     */
    private void fatherOrSon() {
        A a = new B();
        LogUtils.log(a.getString());
    }

    /**
     * 静态变量/代码块和非静态变量/代码块的创建顺序与次数测试
     */
    private void staticInitTest() {
        A a;
        a = new A();
        a = new A();
    }

    /**
     * java自增陷阱测试
     */
    private void selfPlusTest() {
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < 10; i++) {
            count1 = ++count1;
            count2 = count2++;
            LogUtils.log("count1:" + count1 + ";" + "count2:" + count2); // count为0，并没有增加
        }
    }

    /**
     * java中String类型引用测试：JVM栈内存（这里是JVN栈，不包含本地的native栈），堆内存，方法区（常量区属于方法区里面的一部分）的使用关系
     */
    private void StringReferenceTest() {
        String a = "abc";
        String b = new String("abc");
        LogUtils.log("abc的HashCode：" + "abc".hashCode() + "; a的HashCode：" + a.hashCode() + "; b的HashCode：" + b.hashCode()
                + (a == b) + (a == "abc") + ("abc" == b));
    }


    /**
     * JAVA相关引用测试
     */
    private void testRefrence() {
        String s = new String("abc");
        char[] c = {'a', 'b', 'c'};
        String s1 = "abc";
        String s2 = "abc";
        Bean b = new Bean("a");
        Bean b2 = new Bean("a");
        LogUtils.log("equls结果为：" + b.equals(b2) + "; 各自HashCode值为：b:" + b.hashCode() + "; b2:" + b2.hashCode());
        change(s, c, s1, s2, b);

        LogUtils.log("Test结果为：" + s + "; " + c[0] + ";" + s1 + ";" + s2 + "; " + b.getS());
    }

    private class Bean {
        private String s;

        public Bean(String s) {
            this.s = s;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }
    }

    private void change(String s, char[] chars, String s1, String s2, Bean b) {
        s = "gbc";
        chars[0] = 'g';
        s1 = new String("gbc");
        s2 = "gbc";
        b = new Bean("g");
    }

    /**
     * 测试用HashSet添加不重复的随机数
     */
    private void hashSetTest() {
        HashSet<Integer> set = new HashSet<>();
        while (set.size() < 99) {
            int i = (int) (Math.random() * 100);
            set.add(i);
        }
        int i = 1;
        for (int a : set) {
            LogUtils.log("第" + i + "个数为：" + a);
            i++;
        }

    }
}
