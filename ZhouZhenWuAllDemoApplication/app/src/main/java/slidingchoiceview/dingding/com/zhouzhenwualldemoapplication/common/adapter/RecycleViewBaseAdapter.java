package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/29
 * 类简介：自定义RecycleView的Adapter的基类
 */
public abstract class RecycleViewBaseAdapter<T> extends RecyclerView.Adapter {
    protected List<T> mDatas;
    protected OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;
    protected OnRecyclerViewItemLongClickListener mOnRecyclerViewItemLongClickListener;

    protected void onItemClicked(int position) {
        if (mOnRecyclerViewItemClickListener != null) {
            mOnRecyclerViewItemClickListener.onRecyclerViewItemClick(position);
        }
    }

    protected void onItemLongClicked(int position) {
        if (mOnRecyclerViewItemLongClickListener != null) {
            mOnRecyclerViewItemLongClickListener.onRecyclerViewItemLongClick(position);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setData(List<T> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener) {
        this.mOnRecyclerViewItemClickListener = mOnRecyclerViewItemClickListener;
    }

    public void setOnRecyclerViewItemLongClickListener(OnRecyclerViewItemLongClickListener mOnRecyclerViewItemLongClickListener) {
        this.mOnRecyclerViewItemLongClickListener = mOnRecyclerViewItemLongClickListener;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onRecyclerViewItemClick(int position);

    }

    public static interface OnRecyclerViewItemLongClickListener {
        void onRecyclerViewItemLongClick(int position);
    }
}
