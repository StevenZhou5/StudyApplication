package study.zhouzhenwu.com.mydemo.android.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import study.zhouzhenwu.com.mydemo.android.animation.activity.BezierActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.CalendarActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.Camera3DAnimationActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.ChangeWithScrollActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.ClockActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.ExpandableListViewActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.ExpandableRecycleViewActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.HeartActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.MobikeActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.MoveTestActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.OpenActivityFromSrcViewToTargetViewActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.PercentTextViewActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.ProterDufferTestActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.RecycleViewActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.RecycleViewDragItemActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.RecycleViewSlidingItemActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.ScrollerTestActivity;
import study.zhouzhenwu.com.mydemo.android.animation.activity.TendencyChartActivity;
import study.zhouzhenwu.com.mydemo.common.activity.ActivityListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16//8
 * 类简介：Animation相关入口Activity
 */
public class AnimationMainActivityActivity extends ActivityListActivity {
    /**
     * 各个Animation页面
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("ScrollerTest动画", ScrollerTestActivity.class),
            new ActivityListItemBean("Camera进行3D动画", Camera3DAnimationActivity.class),
            new ActivityListItemBean("简单动画集合", SimpleAnimationActivity.class),
            new ActivityListItemBean("滑动吸顶动画", ChangeWithScrollActivity.class),
            new ActivityListItemBean("日历", CalendarActivity.class),
            new ActivityListItemBean("item可以展开的ListView", ExpandableListViewActivity.class),
            new ActivityListItemBean("item可以展开的RecycleView", ExpandableRecycleViewActivity.class),
            new ActivityListItemBean("RecycleView代替ListView", RecycleViewActivity.class),
            new ActivityListItemBean("RecycleView的实现Item的滑动", RecycleViewSlidingItemActivity.class),
            new ActivityListItemBean("RecycleView实现拖拽功能", RecycleViewDragItemActivity.class),
            new ActivityListItemBean("评级流程趋势折线图", TendencyChartActivity.class),
            new ActivityListItemBean("贝塞尔曲线绘制", BezierActivity.class),
            new ActivityListItemBean("Canvas时钟绘制", ClockActivity.class),
            new ActivityListItemBean("心形绘制", HeartActivity.class),
            new ActivityListItemBean("随动控件的各个属性值动态改变测试", MoveTestActivity.class),
            new ActivityListItemBean("由一个小View渐变到一个大View打开Activity", OpenActivityFromSrcViewToTargetViewActivity.class),
            new ActivityListItemBean("图片搜索展示", MobikeActivity.class),
            new ActivityListItemBean("ProterDuff测试", ProterDufferTestActivity.class),
            new ActivityListItemBean("百分比TextView测试", PercentTextViewActivity.class),
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* List<ActivityListItemBean> datas = arrayToList();
        List<ActivityListItemBean> datas2 = new ArrayList<>();
        List<ActivityListItemBean> datas3 = new ArrayList<>();

        log("AddAllStart:" + SystemClock.currentThreadTimeMillis() + "");
        for (int i = 0; i < 10000; i++) {
            datas2.addAll(datas);
        }
        log("AddAllEnd:" + SystemClock.currentThreadTimeMillis() + "");
        datas2.get(0).setName("哈哈哈");
        log("集合1：" + datas.get(0).getName() + "; 集合2：" + datas2.get(0).getName());

        log("AddOneByOneStart:" + SystemClock.currentThreadTimeMillis() + "");
        for (int j = 0; j < 10000; j++) {
            for (int i = 0; i < datas.size(); i++) {
                datas3.add(datas.get(i));
            }
        }
        log("AddOneByOneEnd:" + SystemClock.currentThreadTimeMillis() + "");*/

    }

    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }

    private int[] getValues() {
        return new int[]{1, 2};
    }
}
