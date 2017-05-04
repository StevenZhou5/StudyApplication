package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.android.animation.adapter.MyExpandableListItemAdapter;
import study.zhouzhenwu.com.mydemo.android.animation.model.MyExpandableListItemModel;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/25
 * 类简介：可以将item内容展开的ListView
 */
public class ExpandableListViewActivity extends BaseActivity {
    @Bind(R.id.lv_expandable)
    ListView mLvExpandable;

    private MyExpandableListItemAdapter mLvAdapter;

    private List<MyExpandableListItemModel> mDatas;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);
        ButterKnife.bind(this);

        mLvAdapter = new MyExpandableListItemAdapter(this);
        mLvExpandable.setAdapter(mLvAdapter);
//        mLvAdapter.setLimit(1); // 设置最多限制几个可以撑开


        mDatas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            mDatas.add(new MyExpandableListItemModel());
        }
        mLvAdapter.addAll(mDatas);
    }
}
