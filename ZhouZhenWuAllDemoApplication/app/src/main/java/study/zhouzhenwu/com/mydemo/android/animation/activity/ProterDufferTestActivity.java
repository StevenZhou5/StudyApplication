package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.widgets.PorterDuffXfermodeView;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.BaseRecycleViewViewHolder;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.RecycleViewArrayAdapter;


/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/9/6
 * 类简介：
 */

public class ProterDufferTestActivity extends BaseActivity {
    @Bind(R.id.recycle_view)
    RecyclerView mRecycleView;

    RecycleViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proter_duff_test);
        ButterKnife.bind(this);

        mRecycleView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RecycleViewAdapter();
        mRecycleView.setAdapter(adapter);

        List<TestData> datas = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            TestData data = new TestData();
            data.setPorterDuffMode(intToMode(i));
            datas.add(data);
        }
        adapter.addAllItems(datas);
    }

    public static final PorterDuff.Mode intToMode(int val) {
        switch (val) {
            default:
            case 0:
                return PorterDuff.Mode.CLEAR;
            case 1:
                return PorterDuff.Mode.SRC;
            case 2:
                return PorterDuff.Mode.DST;
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 4:
                return PorterDuff.Mode.DST_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 6:
                return PorterDuff.Mode.DST_IN;
            case 7:
                return PorterDuff.Mode.SRC_OUT;
            case 8:
                return PorterDuff.Mode.DST_OUT;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 10:
                return PorterDuff.Mode.DST_ATOP;
            case 11:
                return PorterDuff.Mode.XOR;
            case 16:
                return PorterDuff.Mode.DARKEN;
            case 17:
                return PorterDuff.Mode.LIGHTEN;
            case 13:
                return PorterDuff.Mode.MULTIPLY;
            case 14:
                return PorterDuff.Mode.SCREEN;
            case 12:
                return PorterDuff.Mode.ADD;
            case 15:
                return PorterDuff.Mode.OVERLAY;
        }
    }


    public class RecycleViewAdapter extends RecycleViewArrayAdapter<TestData> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proter_duff_test, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindBaseViewHolder(BaseRecycleViewViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.onBindData(mItems.get(position));
        }


        class ViewHolder extends BaseRecycleViewViewHolder {
            @Bind(R.id.proter_duff_view)
            PorterDuffXfermodeView porterDuffXfermodeView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void onBindData(TestData mainTestData) {
                porterDuffXfermodeView.setProterDuffMode(mainTestData.porterDuffMode);
            }
        }
    }


    public class TestData {
        private PorterDuff.Mode porterDuffMode = PorterDuff.Mode.MULTIPLY;

        public PorterDuff.Mode getPorterDuffMode() {
            return porterDuffMode;
        }

        public void setPorterDuffMode(PorterDuff.Mode porterDuffMode) {
            this.porterDuffMode = porterDuffMode;
        }
    }
}
