package study.zhouzhenwu.com.mydemo.common.widgets.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/3/23
 * 类简介：基类的RecycleViewAdapter，可以设置Item的点击监听和长按监听，这个类的ViewHolder类型必须是BaseRecycleViewViewHolder类型，否则会抛出异常
 */

public abstract class BaseRecycleViewAdapter<VH extends BaseRecycleViewViewHolder> extends RecyclerView.Adapter<VH> implements OnRecyclerViewItemClickListener, OnRecyclerViewItemLongClickListener {
    protected OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;
    protected OnRecyclerViewItemLongClickListener mOnRecyclerViewItemLongClickListener;

    @Override
    public void onBindViewHolder(VH holder, int position) {
        LogUtils.log("onBindViewHolder");
        holder.setOnItemClickListener(this);
        holder.setOnItemLongClickListener(this);
        onBindBaseViewHolder(holder, position);
    }

    public abstract void onBindBaseViewHolder(VH holder, int position);


    /*-------------------------------------- recycleView的Item点击 ------------------------------*/

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public void onRecyclerViewItemClick(View v, int position) {
        if (mOnRecyclerViewItemClickListener == null) {
            return;
        }
        mOnRecyclerViewItemClickListener.onRecyclerViewItemClick(v, position);
    }

    /*-------------------------------------- recycleView的Item长按点击 ------------------------------*/

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener) {
        mOnRecyclerViewItemLongClickListener = onRecyclerViewItemLongClickListener;
    }

    @Override
    public void onRecyclerViewItemLongClick(View v, int position) {
        if (mOnRecyclerViewItemLongClickListener == null) {
            return;
        }
        mOnRecyclerViewItemLongClickListener.onRecyclerViewItemLongClick(v, position);
    }
}
