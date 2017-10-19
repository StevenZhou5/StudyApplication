package study.zhouzhenwu.com.mydemo.android.moudle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.util.Pools;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/9/3
 * 类简介：采用对象池方式的User对象
 */

public class PoolUser {
    private long mUserId;

    private String mUserName;

    private static Pools.SynchronizedPool<PoolUser> sPool = new Pools.SynchronizedPool<>(20);

    public static PoolUser obtain() {
        PoolUser instance = sPool.acquire();
        return instance == null ? new PoolUser() : instance;
    }

    public void recycle() {
        sPool.release(this);
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long userId) {
        this.mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }
}
