package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.adapter.MainActivityListAdapter;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.adapter.MyBaseAdapter;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.bean.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：程序主入口Activity
 */
public class MainActivity extends AppCompatActivity {
    private ListView mLvMain;
    private MyBaseAdapter mLvMainAdapter;

    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("java相关测试", AboutScrollViewActivity.class),
            new ActivityListItemBean("滑动吸顶效果", ChangeWithScrollActivity.class),
            new ActivityListItemBean("算法测试", AlgorithmActivity.class),
            new ActivityListItemBean("设计模式", DesignPatternActivity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initListView();
        initFloationButton();
    }

    /**
     * // ListView初始化
     */
    private void initListView() {
        mLvMain = (ListView) findViewById(R.id.lv_main);
        mLvMainAdapter = new MainActivityListAdapter();
        // 数据初始化
        List<Object> datas = new ArrayList<>();
        for (ActivityListItemBean data : mAllItemBeans) {
            datas.add(data);
        }
        mLvMainAdapter.setDatas(datas);
        mLvMain.setAdapter(mLvMainAdapter);
        mLvMain.setOnItemClickListener(mOnItemClickListener);
    }

    /**
     * ListView的点击监听
     */
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ActivityListItemBean data = (ActivityListItemBean) mLvMainAdapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, data.getActivity());
            startActivity(intent);
        }
    };


    /**
     * 选择按钮初始化
     */
    private void initFloationButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
