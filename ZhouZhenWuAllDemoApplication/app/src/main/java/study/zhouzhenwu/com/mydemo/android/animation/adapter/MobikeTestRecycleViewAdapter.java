package study.zhouzhenwu.com.mydemo.android.animation.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.android.animation.model.MobikeTestData;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.BaseRecycleViewViewHolder;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.RecycleViewArrayAdapter;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/9/5
 * 类简介：首页测试用的
 */

public class MobikeTestRecycleViewAdapter extends RecycleViewArrayAdapter<MobikeTestData> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_test_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindBaseViewHolder(BaseRecycleViewViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.onBindData(mItems.get(position));
    }


    class ViewHolder extends BaseRecycleViewViewHolder {
        @Bind(R.id.image)
        ImageView mImage;
        @Bind(R.id.tv_name)
        TextView mTvName;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindData(MobikeTestData mainTestData) {
            mImage.setImageURI(Uri.parse(mainTestData.getImageUrl()));
            mTvName.setText(mainTestData.getName());
        }
    }
}
