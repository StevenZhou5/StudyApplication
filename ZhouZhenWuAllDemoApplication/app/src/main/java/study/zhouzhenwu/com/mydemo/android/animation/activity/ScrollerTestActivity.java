package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

public class ScrollerTestActivity extends BaseActivity {
    private View mViewContent;
    private Button mBtScrollTo;
    private Button mBtScrollBy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_test);
        mViewContent = findViewById(R.id.layout_content);
        mBtScrollBy = (Button) findViewById(R.id.bt_scroll_by);
        mBtScrollTo = (Button) findViewById(R.id.bt_scroll_to);

        mBtScrollTo.setOnClickListener(v -> mViewContent.scrollTo(50, 50));
        mBtScrollBy.setOnClickListener(v -> mViewContent.scrollBy(50, 50));
    }
}
