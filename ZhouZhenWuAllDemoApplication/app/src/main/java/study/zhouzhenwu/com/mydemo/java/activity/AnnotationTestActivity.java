package study.zhouzhenwu.com.mydemo.java.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.java.testclass.TestUser;
import study.zhouzhenwu.com.mydemo.java.testclass.UserName;

/**
 * Created by Steven on 2018/5/28.
 */

public class AnnotationTestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TestUser testUser = new TestUser();
        Log.d("ZZW_TEST:", "testUserName_before: name=" + testUser.getName() + "; age=" + testUser.getAge());
        getUserInfo(testUser);
        Log.d("ZZW_TEST:", "testUserName_after:" + testUser.getName() + "; age=" + testUser.getAge());
    }

    public void getUserInfo(TestUser testUser) {
        Class<?> targetClass = testUser.getClass();
        Field[] fields = targetClass.getDeclaredFields(); // 获取成员变量
        Class[] args = new Class[1];
        args[0] = String.class;

        for (Field field : fields) {
            if (field.isAnnotationPresent(UserName.class)) {
                UserName userName = field.getAnnotation(UserName.class);

                // 通过方法反射设置age
                try {
                    Method setName = targetClass.getDeclaredMethod("setAge", args);
                    setName.setAccessible(true);
                    setName.invoke(testUser, userName.age());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 通过更改私有成员变量的访问权限，利用反射赋值
                try {
                    field.setAccessible(true);
                    field.set(testUser, userName.value());
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}


