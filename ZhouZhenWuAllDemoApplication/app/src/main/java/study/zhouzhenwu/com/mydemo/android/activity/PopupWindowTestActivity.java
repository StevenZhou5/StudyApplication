package study.zhouzhenwu.com.mydemo.android.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;
import study.zhouzhenwu.com.mydemo.common.widgets.LoadingPopupWindow;
import study.zhouzhenwu.com.mydemo.common.widgets.calendar.CalendarDataBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/2/27
 * 类简介：
 */

public class PopupWindowTestActivity extends BaseActivity {

    @Bind(R.id.layout_pop_container)
    View mPopContainer;

    @Bind(R.id.view_background)
    View mViewBackground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);

        Button button = new Button(this);
        ViewGroup.LayoutParams buttonLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(buttonLayoutParams);
        linearLayout.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidTest(v);
            }
        });
        button.setText("开始相关测试");
        setContentView(linearLayout);
    }

    /**
     * 测试按钮被点击
     *
     * @param view
     */
    public void androidTest(View view) {
//        widgetPopupWindowTest(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

        List list = new ArrayList<>();
        list.add(1);
        int[] ints = new int[5];
        int length = ints.length;
        HashSet<CalendarDataBean> set = new HashSet<>();
        set.size();
        Map<String ,CalendarDataBean> map = new HashMap<>();
        map.keySet();
        map.values();
//        animationPopupWindowTest(view);
    }


    /**
     * 带动画效果的popupwindow测试
     *
     * @param view
     */
    private void animationPopupWindowTest(View view) {
        // 带有动画效果的popupWindow
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popupwindow_layout, null);
        ButterKnife.bind(this, contentView);
        PopupWindow popup = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popup.setFocusable(true);
        popup.setAnimationStyle(R.style.AppTheme_PopupOverlay);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(view);

        mPopContainer.setVisibility(View.VISIBLE);
        mViewBackground.setVisibility(View.VISIBLE);

        mPopContainer.measure(0, 0);
        LogUtils.log("height:" + mPopContainer.getMeasuredHeight());
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -mPopContainer.getMeasuredHeight(), 0);
        translateAnimation.setDuration(2000);
        mPopContainer.startAnimation(translateAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.1f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        mViewBackground.startAnimation(alphaAnimation);
    }

//    private LoadingPopupWindow loadingPopupWindow;

    private void widgetPopupWindowTest(View view) {
        // 自定义的展示一张图片的Pop
//        OneImageViewPopupWindow oneImageViewPopupWindow = new OneImageViewPopupWindow(this, R.drawable.leak_canary_icon);
//        oneImageViewPopupWindow.showAsDropDown(view);

        // 自定义的展示全屏loading的popupWindow
        LoadingPopupWindow loadingPopupWindow = new LoadingPopupWindow(this);
        loadingPopupWindow.showLoading();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.log(getClass().getSimpleName() + ":onNewIntent");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        log("onActivityResult:" + requestCode + ";" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
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
        LogUtils.log(getClass().getSimpleName() + ":onTrimMemory;" + "; 此时的level:" + level);
    }
}
