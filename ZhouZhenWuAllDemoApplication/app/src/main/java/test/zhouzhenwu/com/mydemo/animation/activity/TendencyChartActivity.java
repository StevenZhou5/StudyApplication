package test.zhouzhenwu.com.mydemo.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.zhouzhenwu.com.mydemo.R;
import test.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import test.zhouzhenwu.com.mydemo.common.widgets.chart.ChartView;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/9/6
 * 类简介：展示趋势图View的Activity
 */
public class TendencyChartActivity extends BaseActivity {

    @Bind(R.id.chart_view)
    ChartView mChartView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tendency_chart);
        ButterKnife.bind(this);

        mChartView.setDatats(null);

        mChartView.setOnSelectedPositionChangeListener(new ChartView.OnSelectedPositionChangeListener() {
            @Override
            public void onSelectedPosition(int position) {
                showToast("当前选中位置为：" + position);
            }
        });
    }
}
