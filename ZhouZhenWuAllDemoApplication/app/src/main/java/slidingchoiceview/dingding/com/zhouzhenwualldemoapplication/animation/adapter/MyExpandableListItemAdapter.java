package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.animation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.animation.model.MyExpandableListItemModel;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.widgets.listview.itemmanipulation.ExpandableListItemAdapter;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/25
 * 类简介：自定义的Item
 */
public class MyExpandableListItemAdapter extends ExpandableListItemAdapter<MyExpandableListItemModel> {
    private Context mContext;

    public MyExpandableListItemAdapter(Context context) {
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
}
