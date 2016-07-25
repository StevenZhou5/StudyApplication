package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.utils.CommonUtils;


/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/5/5
 * 类简介：展示一张图片蒙层的popupWindows
 */
public class OneImageViewPopupWindow extends PopupWindow {


    public OneImageViewPopupWindow(Activity context, int resourceId) {
        super(context); // 不加这句的话，showAsDropDown(View anchor)
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popupwindow_one_image, null);
        initImageView(context, resourceId, contentView);
        initPopupWindow(contentView);
    }

    /**
     * 初始化ImageView
     *
     * @param context
     * @param resourceId
     * @param contentView
     */
    private void initImageView(Activity context, int resourceId, View contentView) {
        ImageView imageView = (ImageView) contentView.findViewById(R.id.iv);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.topMargin = CommonUtils.getStatusBarHeight(context);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(resourceId);
        imageView.setFocusable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneImageViewPopupWindow.this.dismiss();
            }
        });
    }


    /**
     * 初始化当前的PopupWindow
     *
     * @param contentView
     */
    private void initPopupWindow(View contentView) {
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);
    }
}
