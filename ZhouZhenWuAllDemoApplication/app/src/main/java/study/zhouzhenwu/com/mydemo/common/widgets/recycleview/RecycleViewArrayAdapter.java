package study.zhouzhenwu.com.mydemo.common.widgets.recycleview;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;

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
        if (items == null) {
            return;
        }
        int startPosition = mItems.size();
        int itemCount = items.size();
        Log.d("ZZW", "addAllItems方法被调用：startPosition为" + startPosition + "; itemCount为:" + itemCount);
        mItems.addAll(items);
        notifyItemRangeInserted(startPosition, itemCount);
    }

    /**
     * 将所有items从指定位置插入到list中去
     *
     * @param location 要插入的位置
     * @param items    要添加的items
     */
    public void addAllItems(int location, Collection<? extends T> items) {
        if (items == null || location < 0 || location > items.size()) {
            return;
        }
        int itemCount = items.size();
        Log.d("ZZW", "addAllItems方法被调用：startPosition为" + location + "; itemCount为:" + itemCount);
        mItems.addAll(location, items);
        notifyItemRangeInserted(location, itemCount);
    }

    /**
     * 添加某一条item到list的末端
     *
     * @param item 要添加的item
     */
    public void addItem(T item) {
        if (item == null) {
            return;
        }
        int startPosition = mItems.size();
        mItems.add(item);
        notifyItemInserted(startPosition);
        Log.d("ZZW", "addItem方法被调用：startPosition为" + startPosition + ";");
    }

    /**
     * 在指定位置插入一条item
     *
     * @param location 要插入的位置
     * @param item     要添加的item
     */
    public void addItem(int location, T item) {
        if (item == null || location < 0 || location > mItems.size()) {
            return;
        }
        mItems.add(location, item);
        notifyItemInserted(location);
        Log.d("ZZW", "addItem方法被调用：startPosition为" + location + ";");
    }

    /**
     * 移除某一条Item
     *
     * @param item 要移除的Item
     */
    public void removeItem(T item) {
        int location = mItems.indexOf(item); // 先找出位置信息，然后移除对应位置的数据
        removeItem(location);
    }

    /**
     * 移除指定位置上的Item
     *
     * @param location 要移除的item的所在的位置
     */
    public void removeItem(int location) {
        if (location < 0 || location >= mItems.size()) {
            return;
        }
        mItems.remove(location);
        notifyItemRemoved(location);
        Log.d("ZZW", "removeItem方法被调用：removePosition为" + location + ";");
    }

    /**
     * 移除所有Items
     *
     * @param items 要移除的items
     */
    public void removeAllItems(Collection<? extends T> items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        Iterator<? extends T> it = items.iterator();
        while (it.hasNext()) { // 遍历然后一个一个移除
            removeItem(it.next());
        }
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
