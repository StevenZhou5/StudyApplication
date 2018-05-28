package com.example.mylibrary.utils;

import java.util.Random;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/11
 * 类简介：和字符串相关的工具类方法
 */

public class StringUtils {

    /**
     * 随机小写字符串生成
     *
     * @param length
     * @return
     */
    public static String RandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(str.length());
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

}
