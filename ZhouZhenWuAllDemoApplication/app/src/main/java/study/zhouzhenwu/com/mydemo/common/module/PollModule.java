package study.zhouzhenwu.com.mydemo.common.module;

import android.support.v4.util.Pools;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/7/22
 * 类简介：对象池使用的一个测试类
 */
public class PollModule {
    protected static Pools.SynchronizedPool<PollModule> spolls = new Pools.SynchronizedPool<PollModule>(10); // 必须是静态的

    public PollModule obtain() {
        PollModule module = spolls.acquire();
        return module == null ? new PollModule() : module;
    }

    public void recyclePoll() {
        spolls.release((PollModule) this);
    }
}
