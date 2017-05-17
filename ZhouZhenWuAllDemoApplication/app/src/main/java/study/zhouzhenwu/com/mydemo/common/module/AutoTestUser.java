package study.zhouzhenwu.com.mydemo.common.module;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.Date;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/16
 * 类简介：
 */
@AutoValue
public abstract class AutoTestUser implements Parcelable {
    abstract String name();

    abstract String addr();

    abstract int age();

    abstract String gender();

    abstract String hobby();

    abstract String sign();

    abstract Date date();

    //创建AutoTestUser，内部调用的是AutoValue_AutoTestUser
    public static AutoTestUser create(String name, String addr, int age, String gender, String hobby, String sign,Date date) {
        return new AutoValue_AutoTestUser(name, addr, age, gender, hobby, sign,date);
    }
}
