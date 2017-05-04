package study.zhouzhenwu.com.mydemo.common.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import study.zhouzhenwu.com.mydemo.common.adapter.SimpleRecycleViewAdapter;
import study.zhouzhenwu.com.mydemo.common.utils.ViewUtils;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.OnRecyclerViewItemClickListener;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2016/11/28
 * 类简介：展示的List中的每一个Item对应一个Activity，点击Item跳转到对应的Activity
 */

public abstract class ActivityListActivity extends SimpleListActivity<ActivityListItemBean> {
    @Override
    protected void onItemClicked(View v, int position, ActivityListItemBean item) {
        log("onItemClick为：" + position);
        Rect targetRect = ViewUtils.getViewRectInActivity(v);
        log("当前被点击ItemView的targetRect:" + "left:" + targetRect.left + "; right:" + targetRect.right + "; top:"
                + targetRect.top + "; bottom:" + targetRect.bottom);
        Intent intent = new Intent(ActivityListActivity.this, item.getActivity());
//                ActivityOpenFromSrcViewToTargetViewUtils.startActivity(ActivityListActivity.this, intent, v);
        startActivity(intent);
//                startActivityForResult(intent,-1); // 如果requestCode设置为-1的话，startActivity将不起作用
    }
}
