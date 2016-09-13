package study.zhouzhenwu.com.mydemo.common.adapter;

import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

import study.zhouzhenwu.com.mydemo.MyApplication;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/4/9
 * 类简介：自定义ListView的基类Adapter，用于桥接具体Adapter
 */
public abstract class ListDataBaseAdapter<T> extends BaseAdapter {
    protected LayoutInflater mLayoutInflater;
    protected List<T> mDatas;

    public ListDataBaseAdapter() {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(MyApplication.instance.getApplicationContext());
        }
    }

    public void setData(List<T> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }


    public List<T> getData() {
        return mDatas;
    }

    @Override
    public T getItem(int position) {
        if (getCount() <= position) {
            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public void clear() {
        if (mDatas != null) {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }
}
