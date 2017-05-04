package study.zhouzhenwu.com.mydemo.android.animation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.android.animation.model.MyExpandableListItemModel;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.ExpandableRecycleViewAdapter;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2016/12/9
 * 类简介：
 */

public class MyExpandableRecycleViewItemAdapter extends ExpandableRecycleViewAdapter<MyExpandableListItemModel> {
    private Context mContext;

    public MyExpandableRecycleViewItemAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View getTitleView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.my_expandable_title_view_layout, parent, false);
        }
        return convertView;
    }

    @Override
    public View getContentView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.my_expandable_content_view_layout, parent, false);
        }
        return convertView;
    }

    @Override
    public void onAnimationEnd(boolean isExpanding) {

    }
}
