package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.widgets.PercentTextView;

public class PercentTextViewActivity extends BaseActivity {
    PercentTextView mPercentTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent_text_view);
        mPercentTextView = (PercentTextView) findViewById(R.id.percent_tv);

        mPercentTextView.setOnClickListener(v -> {
            double percent = Math.random();
            mPercentTextView.setPercent(percent);
            mPercentTextView.setText((int) (percent * 100) + "%");

        });
    }
}
