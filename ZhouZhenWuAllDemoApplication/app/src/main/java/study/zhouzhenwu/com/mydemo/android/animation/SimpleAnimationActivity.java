package study.zhouzhenwu.com.mydemo.android.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.SimpleListActivity;
import study.zhouzhenwu.com.mydemo.common.module.AnimationIdListBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/4
 * 类简介： 今天简单动画展测试的Activity
 */

public class SimpleAnimationActivity extends SimpleListActivity<AnimationIdListBean> {

    private AnimationIdListBean[] mAnimationIdListBean = {
            new AnimationIdListBean("a1", R.anim.a1),
            new AnimationIdListBean("a2", R.anim.a2),
            new AnimationIdListBean("alpha", R.anim.alpha),
            new AnimationIdListBean("alpha_rotate", R.anim.alpha_rotate),
            new AnimationIdListBean("alpha_scale", R.anim.alpha_scale),
            new AnimationIdListBean("alpha_scale_rotate", R.anim.alpha_scale_rotate),
            new AnimationIdListBean("alpha_scale_translate", R.anim.alpha_scale_translate),
            new AnimationIdListBean("alpha_scale_translate_rotate", R.anim.alpha_scale_translate_rotate),
            new AnimationIdListBean("alpha_translate", R.anim.alpha_translate),
            new AnimationIdListBean("alpha_translate_rotate", R.anim.alpha_translate_rotate),
            new AnimationIdListBean("drawroll_ani_in", R.anim.drawroll_ani_in),
            new AnimationIdListBean("drawroll_ani_out", R.anim.drawroll_ani_out),
            new AnimationIdListBean("fade", R.anim.fade),
            new AnimationIdListBean("gallery_in", R.anim.gallery_in),
            new AnimationIdListBean("hold", R.anim.hold),
            new AnimationIdListBean("hyperspace_in", R.anim.hyperspace_in),
            new AnimationIdListBean("hyperspace_out", R.anim.hyperspace_out),
            new AnimationIdListBean("left_in", R.anim.left_in),
            new AnimationIdListBean("left_out", R.anim.left_out),
            new AnimationIdListBean("my_alpha_action", R.anim.my_alpha_action),
            new AnimationIdListBean("my_scale_action", R.anim.my_scale_action),
            new AnimationIdListBean("myanimation_set", R.anim.myanimation_set),
            new AnimationIdListBean("myown_design", R.anim.myown_design),
            new AnimationIdListBean("push_left_in", R.anim.push_left_in),
            new AnimationIdListBean("push_left_out", R.anim.push_left_out),
            new AnimationIdListBean("push_up_in", R.anim.push_up_in),
            new AnimationIdListBean("push_up_out", R.anim.push_up_out),
            new AnimationIdListBean("right_in", R.anim.right_in),
            new AnimationIdListBean("right_out", R.anim.right_out),
            new AnimationIdListBean("rotate", R.anim.rotate),
            new AnimationIdListBean("scale", R.anim.scale),
            new AnimationIdListBean("scale_rotate", R.anim.scale_rotate),
            new AnimationIdListBean("scale_translate", R.anim.scale_translate),
            new AnimationIdListBean("scale_translate_rotate", R.anim.scale_translate_rotate),
            new AnimationIdListBean("slide_down_out", R.anim.slide_down_out),
            new AnimationIdListBean("slide_left", R.anim.slide_left),
            new AnimationIdListBean("slide_right", R.anim.slide_right),
            new AnimationIdListBean("slide_up_in", R.anim.slide_up_in),
            new AnimationIdListBean("translate", R.anim.translate),
            new AnimationIdListBean("translate_rotate", R.anim.translate_rotate),
            new AnimationIdListBean("wave_scale", R.anim.wave_scale),
            new AnimationIdListBean("zoom_enter", R.anim.zoom_enter),
            new AnimationIdListBean("zoom_exit", R.anim.zoom_exit),
    };

    @Override
    protected void onItemClicked(View v, int position, AnimationIdListBean item) {
        Animation animation = AnimationUtils.loadAnimation(SimpleAnimationActivity.this,
                item.getAnimationId());
        mRecyclerView.startAnimation(animation);
    }

    @Override
    protected AnimationIdListBean[] getItemBeans() {
        return mAnimationIdListBean;
    }
}
