package study.zhouzhenwu.com.mydemo.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/3/20
 * 类简介： 测试使用layerList的方式来进行背景布局构建
 */

public class LayerListTestActivity extends BaseActivity {
    EditText mEtTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layer_list);
        mEtTest = (EditText) findViewById(R.id.et_test);

        mEtTest.setBackgroundResource(R.drawable.layer_list_edit_text);

    }
}
