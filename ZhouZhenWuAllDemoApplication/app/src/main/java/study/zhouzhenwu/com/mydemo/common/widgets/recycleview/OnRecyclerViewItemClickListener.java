package study.zhouzhenwu.com.mydemo.common.widgets.recycleview;

import android.view.View;

/**
 * recycleView的Item点击监听接口（注意：接口必须是public的）
 */
public interface OnRecyclerViewItemClickListener {
    void onRecyclerViewItemClick(View v, int position);
}
