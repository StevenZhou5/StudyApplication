package study.zhouzhenwu.com.mydemo.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.algorithm.activity.AlgorithmActivity;
import study.zhouzhenwu.com.mydemo.android.activity.AndroidTestActivity;
import study.zhouzhenwu.com.mydemo.animation.activity.AnimationMainActivity;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.adapter.ActivityListAdapter;
import study.zhouzhenwu.com.mydemo.common.adapter.ListDataBaseAdapter;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;
import study.zhouzhenwu.com.mydemo.designpattern.activity.DesignPatternActivity;
import study.zhouzhenwu.com.mydemo.java.activity.JavaTestActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/19
 * 类简介：程序主入口Activity
 */
public class MainActivity extends BaseActivity {
    private ListView mLvMain;
    private ListDataBaseAdapter mLvMainAdapter;

    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("Android相关测试", AndroidTestActivity.class),
            new ActivityListItemBean("java相关测试", JavaTestActivity.class),
            new ActivityListItemBean("算法测试", AlgorithmActivity.class),
            new ActivityListItemBean("设计模式", DesignPatternActivity.class),
            new ActivityListItemBean("动画", AnimationMainActivity.class)
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
        mLvMainAdapter = new ActivityListAdapter();
        // 数据初始化
        List<Object> datas = new ArrayList<>();
        for (ActivityListItemBean data : mAllItemBeans) {
            datas.add(data);
        }
        mLvMainAdapter.setData(datas);
        mLvMain.setAdapter(mLvMainAdapter);
        mLvMain.setOnItemClickListener(mOnItemClickListener);

        mLvMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.log("onItemSelected的position为：" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                LogUtils.log("onItemSelected的positio");
            }
        });
    }

    /**
     * ListView的点击监听
     */
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtils.log("onItemClick为：" + position);
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
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
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
