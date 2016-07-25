package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.main.activity;

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
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.algorithm.activity.AlgorithmActivity;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.android.activity.AndroidTestActivity;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.animation.activity.ChangeWithScrollActivity;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.adapter.LIstDataBaseAdapter;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.utils.LogUtils;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.designpattern.activity.DesignPatternActivity;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.java.activity.JavaTestActivity;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.main.adapter.MainActivityListAdapter;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.main.module.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：程序主入口Activity
 */
public class MainActivity extends AppCompatActivity {
    private ListView mLvMain;
    private LIstDataBaseAdapter mLvMainAdapter;

    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("Android相关测试", AndroidTestActivity.class),
            new ActivityListItemBean("java相关测试", JavaTestActivity.class),
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
        LogUtils.log(getClass().getSimpleName() + ":onCreate");
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
        // 迭代器移除测试
       /* Log.d("ZZW", "开始");
        Iterator iterator = datas.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
            for (Object data : datas) {
                ActivityListItemBean bean = (ActivityListItemBean) data;
                Log.d("ZZW", bean.getName());
            }
        }*/
        mLvMainAdapter.setData(datas);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.log(getClass().getName() + ":onNewIntent");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.log(getClass().getSimpleName() + ":onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.log(getClass().getSimpleName() + ":onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.log(getClass().getSimpleName() + ":onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.log(getClass().getSimpleName() + ":onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.log(getClass().getSimpleName() + ":onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.log(getClass().getSimpleName() + ":onDestroy");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtils.log(getClass().getSimpleName() + ":onTrimMemory");
    }
}
