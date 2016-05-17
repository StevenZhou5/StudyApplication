package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/4/9
 * 类简介：自定义ListView的基类Adapter，用于桥接具体Adapter
 */
public class MyBaseAdapter extends BaseAdapter {
    public List<Object> mDatas;

    public void setDatas(List<Object> datas) {
        mDatas = datas;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (mDatas != null) {
            count = mDatas.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas != null && mDatas.get(position) != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
