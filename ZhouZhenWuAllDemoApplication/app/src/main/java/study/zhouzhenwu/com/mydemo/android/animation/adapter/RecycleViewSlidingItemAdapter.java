package study.zhouzhenwu.com.mydemo.android.animation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.BaseRecycleViewAdapter;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.BaseRecycleViewViewHolder;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.OnRecyclerViewItemClickListener;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.OnRecyclerViewItemLongClickListener;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/17
 * 类简介：
 */

public class RecycleViewSlidingItemAdapter extends BaseRecycleViewAdapter<RecycleViewSlidingItemAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycle_view_drag_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindBaseViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends BaseRecycleViewViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

    }
}
