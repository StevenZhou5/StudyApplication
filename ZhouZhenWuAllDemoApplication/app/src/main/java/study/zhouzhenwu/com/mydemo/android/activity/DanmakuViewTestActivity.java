package study.zhouzhenwu.com.mydemo.android.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.util.SystemClock;
import master.flame.danmaku.ui.widget.DanmakuView;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

public class DanmakuViewTestActivity extends BaseActivity {
    private DanmakuView mDanmakuView;
    private DanmakuContext mDanmakuContext;

    private View mBtServer;
    private View mBtMyself;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmaku_view_test);
        mDanmakuView = (DanmakuView) findViewById(R.id.view_danmaku);

        mBtServer = findViewById(R.id.tv_server);
        mBtMyself = findViewById(R.id.tv_my_self);

        // 设置最大显示行数: 必须当danmaku.priority的值为0时才有效
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠：必须当danmaku.priority的值为0时才有效
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f)
//                .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
//        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair)
                .setDanmakuMargin(40);
        mDanmakuView.prepare(createParser(), mDanmakuContext);

        mBtServer.setOnClickListener(v -> {
            if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
                mDanmakuView.resume();
            }
            requestServerDanmaku();
        });

        mBtMyself.setOnClickListener(v -> {
            if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
                mDanmakuView.resume();
            }
            addMySelfDanmaku(false);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    private BaseDanmakuParser createParser() {
        return new BaseDanmakuParser() {

            @Override
            protected Danmakus parse() {
                return new Danmakus();
            }
        };

    }

    private void addMySelfDanmaku(boolean isLive) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = "这是一条用户当前发的弹幕的标题";
        danmaku.priority = 1; // 大于0一定会显示，用户自己的弹幕一定会显示
        danmaku.setTime(mDanmakuView.getCurrentTime() + 1200); // 弹幕开始出现的时间
        danmaku.setDuration(new Duration(5000));// 弹幕显示的时长
        danmaku.isLive = isLive;

        danmaku.textColor = Color.RED; // 弹幕文字颜色
        danmaku.textSize = 50f;
        mDanmakuView.addDanmaku(danmaku);
    }

    Timer timer = new Timer();

    private void requestServerDanmaku() {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new AsyncAddTask(), 0);
    }

    class AsyncAddTask extends TimerTask {

        @Override
        public void run() {
            for (int i = 0; i < 500; i++) {
                addServerDanmaku(true);
                SystemClock.sleep(200);
            }
        }
    }

    private void addServerDanmaku(boolean isLive) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = "这是一条服务器返回的弹幕的标题";
        danmaku.priority = 0; // 大于0一定会显示，用户自己的弹幕一定会显示
        danmaku.setTime(mDanmakuView.getCurrentTime() + 1200); // 弹幕开始出现的时间

        danmaku.setDuration(new Duration((long) (Math.random() * 2000) + 3000));// 弹幕显示的时长
        danmaku.isLive = isLive;

        danmaku.textColor = Color.WHITE; // 弹幕文字颜色
        danmaku.textSize = 50f;
        mDanmakuView.addDanmaku(danmaku);
    }
}
