package study.zhouzhenwu.com.mydemo.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.module.SimpleListItemBean;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.BaseRecycleViewViewHolder;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.RecycleViewArrayAdapter;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2016/11/30
 * 类简介：用于进行Activi跳转的的list的Adapter；（List的实现用的是RecycleView）
 */

public class SimpleRecycleViewAdapter extends RecycleViewArrayAdapter<SimpleListItemBean> {
    @Override
    public BaseRecycleViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_activity_list, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindBaseViewHolder(BaseRecycleViewViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SimpleListItemBean data = mItems.get(position);
        String name = data.getName();
        viewHolder.mTvName.setText(name);
    }

    class ViewHolder extends BaseRecycleViewViewHolder {
        TextView mTvName;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_name);
        }
    }

}
