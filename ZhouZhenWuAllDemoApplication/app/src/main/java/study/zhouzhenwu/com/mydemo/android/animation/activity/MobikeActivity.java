package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.android.animation.model.MobikeTestData;
import study.zhouzhenwu.com.mydemo.android.animation.adapter.MobikeTestRecycleViewAdapter;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import com.example.mylibrary.utils.ToastUtils;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.OnRecyclerViewItemClickListener;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/9/5
 * 类简介：
 */

public class MobikeActivity extends BaseActivity {
    @Bind(R.id.et_input)
    EditText mEtInput;

    @Bind(R.id.bt_search)
    Button mBtSearch;

    @Bind(R.id.recycle_view)
    RecyclerView mRecycleView;

    private MobikeTestRecycleViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mAdapter = new MobikeTestRecycleViewAdapter();
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(View v, int position) {
                MobikeTestData data = mDatas.get(position);
                ToastUtils.showShortToast(getApplicationContext(), data.getName());
            }
        });

        mBtSearch.setOnClickListener(mClickListener);
    }

    private View.OnClickListener mClickListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(mEtInput.getText())){
                ToastUtils.showShortToast(getApplicationContext(),"搜索内容不能为空");
                return;
            }
            requestData();
        }
    };

    private void requestData() {
        List<MobikeTestData> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            MobikeTestData data = new MobikeTestData();
            data.setImageUrl("http://f10.baidu.com/it/u=1026319015,1600389580&fm=72");
            data.setName("图片：" + i);
            datas.add(data);
        }
        refreshData(datas);
    }

    private List<MobikeTestData> mDatas;

    private void refreshData(List<MobikeTestData> datas) {
        mDatas = datas;
        mAdapter.addAllItems(datas);
    }
}
