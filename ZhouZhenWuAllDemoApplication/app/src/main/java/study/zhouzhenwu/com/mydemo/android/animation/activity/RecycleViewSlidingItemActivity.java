package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.android.animation.adapter.RecycleViewSlidingItemAdapter;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/4/7
 * 类简介：用RecycleView实现Item的滑动
 */

public class RecycleViewSlidingItemActivity extends BaseActivity {
    @Bind(R.id.recycle_view_sliding_item)
    RecyclerView mRecycleView;

    RecycleViewSlidingItemAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_sliding_item);
        ButterKnife.bind(this);

        mAdapter = new RecycleViewSlidingItemAdapter();
        mRecycleView.setAdapter(mAdapter);

    }
}