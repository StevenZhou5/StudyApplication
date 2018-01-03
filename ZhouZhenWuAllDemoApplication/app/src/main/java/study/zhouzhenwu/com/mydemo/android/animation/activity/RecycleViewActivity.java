package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.android.animation.adapter.MyRecycleViewAdapter;
import study.zhouzhenwu.com.mydemo.android.animation.model.MyRecycleViewItemModel;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.OnRecyclerViewItemClickListener;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/29
 * 类简介：测试RecycleView使用的Activity
 */
public class RecycleViewActivity extends BaseActivity {
    @Bind(R.id.recycle_view)
    RecyclerView mRecycleView;
    private MyRecycleViewAdapter mRecycleViewAdapter;
    private List<MyRecycleViewItemModel> mListDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        ButterKnife.bind(this);
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
    }
}