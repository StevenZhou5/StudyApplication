package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.module;

import android.support.v4.util.Pools;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/7/22
 * 类简介：
 */
public class pollModule {
    protected static Pools.SynchronizedPool<pollModule> spolls = new Pools.SynchronizedPool<pollModule>(10); // 必须是静态的

    public pollModule obtain() {
        pollModule module = spolls.acquire();
        return module == null ? new pollModule() : module;
    }

    public void recyclePoll() {
        spolls.release((pollModule) this);
    }
}
