package study.zhouzhenwu.com.mydemo.android.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import me.drakeet.multitype.ItemViewBinder;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.adapter.SimpleRecycleViewAdapter;
import study.zhouzhenwu.com.mydemo.common.module.SimpleListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/7/10
 * 类简介：用于MultiType测试的垂直RecycleView
 */
public class MultiTypeVerticalRecycleViewViewBinder extends ItemViewBinder<MultiTypeVerticalRecycleView, MultiTypeVerticalRecycleViewViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_multi_type_vertical_recycle_view, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MultiTypeVerticalRecycleView multiTypeVerticalRecycleView) {
        int size = (int) (Math.random() * 20);
        for (int i = 0; i < size; i++) {
            holder.mAdapter.addItem(new SimpleListItemBean("第" + i + "条测试数据"));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        SimpleRecycleViewAdapter mAdapter;

        ViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycle_view);
            mAdapter = new SimpleRecycleViewAdapter();
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

        }
    }
}
