package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.animation.adapter.MyExpandableListItemAdapter;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.animation.model.MyExpandableListItemModel;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.activity.BaseActivity;

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
        for (int i = 0; i < 5; i++) {
            mDatas.add(new MyExpandableListItemModel());
        }
        mLvAdapter.addAll(mDatas);
    }
}
