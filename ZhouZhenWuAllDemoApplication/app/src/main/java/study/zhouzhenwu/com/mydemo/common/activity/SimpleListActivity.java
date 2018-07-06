package study.zhouzhenwu.com.mydemo.common.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import study.zhouzhenwu.com.mydemo.common.adapter.SimpleRecycleViewAdapter;
import study.zhouzhenwu.com.mydemo.common.module.SimpleListItemBean;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.OnRecyclerViewItemClickListener;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/4
 * 类简介：用于展示List列表Activity基类，其中提供的数据中至少要包含Item的名字信息
 */

public abstract class SimpleListActivity<T extends SimpleListItemBean> extends BaseActivity {
    protected RecyclerView mRecyclerView;
    protected SimpleRecycleViewAdapter mAdapter;
    protected List<T> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // step1:recycleView初始化
        mRecyclerView = new RecyclerView(this);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView.setLayoutParams(params);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        // step2:设置ContentView
        setContentView(mRecyclerView);

        // step3:adapter初始化并为recyclerView设置Adapter
        mAdapter = new SimpleRecycleViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // step4:数据初始化，并刷新显示最新数据
        mDatas = arrayToList(getItemBeans());
        mAdapter.addAllItems(mDatas);

        // step5:监听初始化
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(View v, int position) {
                onItemClicked(v, position, mDatas.get(position));
            }
        });
    }

    @NonNull
    protected List<T> arrayToList(T[] array) {
        List<T> datas = new ArrayList<>();
        Observable<T> observable = Observable.from(array);
        observable.subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(T data) {
                datas.add(data);
            }
        });
        return datas;
    }

    /**
     * Item被点击
     *
     * @param v
     * @param position
     * @param item
     */
    protected abstract void onItemClicked(View v, int position, T item);

    /**
     * 获取数据
     *
     * @return
     */
    protected abstract T[] getItemBeans();
}
