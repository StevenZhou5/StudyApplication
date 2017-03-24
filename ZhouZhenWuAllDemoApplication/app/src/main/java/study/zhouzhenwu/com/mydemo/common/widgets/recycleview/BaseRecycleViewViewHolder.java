package study.zhouzhenwu.com.mydemo.common.widgets.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/3/22
 * 类简介： 基类的RecycleViewViewHolder，内部实现了ItemView的点击和长按监听
 */

public class BaseRecycleViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public static final int TAG_TYPE_DEFAULT_ITEM_VIEW = -1;

    private OnRecyclerViewItemClickListener mItemClickListener;
    private OnRecyclerViewItemLongClickListener mItemLongClickListener;

    /**
     * 无点击监听的构造函数
     *
     * @param itemView
     */
    public BaseRecycleViewViewHolder(View itemView) {
        super(itemView);
        init(itemView, null, null);
    }

    /**
     * 含有点击监听构造函数
     *
     * @param itemView
     * @param clickListener     点击监听
     * @param longClickListener 长按监听
     */
    public BaseRecycleViewViewHolder(View itemView, OnRecyclerViewItemClickListener clickListener, OnRecyclerViewItemLongClickListener longClickListener) {
        super(itemView);
        init(itemView, clickListener, longClickListener);
    }

    private void init(View itemView, OnRecyclerViewItemClickListener clickListener, OnRecyclerViewItemLongClickListener longClickListener) {
        mItemClickListener = clickListener;
        mItemLongClickListener = longClickListener;
        itemView.setTag(TAG_TYPE_DEFAULT_ITEM_VIEW);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    /**
     * 动态设置点击监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener itemClickListener) {
        if (this.mItemClickListener == itemClickListener) {
            return;
        }
        this.mItemClickListener = itemClickListener;
    }

    /**
     * 动态设置长按监听监听
     *
     * @param itemLongClickListener
     */
    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener itemLongClickListener) {
        if (this.mItemLongClickListener == itemLongClickListener) {
            return;
        }
        this.mItemLongClickListener = itemLongClickListener;
    }

    @Override
    public void onClick(View v) {
        LogUtils.log("itemView的点击触发");
        if (mItemClickListener != null) {
            mItemClickListener.onRecyclerViewItemClick(v, getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mItemLongClickListener != null) {
            mItemLongClickListener.onRecyclerViewItemLongClick(v, getLayoutPosition());
        }
        return false;
    }
}