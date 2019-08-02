package study.zhouzhenwu.com.mydemo.designpattern.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2019-07-31
 * 类简介： 面向对象设计的基本原则
 */
public class DesignPatternBaseRuleActivity extends BaseActivity {
    private TextView mTvBaseRule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_pattern_base_rule);
        mTvBaseRule = (TextView) findViewById(R.id.tv_base_rule);

        String strBaseRule = getString(R.string.design_pattern_base_rule);
        mTvBaseRule.setText(Html.fromHtml(strBaseRule.replace("\n", "<br>")));
    }
}
