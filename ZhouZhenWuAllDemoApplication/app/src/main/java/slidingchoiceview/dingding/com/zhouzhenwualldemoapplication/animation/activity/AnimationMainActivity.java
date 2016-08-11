package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.animation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.activity.BaseActivity;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.adapter.ActivityListAdapter;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.adapter.ListDataBaseAdapter;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.module.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16//8
 * 类简介：Animation相关入口Activity
 */
public class AnimationMainActivity extends BaseActivity {
    @Bind(R.id.lv_animation_main)
    ListView mLvMain;

    private ListDataBaseAdapter mLvAdapter;

    /**
     * 各个Animation页面
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("滑动吸顶动画", ChangeWithScrollActivity.class),
            new ActivityListItemBean("日历", CalendarActivity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_main);
        ButterKnife.bind(this);

        // step1:View相关初始化
        mLvAdapter = new ActivityListAdapter();
        mLvMain.setAdapter(mLvAdapter);

        // step2:数据相关初始化
        List<ActivityListItemBean> listDatas = new ArrayList<>();
        for (ActivityListItemBean bean : mAllItemBeans) {
            listDatas.add(bean);
        }
        mLvAdapter.setData(listDatas);

        // step3：listener相关初始化
        mLvMain.setOnItemClickListener(mLvItemClickListener);
    }

    private AdapterView.OnItemClickListener mLvItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ActivityListItemBean data = (ActivityListItemBean) mLvAdapter.getItem(position);
            Intent intent = new Intent(AnimationMainActivity.this, data.getActivity());
            startActivity(intent);
        }
    };
}
