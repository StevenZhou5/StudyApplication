package study.zhouzhenwu.com.mydemo.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.android.adapter.BannerBeanViewBinder;
import study.zhouzhenwu.com.mydemo.android.adapter.MultiTypeBannerData;
import study.zhouzhenwu.com.mydemo.android.adapter.MultiTypeTestText;
import study.zhouzhenwu.com.mydemo.android.adapter.MultiTypeVerticalRecycleView;
import study.zhouzhenwu.com.mydemo.android.adapter.MultiTypeVerticalRecycleViewViewBinder;
import study.zhouzhenwu.com.mydemo.android.adapter.TestTextViewBinder;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/7/1
 * 类简介： 使用Multitype来实现多种不同类型Item展示的效果
 */

public class MultiTypeActivity extends BaseActivity {
    @Bind(R.id.recycle_view)
    RecyclerView mRecycleView;

    private MultiTypeAdapter mMultiTypeAdapter;
    private Items mMultiTypeItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type_recycle_view);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mMultiTypeAdapter = new MultiTypeAdapter();
        mMultiTypeAdapter.register(MultiTypeBannerData.class, new BannerBeanViewBinder());
        mMultiTypeAdapter.register(MultiTypeTestText.class, new TestTextViewBinder());
        mMultiTypeAdapter.register(MultiTypeVerticalRecycleView.class, new MultiTypeVerticalRecycleViewViewBinder());

        mRecycleView.setAdapter(mMultiTypeAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mRecycleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMultiTypeItems = new Items();
                mMultiTypeItems.add(new MultiTypeBannerData()); // 添加Banner数据
                mMultiTypeItems.add(new MultiTypeTestText()); // 添加TestText数据
                mMultiTypeItems.add(new MultiTypeTestText()); // 添加TestText数据
                mMultiTypeItems.add(new MultiTypeTestText()); // 添加TestText数据
                mMultiTypeItems.add(new MultiTypeVerticalRecycleView()); // 添加TestText数据

                mMultiTypeAdapter.setItems(mMultiTypeItems);
                mMultiTypeAdapter.notifyDataSetChanged();
            }
        }, 300);
    }
}
