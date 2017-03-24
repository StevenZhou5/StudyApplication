package study.zhouzhenwu.com.mydemo.animation.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.animation.model.MyRecycleViewItemModel;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.BaseRecycleViewViewHolder;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.RecycleViewArrayAdapter;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/29
 * 类简介：
 */
public class MyRecycleViewAdapter extends RecycleViewArrayAdapter<MyRecycleViewItemModel> {
    private int mSelectedPosition = -1;

    public void setSelectedPosition(int selectedPosition) {
        this.mSelectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    @Override
    public BaseRecycleViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtils.log("onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recycle_view_item_layout, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindBaseViewHolder(BaseRecycleViewViewHolder holder, int position) {
        LogUtils.log("onBindViewHolder");
        ViewHolder viewHolder = (ViewHolder) holder;
        MyRecycleViewItemModel data = mItems.get(position);
        viewHolder.tvTitle.setText(data.getTitle() + position + "");
        if (mSelectedPosition == position) {
            viewHolder.tvTitle.setTextColor(Color.RED);
        } else {
            viewHolder.tvTitle.setTextColor(Color.BLACK);
        }
    }

    private class ViewHolder extends BaseRecycleViewViewHolder {
        private TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
