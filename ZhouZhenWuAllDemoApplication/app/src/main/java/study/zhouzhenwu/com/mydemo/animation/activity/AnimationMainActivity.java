package study.zhouzhenwu.com.mydemo.animation.activity;

import study.zhouzhenwu.com.mydemo.common.activity.ListActivity;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16//8
 * 类简介：Animation相关入口Activity
 */
public class AnimationMainActivity extends ListActivity {
    /**
     * 各个Animation页面
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("滑动吸顶动画", ChangeWithScrollActivity.class),
            new ActivityListItemBean("日历", CalendarActivity.class),
            new ActivityListItemBean("item可以展开的ListView", ExpandableListViewActivity.class),
            new ActivityListItemBean("item可以展开的RecycleView", ExpandableRecycleViewActivity.class),
            new ActivityListItemBean("RecycleView代替ListView", RecycleViewActivity.class),
            new ActivityListItemBean("评级流程趋势折线图", TendencyChartActivity.class),
            new ActivityListItemBean("贝塞尔曲线绘制", BezierActivity.class),
            new ActivityListItemBean("Canvas时钟绘制", ClockActivity.class),
            new ActivityListItemBean("心形绘制", HeartActivity.class),
            new ActivityListItemBean("随动控件的各个属性值动态改变测试",MoveTestActivity.class)
    };

    @Override
    protected ActivityListItemBean[] getItemBeans() {
        return mAllItemBeans;
    }
}
