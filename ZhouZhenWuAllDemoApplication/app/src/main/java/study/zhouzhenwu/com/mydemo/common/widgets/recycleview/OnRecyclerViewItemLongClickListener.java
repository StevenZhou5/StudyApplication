package study.zhouzhenwu.com.mydemo.common.widgets.recycleview;

import android.view.View;

/**
 * recycleView的Item长按监听接口（注意：接口必须是public的）
 */
public interface OnRecyclerViewItemLongClickListener {
    void onRecyclerViewItemLongClick(View v, int position);
}
