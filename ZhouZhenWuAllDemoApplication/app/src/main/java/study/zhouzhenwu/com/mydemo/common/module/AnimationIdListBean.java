package study.zhouzhenwu.com.mydemo.common.module;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/4
 * 类简介：
 */

public class AnimationIdListBean extends SimpleListItemBean {
    int animationId;

    public AnimationIdListBean(String name, int animationId) {
        super(name);
        this.animationId = animationId;
    }

    public int getAnimationId() {
        return animationId;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }
}
