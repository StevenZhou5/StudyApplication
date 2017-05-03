package study.zhouzhenwu.com.mydemo.common.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import study.zhouzhenwu.com.mydemo.R;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/4/27
 * 类简介：由小View跳转到大View的动画风格的Activity跳转动画
 */

public class ActivityOpenFromSrcViewToTargetViewUtils {

    private static Bitmap mBitmap; // 根据起点区域截图生成的BitMap
    private static View mContentView;
    private static ImageView mImageView; // 最终用来做动画的ImageView
    private static Rect mSrcRect; // 起点View的区域范围
    private static Point mSrcOffset;
    private static Rect mTargetRect; // 终点View的区域范围
    private static Point mTargetOffset; // 终点View的区域范围

    private static int mScreenStatusHeight;
    private static int mStartX;
    private static int mFinalX;
    private static int mStartY;
    private static int mFinalY;
    private static float mStartSacleX;
    private static float mFinalScaleX = (float) 1.0;
    private static float mStartSacleY;
    private static float mFinalScaleY = (float) 1.0;

    public static void startActivity(Activity activity, Intent intent, View srcView) {

        Log.d("ZZW", "标题栏高度为：" + ScreenUtils.getScreenStatusBarHeight(activity));
        mScreenStatusHeight = ScreenUtils.getScreenStatusBarHeight(activity);
        // step1: 进行相关准备工作
        prepare(srcView);

        // step2:启动Activity
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    private static void prepare(View srcView) {
        if (mSrcRect == null) {
            mSrcRect = new Rect();
        }
        if (mSrcOffset == null) {
            mSrcOffset = new Point();
        }
        srcView.getGlobalVisibleRect(mSrcRect, mSrcOffset);
        mStartX = mSrcOffset.x;
        mStartY = mSrcOffset.y;

        srcView.setDrawingCacheEnabled(true);
        mBitmap = srcView.getDrawingCache();
    }

    public static void prepareAnimation(final Activity destActivity, View targetView) {
        if (mTargetRect == null) {
            mTargetRect = new Rect();
        }
        if (mTargetOffset == null) {
            mTargetOffset = new Point();
        }
//        targetView.getGlobalVisibleRect(mTargetRect, mTargetOffset);
        mFinalX = CommonUtils.dip2px(destActivity, 50);
        mFinalY = CommonUtils.dip2px(destActivity, 50) + mScreenStatusHeight;
//        mFinalX = mTargetOffset.x;
//        mFinalY = mTargetOffset.y;

        // 初始化startScale和finalScale
        mStartSacleX = (float) mSrcRect.width() / CommonUtils.dip2px(destActivity, 200);
        mStartSacleY = (float) mSrcRect.height() / CommonUtils.dip2px(destActivity, 180);
//        mStartSacleX = (float) mSrcRect.width() / mTargetRect.width();
//        mStartSacleY = (float) mSrcRect.height() / mTargetRect.height();

        mImageView = createImageView(destActivity);
    }

    private static ImageView createImageView(Activity destActivity) {
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(destActivity.getApplicationContext())
                .inflate(R.layout.window_acitivity_open, null);
        mContentView = layout;
        ImageView imageView = (ImageView) layout.findViewById(R.id.iv);
        Drawable drawable = new BitmapDrawable(mBitmap);
        imageView.setBackground(drawable);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = CommonUtils.dip2px(destActivity.getApplicationContext(), 200);
        layoutParams.height = CommonUtils.dip2px(destActivity.getApplicationContext(), 180);
        imageView.setLayoutParams(layoutParams);


        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.gravity = Gravity.LEFT | Gravity.TOP;
        windowParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        Log.d("ZZW", "windowParams:" + windowParams.x + "," + windowParams.y + "," + windowParams.height + "," + windowParams.width);
//        windowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN | WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE;
        windowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        windowParams.format = PixelFormat.TRANSLUCENT;  // 半透明，这样可以看到后面的Activity
        windowParams.windowAnimations = 0;
        destActivity.getWindowManager().addView(mContentView, windowParams);
        return imageView;
    }


    /**
     * 开始进行动画
     *
     * @param activity
     */
    public static void animate(Activity activity) {
        animate(activity, 500, new DecelerateInterpolator());
    }

    /**
     * 启动动画
     *
     * @param destActivity 目标Activity
     * @param duration     动画时长
     * @param interpolator 动画插值器
     */
    public static void animate(final Activity destActivity, final int duration, final TimeInterpolator interpolator) {
        mImageView.setPivotX(0f); // 设置旋转及缩放的X轴轴心点坐标
        mImageView.setPivotY(0f);
        Animator anim1 = ObjectAnimator.ofFloat(mImageView, View.X, mStartX, mFinalX);
        Animator anim2 = ObjectAnimator.ofFloat(mImageView, View.Y, mStartY, mFinalY);
        Animator anim3 = ObjectAnimator.ofFloat(mImageView, View.SCALE_X, mStartSacleX, mFinalScaleX);
        Animator anim4 = ObjectAnimator.ofFloat(mImageView, View.SCALE_Y, mStartSacleY, mFinalScaleY);
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        if (interpolator != null) {
            animatorSet.setInterpolator(interpolator);
        }
        animatorSet.playTogether(anim1, anim2, anim3, anim4);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                clean(destActivity);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                clean(destActivity);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mImageView.post(new Runnable() {
            @Override
            public void run() {
                animatorSet.start();
            }
        });
    }

    /**
     * 清空图片
     *
     * @param activity
     */
    private static void clean(Activity activity) {
        if (mContentView != null) {
            mContentView.setLayerType(View.LAYER_TYPE_NONE, null);
            try {
                // If we use the regular removeView() we'll get a small UI glitch
                activity.getWindowManager().removeViewImmediate(mContentView);
            } catch (Exception ignored) {
            }
        }

        mBitmap = null;
        mImageView = null;
        mContentView = null;
    }

}
