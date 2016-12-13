package study.zhouzhenwu.com.mydemo.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.animation.adapter.MyExpandableRecycleViewItemAdapter;
import study.zhouzhenwu.com.mydemo.animation.model.MyExpandableListItemModel;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2016/12/9
 * 类简介：
 */

public class ExpandableRecycleViewActivity extends BaseActivity {
    @Bind(R.id.recycle_view)
    RecyclerView mRecycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_recycle_view);
        ButterKnife.bind(this);

        mRecycleView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);

        MyExpandableRecycleViewItemAdapter adapter = new MyExpandableRecycleViewItemAdapter(this);
        mRecycleView.setAdapter(adapter);

        List mDatas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            mDatas.add(new MyExpandableListItemModel());
        }
        adapter.addAllItems(mDatas);


    }
}
