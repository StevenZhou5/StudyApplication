package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.android.animation.adapter.MyRecycleViewAdapter;
import study.zhouzhenwu.com.mydemo.android.animation.model.MyRecycleViewItemModel;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.ScreenUtils;
import study.zhouzhenwu.com.mydemo.common.widgets.recycleview.OnRecyclerViewItemClickListener;
import tv.cjump.jni.DeviceUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/29
 * 类简介：测试RecycleView使用的Activity
 */
public class RecycleViewActivity extends BaseActivity {
    private ViewGroup mLayoutContent;
    RecyclerView mRecycleView;
    private MyRecycleViewAdapter mRecycleViewAdapter;
    private List<MyRecycleViewItemModel> mListDatas;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        mLayoutContent = (ViewGroup) findViewById(R.id.layout_content);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);

        StringBuilder sb = new StringBuilder();
        sb.append(5);
        sb.append(" ");


        mRecycleView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(layoutManager);

        mRecycleViewAdapter = new MyRecycleViewAdapter();
        mRecycleViewAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(View v, int position) {
                showToast(position + "被点击");
                mRecycleViewAdapter.setSelectedPosition(position);
            }
        });
        mRecycleView.setAdapter(mRecycleViewAdapter);

        mListDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mListDatas.add(new MyRecycleViewItemModel());
        }
        mRecycleViewAdapter.addAllItems(mListDatas);
        mRecycleView.scrollToPosition(10); // 滑动到第几个位置

        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("ZZW", "newState:" + newState);
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    View lastChildView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    int lastItem = recyclerView.getChildLayoutPosition(lastChildView);
                    Log.d("ZZW", "最后一条可见Item是：" + lastItem);
                    if (lastItem == mRecycleViewAdapter.getItemCount() - 1) {
                        Log.d("ZZW", "lastChildView:getLeft=" + lastChildView.getLeft() +
                                "; recyclerView.getWidth:" + recyclerView.getWidth());
                        recyclerView.smoothScrollBy(-(recyclerView.getWidth() - lastChildView.getLeft()), 0);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View lastChildView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                int lastItem = recyclerView.getChildLayoutPosition(lastChildView);
                Log.d("ZZW", "onScrolled最后一条可见Item是：" + lastItem);
                if (lastItem == mRecycleViewAdapter.getItemCount() - 1) {
                    Log.d("ZZW", "lastChildView:getLeft=" + lastChildView.getLeft() +
                            "; recyclerView.getWidth:" + recyclerView.getWidth());
                    if (lastChildView instanceof RelativeLayout) {
                        TextView textView = (TextView) ((RelativeLayout) lastChildView).getChildAt(0);
                        textView.setTextColor(getResources().getColor(R.color.red));
                        int width = Math.abs(recyclerView.getWidth() - lastChildView.getLeft());
                        Log.d("ZZW", "width：" + width);
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                        layoutParams.leftMargin = 0;
                        layoutParams.width = width;
                        textView.setLayoutParams(layoutParams);
                        lastChildView.getLayoutParams().width = 300;
                    }
                }
            }
        });

        // 设置滑动监听，用于处理滑动查看更多的动画效果处理
//        mRecyclerViewVideoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
//                    // step1：找到最后一个可见Item的位置
//                    View lastChildView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
//                    int lastItem = recyclerView.getChildLayoutPosition(lastChildView);
//                    // step2:如果最后一个可见Item就是列表的最后一个Item，且这个Item是产看更多Item,那么进行计算
//                    if (lastItem == mVideoListAdapter.getItemCount() - 1
//                            && mVideoListAdapter.getItemViewType(lastItem) == VideoListAdapter.VIEW_TYPE_MORE_LINK) {
//                        if (isAutoScrollBack) {
//                            // 打开最后最后一条查看更多Item的target
//                            TinyCardEntity tmp = mVideoListAdapter.getItem(mVideoListAdapter.getItemCount()-1);
//                            CUtils.getInstance().openLink(mContext, tmp.getTarget(), tmp.getTargetAddition(), null, null, 0);
//                            isAutoScrollBack = false;
//                        }
//
//                        // step3: 如果最后一条Item展示出来的宽度大于了产看更多Item的最小可见宽度时，需要回滑到最小可见宽度
//                        int showWidth = (recyclerView.getWidth() - lastChildView.getLeft()); // Item展示出来的宽度
//                        if (showWidth > mMoreLinkItemMinWidth) {
//                            recyclerView.smoothScrollBy(-(showWidth - mMoreLinkItemMinWidth), 0);
//                            isAutoScrollBack = true;
//                        }
//
//                    }
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                View lastChildView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
//                int lastItem = recyclerView.getChildLayoutPosition(lastChildView);
//                if (lastItem != mVideoListAdapter.getItemCount() - 1
//                        || mVideoListAdapter.getItemViewType(lastItem) != VideoListAdapter.VIEW_TYPE_MORE_LINK) {
//                    return;
//                }
//                // step1: 如果查看更多Item可见时进行相关icon图标的大小及位置动态设置
//                if (!(lastChildView instanceof RelativeLayout)) {
//                    return;
//                }
//                // step2：计算出item可见的总宽度
//                View childView = ((RelativeLayout) lastChildView).getChildAt(0);
//                int width = Math.abs(recyclerView.getWidth() - lastChildView.getLeft());
//
//                // 计算出图片应该有的宽度:可见宽度减去图片左右两边应该有的margin
//                int imgShouldWidth = width - mMoreLinkItemImgLeftAndRightMargin;
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) childView.getLayoutParams();
//                if (imgShouldWidth < mMoreLinkItemImgMinWidth) { // 图片应该有的宽度小于图片应该展示的最小宽度，那么图片设置为最小宽度
//                    layoutParams.width = mMoreLinkItemImgMinWidth;
//                } else if (imgShouldWidth > mMoreLinkItemImgMaxWidth) { // 图片应该有的宽度大于图片应该展示的最大宽度，那么图片设置为最大宽度
//                    layoutParams.width = mMoreLinkItemImgMaxWidth;
//                } else { // 图片应该有的宽度介于图片应该展示的最小宽度和图片应该展示的最大宽度的之间时，那么图片设置为应该有的宽度
//                    layoutParams.width = imgShouldWidth;
//                }
//                layoutParams.height = layoutParams.width;
//                childView.setLayoutParams(layoutParams);
//                lastChildView.getLayoutParams().width = mMoreLinkItemMaxWidth;
//            }
//        });
    }

    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int left = mRecyclerView.getChildAt(movePosition).getLeft();
                mRecyclerView.smoothScrollBy(-left, 0);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
//            mToPosition = position;
//            mShouldScroll = true;
        }
    }
}
