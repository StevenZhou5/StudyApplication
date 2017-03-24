package study.zhouzhenwu.com.mydemo.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.BaseRecycleViewViewHolder;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.RecycleViewArrayAdapter;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2016/11/30
 * 类简介：用于进行Activi跳转的的list的Adapter；（List的实现用的是RecycleView）
 */

public class ActivityRecycleViewAdapter extends RecycleViewArrayAdapter<ActivityListItemBean> {
    @Override
    public BaseRecycleViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_activity_list, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindBaseViewHolder(BaseRecycleViewViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ActivityListItemBean data = mItems.get(position);
        String name = data.getName();
        viewHolder.mTvName.setText(name);
    }

    class ViewHolder extends BaseRecycleViewViewHolder {
        @Bind(R.id.tv_name)
        TextView mTvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
