package study.zhouzhenwu.com.mydemo.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import study.zhouzhenwu.com.mydemo.common.adapter.ActivityRecycleViewAdapter;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.OnRecyclerViewItemClickListener;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.RecycleViewArrayAdapter;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2016/11/28
 * 类简介：用于展示ListView的Activity
 */

public abstract class ListActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    protected ActivityRecycleViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // step1:recycleView初始化
        mRecyclerView = new RecyclerView(this);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView.setLayoutParams(params);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        // step2:设置ContentView
        setContentView(mRecyclerView);

        // step3:adapter初始化并为recyclerView设置Adapter
        mAdapter = new ActivityRecycleViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // step4:数据初始化，并刷新显示最新数据
        List<ActivityListItemBean> datas = initDatas();
        mAdapter.addAllItems(datas);

        // step5:监听初始化
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(View v, int position) {
                log("onItemClick为：" + position);
                ActivityListItemBean data = mAdapter.getItem(position);
                Intent intent = new Intent(ListActivity.this, data.getActivity());
                startActivity(intent);
//                startActivityForResult(intent,-1); // 如果requestCode设置为-1的话，startActivity将不起作用
            }
        });
    }

    @NonNull
    protected List<ActivityListItemBean> initDatas() {
        List<ActivityListItemBean> datas = new ArrayList<>();
        ActivityListItemBean[] beans = getItemBeans();
        if (beans == null || beans.length == 0) {
            return null;
        }
        for (ActivityListItemBean data : beans) {
            datas.add(data);
        }
        return datas;
    }

    /**
     * 获取数据
     *
     * @return
     */
    protected ActivityListItemBean[] getItemBeans() {
        return null;
    }


}
