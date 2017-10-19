package study.zhouzhenwu.com.mydemo.algorithm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.BaseRecycleViewViewHolder;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.RecycleViewArrayAdapter;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/9/9
 * 类简介：
 */

public class MatrixIntAdapter extends RecycleViewArrayAdapter<Integer> {
    @Override
    public void onBindBaseViewHolder(BaseRecycleViewViewHolder holder, int position) {
        ((ViewHolder) holder).onBindView(mItems.get(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matrix_int, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecycleViewViewHolder {
        @Bind(R.id.tv_int)
        TextView mTvInt;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindView(Integer integer) {
            mTvInt.setText(integer.toString());

        }

    }

}
