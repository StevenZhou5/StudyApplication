package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.widgets.chart.ChartView;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/9/6
 * 类简介：展示趋势图View的Activity
 */
public class TendencyChartActivity extends BaseActivity {
    ChartView mChartView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tendency_chart);
        mChartView = (ChartView) findViewById(R.id.chart_view);

        mChartView.setDatats(null);

        mChartView.setOnSelectedPositionChangeListener(new ChartView.OnSelectedPositionChangeListener() {
            @Override
            public void onSelectedPosition(int position) {
                showToast("当前选中位置为：" + position);
            }
        });
    }
}
