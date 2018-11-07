package study.zhouzhenwu.com.mydemo.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.widgets.PercentTextView;

/**
 * Created by Steven on 2018/7/4.
 */

public class AirbnbLottieAnimationTestActivity extends BaseActivity {
    PercentTextView mPercentTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airbnb_lottie_animation_test);

        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);

        animationView.setAnimation("lottiefiles.com - Emoji Wink.json");
//        animationView.setAnimation("City.json");
        animationView.loop(true);


        mPercentTextView = (PercentTextView) findViewById(R.id.percent_tv);

        mPercentTextView.setOnClickListener(v -> {
            double percent = Math.random();
            mPercentTextView.setPercent(percent);
            mPercentTextView.setText((int) (percent * 100) + "%");

        });
    }
}
