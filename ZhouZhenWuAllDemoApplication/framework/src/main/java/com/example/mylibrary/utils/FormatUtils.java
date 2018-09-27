package com.example.mylibrary.utils;

import android.text.TextUtils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 格式化工具类
 * @author DZH 2015年10月29日
 * @description 格式化日期，字符及数值
 */
public class FormatUtils {

    private static final String TAG = "FormatUtils";

    /** 日期格式——"yyyy-MM-dd HH:mm:ss" */
    public static final SimpleDateFormat DATE_10 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /** 日期格式——"yyyy-MM-dd HH:mm" */
    public static final SimpleDateFormat DATE_11 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /** 日期格式——"yyyy-MM-dd" */
    public static final SimpleDateFormat DATE_12 = new SimpleDateFormat("yyyy-MM-dd");
    /** 日期格式——"yyyy-MM" */
    public static final SimpleDateFormat DATE_13 = new SimpleDateFormat("yyyy-MM");
    /** 日期格式——"yyyy" */
    public static final SimpleDateFormat DATE_14 = new SimpleDateFormat("yyyy");
    /** 日期格式——"MM-dd" */
    public static final SimpleDateFormat DATE_15 = new SimpleDateFormat("MM-dd");
    /** 日期格式——"MM" */
    public static final SimpleDateFormat DATE_16 = new SimpleDateFormat("MM");
    /** 日期格式——"dd" */
    public static final SimpleDateFormat DATE_17 = new SimpleDateFormat("dd");

    /** 日期格式——"HH:mm:ss" */
    public static final SimpleDateFormat DATE_20 = new SimpleDateFormat("HH:mm:ss");
    /** 日期格式——"HH:mm" */
    public static final SimpleDateFormat DATE_21 = new SimpleDateFormat("HH:mm");
    /** 日期格式——"HH" */
    public static final SimpleDateFormat DATE_22 = new SimpleDateFormat("HH");
    /** 日期格式——"mm:ss" */
    public static final SimpleDateFormat DATE_23 = new SimpleDateFormat("mm:ss");
    /** 日期格式——"mm" */
    public static final SimpleDateFormat DATE_24 = new SimpleDateFormat("mm");
    /** 日期格式——"ss" */
    public static final SimpleDateFormat DATE_25 = new SimpleDateFormat("ss");

    /** 日期格式——"yyyy/MM/dd HH:mm:ss" */
    public static final SimpleDateFormat DATE_30 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /** 日期格式——"yyyy/MM/dd" */
    public static final SimpleDateFormat DATE_31 = new SimpleDateFormat("yyyy/MM/dd");
    /** 日期格式——"yyyy/MM" */
    public static final SimpleDateFormat DATE_32 = new SimpleDateFormat("yyyy/MM");
    /** 日期格式——"MM/dd" */
    public static final SimpleDateFormat DATE_33 = new SimpleDateFormat("MM/dd");

    /** 日期格式——"dd/MM/yyyy HH:mm" */
    public static final SimpleDateFormat DATE_34 = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    /** 日期格式——"yyyy年MM月dd日 HH时mm分ss秒" */
    public static final SimpleDateFormat DATE_40 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    /** 日期格式——"yyyy年MM月dd日" */
    public static final SimpleDateFormat DATE_41 = new SimpleDateFormat("yyyy年MM月dd日");
    /** 日期格式——"yyyy年MM" */
    public static final SimpleDateFormat DATE_42 = new SimpleDateFormat("yyyy年MM月");
    /** 日期格式——"MM月dd日" */
    public static final SimpleDateFormat DATE_43 = new SimpleDateFormat("MM月dd日");
    /** 日期格式——"HH时mm分ss秒" */
    public static final SimpleDateFormat DATE_44 = new SimpleDateFormat("HH时mm分ss秒");
    /** 日期格式——"HH时mm分" */
    public static final SimpleDateFormat DATE_45 = new SimpleDateFormat("HH时mm分");
    /** 日期格式——"mm分ss秒" */
    public static final SimpleDateFormat DATE_46 = new SimpleDateFormat("mm分ss秒");
    /** 日期格式——HH时mm分ss秒" */
    public static final SimpleDateFormat DATE_47 = new SimpleDateFormat("ss秒");

    /** 日期格式——"yyyyMMdd_HHmmss" */
    public static final SimpleDateFormat DATE_50 = new SimpleDateFormat("yyyyMMdd_HHmmss");
    /** 日期格式——"yyyyMMdd" */
    public static final SimpleDateFormat DATE_51 = new SimpleDateFormat("yyyyMMdd");
    /** 日期格式——"HHmmss" */
    public static final SimpleDateFormat DATE_52 = new SimpleDateFormat("HHmmss");
    /** 日期格式——"yyyyMMdd_HH" */
    public static final SimpleDateFormat DATE_53 = new SimpleDateFormat("yyyyMMdd_HH");

    /**
     * 格式化日期
     * @param format 格式
     * @return
     */
    public static String formatDate(SimpleDateFormat format) {
        return formatDate(format, new Date());
    }

    /**
     * 格式化日期
     * @param format 格式
     * @param date   日期
     * @return
     */
    public static String formatDate(SimpleDateFormat format, Date date) {
        if (format != null) {
            return format.format(date);
        } else {
            return null;
        }
    }

    /**
     * 格式化日期
     * @param format 格式
     * @param date   日期
     * @return
     */
    public static String formatDate(SimpleDateFormat format, long date) {
        if (format != null) {
            return format.format(new Date(date));
        } else {
            return null;
        }
    }

    /** 时间类型——HH:mm:ss */
    public static final int TIMETYPE_HHMMSS = 0;
    /** 时间类型——HH:mm */
    public static final int TIMETYPE_HHMM = 1;
    /** 时间类型——HH */
    public static final int TIMETYPE_HH = 2;
    /** 时间类型——mm:ss */
    public static final int TIMETYPE_MMSS = 3;
    /** 时间类型——mm */
    public static final int TIMETYPE_MM = 4;
    /** 时间类型——ss */
    public static final int TIMETYPE_SS = 5;
    /** 时间类型——YY-MM-DD HH:mm:ss */
    public static final int TIMETYPE_YYMMDDHHMMSS = 6;

    /**
     * 格式化时间
     * @param time 时间
     * @return
     */
    public static String formatTime(long time) {
        return formatTime(time, TIMETYPE_HHMMSS);
    }

    /**
     * 格式化时间
     * @param time     时间
     * @param timeType 时间类型
     * @return
     */
    public static String formatTime(long time, int timeType) {
        switch (timeType) {
            case TIMETYPE_HHMM:
                return DATE_21.format(new Date(time));
            case TIMETYPE_HH:
                return DATE_22.format(new Date(time));
            case TIMETYPE_MMSS:
                return DATE_23.format(new Date(time));
            case TIMETYPE_MM:
                return DATE_24.format(new Date(time));
            case TIMETYPE_SS:
                return DATE_25.format(new Date(time));
            case TIMETYPE_YYMMDDHHMMSS:
                return DATE_11.format(new Date(time));
            default:
                return DATE_20.format(new Date(time));
        }
    }

    /** 数值格式——0（整数） */
    public static final DecimalFormat NUMERIAL_0 = new DecimalFormat("0");
    /** 数值格式——0.0（一位小数） */
    public static final DecimalFormat NUMERIAL_1 = new DecimalFormat("0.0");
    /** 数值格式——0.00（二位小数） */
    public static final DecimalFormat NUMERIAL_2 = new DecimalFormat("0.00");
    /** 数值格式——0.000（三位小数） */
    public static final DecimalFormat NUMERIAL_3 = new DecimalFormat("0.000");

    /**
     * 格式化播放时间
     * @param duration 时长（毫秒）
     * @return
     */
    public static String formatPlayTimeWithMillis(long duration) {
        return formatPlayTime(duration, 1000);
    }

    /**
     * 格式化播放时间
     * @param duration 时长
     * @return
     */
    private static String formatPlayTime(long duration, int standard) {
        if (duration <= 0) return "00:00";
        if (standard <= 0) standard = 1;
        long d = duration / standard / 60 / 60 / 24;
        long h = duration / standard / 60 / 60 % 24;
        long m = duration / standard / 60 % 60;
        long s = duration / standard % 60;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumIntegerDigits(2);
        nf.setMinimumIntegerDigits(2);
        StringBuffer sb = new StringBuffer();
        if (d > 0) {
            sb.append(d).append(" ");
        }
        if (h > 0) {
            sb.append(nf.format(h)).append(":");
        }
        sb.append(nf.format(m)).append(":");
        sb.append(nf.format(s));
        return sb.toString();
    }

    /**
     * 格式化大小
     * @param size 大小
     * @return
     */
    public static String formatSize(long size) {
        return formatSize(size, NUMERIAL_0);
    }

    /**
     * 格式化大小
     * @param value  值
     * @param format 格式化对象
     * @return
     */
    public static String formatSize(long value, DecimalFormat format) {
        StringBuffer sb = new StringBuffer();
        if (value < 0) {
            sb.append("-");
        }
        long v = Math.abs(value);
        if (v >> 10 == 0) {
            sb.append(format.format((float) v)).append("B");
        } else if (v >> 20 == 0) {
            sb.append(format.format((float) v / (1 << 10))).append("KB");
        } else if (v >> 30 == 0) {
            sb.append(format.format((float) v / (1 << 20))).append("MB");
        } else if (v >> 40 == 0) {
            sb.append(format.format((float) v / (1 << 30))).append("GB");
        } else if (v >> 50 == 0) {
            sb.append(format.format((float) v / (1 << 40))).append("TB");
        } else if (v >> 60 == 0) {
            sb.append(format.format((float) v / (1 << 50))).append("PB");
        } else if (v >> 70 == 0) {
            sb.append(format.format((float) v / (1 << 60))).append("EB");
        } else if (v >> 80 == 0) {
            sb.append(format.format((float) v / (1 << 70))).append("ZB");
        } else if (v >> 90 == 0) {
            sb.append(format.format((float) v / (1 << 80))).append("YB");
        } else if (v >> 100 == 0) {
            sb.append(format.format((float) v / (1 << 90))).append("BB");
        }
        return sb.toString();
    }

    /**
     * 格式化文本
     * @param format 文本
     * @param args   参数
     * @return
     */
    public static String formatString(String format, Object... args) {
        if (TextUtils.isEmpty(format)) return "";
        try {
            return String.format(format, args);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 解析真伪值
     * @param str 文本
     * @param def 默认值
     * @return
     */
    public static boolean parseBoolean(String str, boolean def) {
        if (TextUtils.isEmpty(str)) {
            return def;
        } else if (TextUtils.equals(str, "0")) {
            return false;
        } else if (TextUtils.equals(str, "1")) {
            return true;
        }
        try {
            return Boolean.parseBoolean(str);
        } catch (Exception e) {
        }
        return def;
    }

    /**
     * 解析Int
     * @param str 文本
     * @param def 默认值
     * @return
     */
    public static int parseInt(String str, int def) {
        if (TextUtils.isEmpty(str)) return def;
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return def;
    }

    /**
     * 解析Long
     * @param str 文本
     * @param def 默认值
     * @return
     */
    public static long parseLong(String str, long def) {
        if (TextUtils.isEmpty(str)) return def;
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
        }
        return def;
    }

    /**
     * 解析Float
     * @param str 文本
     * @param def 默认值
     * @return
     */
    public static float parseFloat(String str, float def) {
        if (TextUtils.isEmpty(str)) return def;
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
        }
        return def;
    }

    /**
     * 解析Double
     * @param str 文本
     * @param def 默认值
     * @return
     */
    public static double parseDouble(String str, double def) {
        if (TextUtils.isEmpty(str)) return def;
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
        }
        return def;
    }

    /**
     * 获取带小数的值
     * @param number       原始数值
     * @param newScale     小数位
     * @param roundingMode {@link RoundingMode}
     * @return
     */
    public static float getDecimals(float number, int newScale, RoundingMode roundingMode) {
        if (newScale < 0) return number;
        try {
            return new BigDecimal(number).setScale(newScale, roundingMode).floatValue();
        } catch (Exception e) {
        }
        return number;
    }

    /**
     * 获取带小数的值
     * @param number       原始数值
     * @param newScale     小数位
     * @param roundingMode {@link RoundingMode}
     * @return
     */
    public static double getDecimals(double number, int newScale, RoundingMode roundingMode) {
        if (newScale < 0) return number;
        try {
            return new BigDecimal(number).setScale(newScale, roundingMode).doubleValue();
        } catch (Exception e) {
        }
        return number;
    }


    //format long to HH:mm:ss
    public static String formatPlayTime(long ms) {

        if (ms == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        long hour = ms / 1000 / 3600;
        if (hour > 0) {
            if (hour < 10) {
                builder.append("0");
            }
            builder.append(hour);
            builder.append(":");
        }
        long minute = ms / 1000 % 3600 / 60;
        if (minute < 10) {
            builder.append("0");
        }
        builder.append(minute);
        builder.append(":");
        long second = ms / 1000 % 3600 % 60;
        if (second < 10) {
            builder.append("0");
        }
        builder.append(second);
        return builder.toString();
    }

    public static String getStandardDate(long timestamp) {

        StringBuffer sb = new StringBuffer();

        long time = System.currentTimeMillis() - (timestamp * 1000);
        long mill = (long) Math.ceil(time / 1000);//秒前
        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前
        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时
        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 0) {
            Calendar inputTime = Calendar.getInstance();
            inputTime.setTimeInMillis(timestamp * 1000);
            int hourOfDay = inputTime.get(Calendar.HOUR_OF_DAY);
            int minuteOfHour = inputTime.get(Calendar.MINUTE);
            int secondOfMinute = inputTime.get(Calendar.SECOND);

            long newTimestamp = timestamp + ((24 * 60 * 60) - (hourOfDay * 3600 + minuteOfHour * 60 + secondOfMinute)); // 将时间变为那天的晚上24小时
            long deltaTime = System.currentTimeMillis() - (newTimestamp * 1000);
            long deltaHour = (long) Math.ceil(deltaTime / 60 / 60 / 1000.0f);// 小时
            if (deltaHour > 48) {
                return FormatUtils.formatDate(DATE_41, timestamp * 1000);
            } else if (deltaHour > 24) {
                return "前天";
            } else {
                return "昨天";
            }
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                return "昨天";
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }


    /**
     * 将纯数字数目，变为指定标准的数字加中文的表示，例如：888888 变为88.89万，1058970变为105万
     *  Random random  = new Random();
     *  Log.d("ZZW",getStandardCount(random.nextInt(2000000)));
     * @param count
     * @return
     */
    public static String getStandardCount(long count) {
        if (count < 10000) {
            return count + "";
        }
        if (count < (1000000)) {
            double d = (double)count / (double)10000;
            BigDecimal bd=new BigDecimal(d);
            double d1=bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            return d1 + "万";
        }
        long d = count / 10000;
        return d + "万";
    }
}
