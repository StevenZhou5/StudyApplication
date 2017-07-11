package study.zhouzhenwu.com.mydemo.android.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.drakeet.multitype.ItemViewBinder;
import study.zhouzhenwu.com.mydemo.R;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/7/10
 * 类简介： 用于MultiType测试的文案Item
 */
public class TestTextViewBinder extends ItemViewBinder<MultiTypeTestText, TestTextViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_test_text, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MultiTypeTestText testText) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
