package test.zhouzhenwu.com.mydemo.common.widgets;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import test.zhouzhenwu.com.mydemo.R;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/7/26
 * 类简介：展示Loading动画的PopupWindow
 */
public class LoadingPopupWindow extends PopupWindow {
    private Activity mActivity;

    private ImageView mIvLoading;
    private TextView mTvLoading;


    private static final long DEFAULT_DURATION = 1000;
    private RotateAnimation mRotateAnimation;

    public LoadingPopupWindow(Activity context) {
        super(context); // 必须加这句话，不然showAsDropDown()这类的方法调用将会失效
        mActivity = context;
        initContentView(context);
        initPopupState();
        initAnimation();
    }

    /**
     * @param context
     */
    private void initContentView(Activity context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contentView = layoutInflater.inflate(R.layout.popup_window_loading_layout, null);
        mIvLoading = (ImageView) contentView.findViewById(R.id.iv_loading);
        mTvLoading = (TextView) contentView.findViewById(R.id.tv_loading);
        setContentView(contentView);
    }

    /**
     * 动画相关的初始化
     */
    private void initAnimation() {
        mIvLoading.measure(0, 0);
        float centerX = mIvLoading.getMeasuredWidth() / 2.0f;
        float centerY = mIvLoading.getMeasuredHeight() / 2.0f;
        mRotateAnimation = new RotateAnimation(0, 360, centerX, centerY);
        mRotateAnimation.setInterpolator(new LinearInterpolator()); // 设置迅速插值器
        mRotateAnimation.setDuration(DEFAULT_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setFillAfter(true);
    }

    /**
     * 初始化一些popupWindow自身相关的一些属性
     */
    private void initPopupState() {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        // 实例化一个ColorDrawable的透明颜色背景颜色
        ColorDrawable dw = new ColorDrawable(0x55000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);
    }

    public void showLoading() {
        showAtLocation(mActivity.getWindow().getDecorView(),
                Gravity.CENTER, 0, 0);
        mIvLoading.startAnimation(mRotateAnimation);
    }

    public void dismissLoading() {
        dismiss();
    }

}
