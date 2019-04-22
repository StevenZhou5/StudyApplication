package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.android.animation.adapter.MyRecycleViewAdapter;
import study.zhouzhenwu.com.mydemo.android.animation.model.MyRecycleViewItemModel;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.ScreenUtils;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.OnRecyclerViewItemClickListener;
import tv.cjump.jni.DeviceUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/29
 * 类简介：测试RecycleView使用的Activity
 */
public class RecycleViewActivity extends BaseActivity {
    private ViewGroup mLayoutContent;
    RecyclerView mRecycleView;
    private MyRecycleViewAdapter mRecycleViewAdapter;
    private List<MyRecycleViewItemModel> mListDatas;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        mLayoutContent = (ViewGroup) findViewById(R.id.layout_content);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);

        StringBuilder sb = new StringBuilder();
        sb.append(5);
        sb.append(" ");


        mRecycleView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(layoutManager);

        mRecycleViewAdapter = new MyRecycleViewAdapter();
        mRecycleViewAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(View v, int position) {
                showToast(position + "被点击");
                mRecycleViewAdapter.setSelectedPosition(position);
            }
        });
        mRecycleView.setAdapter(mRecycleViewAdapter);

        mListDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mListDatas.add(new MyRecycleViewItemModel());
        }
        mRecycleViewAdapter.addAllItems(mListDatas);
        mRecycleView.scrollToPosition(10); // 滑动到第几个位置

        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("ZZW", "newState:" + newState);
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    View lastChildView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    int lastItem = recyclerView.getChildLayoutPosition(lastChildView);
                    Log.d("ZZW", "最后一条可见Item是：" + lastItem);
                    if (lastItem == mRecycleViewAdapter.getItemCount() - 1) {
                        Log.d("ZZW", "lastChildView:getLeft=" + lastChildView.getLeft() +
                                "; recyclerView.getWidth:" + recyclerView.getWidth());
                        recyclerView.smoothScrollBy(-(recyclerView.getWidth() - lastChildView.getLeft()), 0);
                    }
                }
            }

        });
    }

    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int left = mRecyclerView.getChildAt(movePosition).getLeft();
                mRecyclerView.smoothScrollBy(-left, 0);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
//            mToPosition = position;
//            mShouldScroll = true;
        }
    }
}
