package study.zhouzhenwu.com.mydemo.common.widgets.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2016/12/7
 * 类简介：
 */

public abstract class ExpandableRecycleViewAdapter<T> extends RecycleViewArrayAdapter<T> {
    private static final int DEFAULT_TITLE_PARENT_RES_ID = 10000; // 默认title父View的ResId
    private static final int DEFAULT_CONTENT_PARENT_RES_ID = 10001; // 默认Content父ViewPage的ResId

    private Context mContext;

    private List<Long> mVisibleIds; // 根据ItemId记录那些Content要显示

    private int mLimitCount = 1; // 限制展看的个数
    private Map<Long, View> mExpandedViewMaps; // 被展看View的map集合

    private AnimationEndListener mAnimationEndListener;

    /**
     * 默认构造函数
     *
     * @param context
     */
    public ExpandableRecycleViewAdapter(Context context) {
        mContext = context;
        mVisibleIds = new ArrayList<>();

        mAnimationEndListener = getAnimationEndListener();
    }

    /**
     * 设置动画结束的监听
     */
    public AnimationEndListener getAnimationEndListener() {
        AnimationEndListener animationEndListener = new AnimationEndListener() {
            @Override
            public void animationEnd(boolean isExpanding) {
                if (isExpanding) {
                    notifyDataSetChanged();
                }
                onAnimationEnd(isExpanding);
            }
        };
        return animationEndListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parent = new RootView(mContext);
        ViewHolder holder = new ViewHolder(parent);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        // step1：title相关初始化
        View titleView = getTitleView(position, viewHolder.titleView, viewHolder.titleParent);
        if (titleView != viewHolder.titleView) {
            viewHolder.titleParent.removeAllViews();
            viewHolder.titleParent.addView(titleView);
        }
        viewHolder.titleView = titleView;


        // step2：content相关初始化
        View contentView = getContentView(position, viewHolder.contentView, viewHolder.titleParent);
        if (contentView != viewHolder.contentView) {
            viewHolder.contentParent.removeAllViews();
            viewHolder.contentParent.addView(contentView);
        }
        viewHolder.contentView = contentView;

        // step3:点击监听设置
        viewHolder.titleParent.setOnClickListener(new TouchViewOnClickListener(viewHolder.contentParent, mAnimationEndListener)); // 设置titleParent为出发点击的View

        // step4:View显示初始化
        long itemId = getItemId(position);
        boolean isShowContent = mVisibleIds.contains(itemId);
        viewHolder.contentParent.setVisibility(isShowContent ? View.VISIBLE : View.GONE);
        viewHolder.contentParent.setTag(itemId);
    }

    /**
     * 由子类去实现的TitleView的具体View内容
     *
     * @param position
     * @param titleView
     * @param titleParent
     * @return
     */
    public abstract View getTitleView(int position, View titleView, ViewGroup titleParent);

    /**
     * 由子类去实现的ContentView的具体View内容
     *
     * @param position
     * @param contentView
     * @param contentParent
     * @return
     */
    public abstract View getContentView(int position, View contentView, ViewGroup contentParent);

    /**
     * 展看或者关闭动画结束后要做的操作
     *
     * @param isExpanding
     */
    public abstract void onAnimationEnd(boolean isExpanding);

    /**
     * itemView 的rootView是一个垂直布局的LinearLayout
     */
    private static class RootView extends LinearLayout {

        private ViewGroup mTitleViewGroup;
        private ViewGroup mContentViewGroup;

        public RootView(Context context) {
            super(context);
            init();
        }

        private void init() {
            setOrientation(VERTICAL);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(params);

            mTitleViewGroup = new FrameLayout(getContext());
            mTitleViewGroup.setId(DEFAULT_TITLE_PARENT_RES_ID);
            addView(mTitleViewGroup);

            mContentViewGroup = new FrameLayout(getContext());
            mContentViewGroup.setId(DEFAULT_CONTENT_PARENT_RES_ID);
            addView(mContentViewGroup);
        }
    }


    /**
     * 内部ViewHolder类
     */
    class ViewHolder extends BaseViewHolder {
        ViewGroup titleParent;
        ViewGroup contentParent;
        View titleView;
        View contentView;

        public ViewHolder(View itemView) {
            super(itemView);
            RootView rootView = (RootView) itemView;
            titleParent = rootView.mTitleViewGroup;
            contentParent = rootView.mContentViewGroup;
        }
    }


    /**
     * 出发点击事件View的点击监听
     */
    private class TouchViewOnClickListener implements View.OnClickListener {

        private View mContentParent;
        private AnimationEndListener mListener;

        private TouchViewOnClickListener(View contentParent, AnimationEndListener listener) {
            this.mContentParent = contentParent;
            this.mListener = listener;
        }

        @Override
        public void onClick(View view) {
            boolean isVisible = mContentParent.getVisibility() == View.VISIBLE; // 通过Content是否可见要对应做展开或者关闭动画
            if (!isVisible && mLimitCount > 0 && mVisibleIds.size() >= mLimitCount) {
                mVisibleIds.remove(0);
            }

            if (isVisible && mVisibleIds.contains(mContentParent.getTag())) {
                ExpandCollapseHelper.animateCollapsing(mContentParent, mListener);
                mVisibleIds.remove(mContentParent.getTag());
                return;
            }

            if (!isVisible && !mVisibleIds.contains(mContentParent)) {
                ExpandCollapseHelper.animateExpanding(mContentParent, mListener);
                mVisibleIds.add((Long) mContentParent.getTag());
            }
        }
    }

    /**
     * 用于做展看和关闭动画的帮助类
     */
    private static class ExpandCollapseHelper {

        /**
         * 关闭动画
         *
         * @param view
         */
        public static void animateCollapsing(final View view, final AnimationEndListener animationEndListener) {
            int origHeight = view.getHeight();

            ValueAnimator animator = createHeightAnimator(view, origHeight, 0);
            animator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animator) {
                    view.setVisibility(View.GONE); // 关闭动画结束后要设置View消失

                    // 因为View的复用机制，在属性动画结束后View的真实高度其实为零了，那么在复用View的时候那个明明是展看的View但是它的高度是为零的，会导显示异常，所以这里在动画结束后一定要重新恢复ViewPage的高度为原来的真实高度
                    final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    view.measure(widthSpec, heightSpec);
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = view.getMeasuredHeight();
                    view.setLayoutParams(layoutParams);

                    // 监听回调
                    if (animationEndListener != null) {
                        animationEndListener.animationEnd(false);
                    }
                }
            });
            animator.start();
        }

        /**
         * 展看动画
         *
         * @param view
         */
        public static void animateExpanding(final View view, final AnimationEndListener animationEndListener) {
            view.setVisibility(View.VISIBLE);

            final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(widthSpec, heightSpec);

            ValueAnimator animator = createHeightAnimator(view, 0, view.getMeasuredHeight());
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (animationEndListener != null) {
                        animationEndListener.animationEnd(true);
                    }
                }
            });
            animator.start();
        }

        /**
         * 创建属性动画的ValueAnimator
         *
         * @param view  要做动画的View
         * @param start 动画开始的值
         * @param end   动画结束时的值
         * @return
         */
        public static ValueAnimator createHeightAnimator(final View view, int start, int end) {
            ValueAnimator animator = ValueAnimator.ofInt(start, end);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (Integer) valueAnimator.getAnimatedValue();

                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = value;
                    view.setLayoutParams(layoutParams);
                }
            });
            return animator;
        }
    }

    /**
     * 展看关闭动画结束监听
     */
    public interface AnimationEndListener {
        /**
         * 动画结束回调
         *
         * @param isExpanding:是否是展看动画
         */
        void animationEnd(boolean isExpanding);
    }
}
