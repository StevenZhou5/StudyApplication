package study.zhouzhenwu.com.mydemo.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import study.zhouzhenwu.com.mydemo.R;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/29
 * 类简介：自定义RecycleView的Adapter的基类
 */
public abstract class RecycleViewBaseAdapter<T> extends RecyclerView.Adapter {
    protected List<T> mDatas;
    protected OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;
    protected OnRecyclerViewItemLongClickListener mOnRecyclerViewItemLongClickListener;

    public void setData(List<T> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public T getItem(int position) {
        if (getItemCount() <= position) {
            return null;
        }
        return mDatas.get(position);
    }

    /*-------------------------------------- recycleView的Item点击 ------------------------------*/
    protected void onItemClicked(View v, int position) {
        if (mOnRecyclerViewItemClickListener != null) {
            mOnRecyclerViewItemClickListener.onRecyclerViewItemClick(v, position);
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener) {
        this.mOnRecyclerViewItemClickListener = mOnRecyclerViewItemClickListener;
    }

    /*-------------------------------------- recycleView的Item长按点击 ------------------------------*/
    protected void onItemLongClicked(View v, int position) {
        if (mOnRecyclerViewItemLongClickListener != null) {
            mOnRecyclerViewItemLongClickListener.onRecyclerViewItemLongClick(v, position);
        }
    }

    public void setOnRecyclerViewItemLongClickListener(OnRecyclerViewItemLongClickListener mOnRecyclerViewItemLongClickListener) {
        this.mOnRecyclerViewItemLongClickListener = mOnRecyclerViewItemLongClickListener;
    }


    /*-------------------------------------- 内部类或者内部接口定义 ------------------------------*/

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public BaseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClicked(v, getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemLongClicked(v, getLayoutPosition());
            return false;
        }
    }

    /**
     * recycleView的Item点击监听接口（注意：接口必须是public的）
     */
    public interface OnRecyclerViewItemClickListener {
        void onRecyclerViewItemClick(View v, int position);

    }

    /**
     * recycleView的Item长按监听接口（注意：接口必须是public的）
     */
    public interface OnRecyclerViewItemLongClickListener {
        void onRecyclerViewItemLongClick(View v, int position);
    }

}
