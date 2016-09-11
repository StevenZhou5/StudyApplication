package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.animation.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.animation.model.MyRecycleViewItemModel;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.adapter.RecycleViewBaseAdapter;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/29
 * 类简介：
 */
public class MyRecycleViewAdapter extends RecycleViewBaseAdapter<MyRecycleViewItemModel> {
    private int mSelectedPosition = -1;

    public void setSelectedPosition(int selectedPosition) {
        this.mSelectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recycle_view_item_layout, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        MyRecycleViewItemModel data = mDatas.get(position);
        viewHolder.tvTitle.setText(data.getTitle() + position + "");
        if (mSelectedPosition == position) {
            viewHolder.tvTitle.setTextColor(Color.RED);
        } else {
            viewHolder.tvTitle.setTextColor(Color.BLACK);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClicked(this.getLayoutPosition());
        }
    }
}
