package study.zhouzhenwu.com.mydemo.common.widgets.recycleview;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import study.zhouzhenwu.com.mydemo.animation.adapter.MyRecycleViewAdapter;
import study.zhouzhenwu.com.mydemo.animation.model.MyRecycleViewItemModel;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/29
 * 类简介：自定义RecycleView的含有List数据吗的Adapter的基类
 */
public abstract class RecycleViewArrayAdapter<T> extends BaseRecycleViewAdapter {
    protected List<T> mItems;

    public RecycleViewArrayAdapter() {
        this(null);
    }

    public RecycleViewArrayAdapter(List<T> items) {
        mItems = new ArrayList<T>();
        if (items == null) {
            return;
        }
        mItems.addAll(items);
    }

    /**
     * 将所有items添加到list后面
     *
     * @param items 要添加的items
     */
    public void addAllItems(Collection<? extends T> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 将所有items从指定位置插入到list中去
     *
     * @param location 要插入的位置
     * @param items    要添加的items
     */
    public void addAllItems(int location, Collection<? extends T> items) {
        mItems.addAll(location, items);
        notifyDataSetChanged();
    }

    /**
     * 添加某一条item到list的末端
     *
     * @param item 要添加的item
     */
    public void addItem(T item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    /**
     * 在指定位置插入一条item
     *
     * @param location 要插入的位置
     * @param item     要添加的item
     */
    public void addItem(int location, T item) {
        mItems.add(location, item);
        notifyDataSetChanged();
    }

    /**
     * 移除某一条Item
     *
     * @param item 要移除的Item
     */
    public void removeItem(T item) {
        mItems.remove(item);
        notifyDataSetChanged();
    }

    /**
     * 移除指定位置上的Item
     *
     * @param location 要移除的item的所在的位置
     */
    public void removeItem(int location) {
        mItems.remove(location);
        notifyDataSetChanged();
    }

    /**
     * 移除所有Items
     *
     * @param items 要移除的items
     */
    public void removeAllItems(Collection<? extends T> items) {
        mItems.removeAll(items);
        notifyDataSetChanged();
    }

    /**
     * 清空list
     */
    public void clearItems() {
        mItems.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public T getItem(int position) {
        if (getItemCount() <= position) {
            return null;
        }
        return mItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

}
