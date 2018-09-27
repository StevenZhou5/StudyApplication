package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.module.SingleLinkedListNode;
import study.zhouzhenwu.com.mydemo.common.utils.SindleLinkedListUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/8
 * 类简介： 单列表操作相关的算法
 */

public class SingleLinkedListsActivity extends BaseActivity implements OnClickListener {
    Button mBtInit;

    Button mBtTest;

    TextView mTvInput;

    TextView mTvOutput;

    private SingleLinkedListNode originalListRoot1; // 原始的List1的根节点
    private SingleLinkedListNode reversedListRoot1; // 反转后的List1的根节点
    private SingleLinkedListNode originalListRoot2; // 原始的List2的根节点
    private SingleLinkedListNode reversedListRoot2; // 反转的List2的根节点

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_linked_list);
        mBtInit = (Button) findViewById(R.id.bt_init);
        mBtTest = (Button) findViewById(R.id.bt_test);
        mTvInput = (TextView) findViewById(R.id.tv_input);
        mTvOutput = (TextView) findViewById(R.id.tv_output);


        initListener();
    }

    private void initListener() {
        mBtInit.setOnClickListener(this);
        mBtTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_init:
                originalListRoot1 = SindleLinkedListUtils.initSingleLinkedLists();
                originalListRoot2 = SindleLinkedListUtils.initSingleLinkedLists();
                String originalString1 = SindleLinkedListUtils.listNodeToString(originalListRoot1);
                String originalString2 = SindleLinkedListUtils.listNodeToString(originalListRoot2);
                mTvInput.setText("原始链表1：" + originalString1 + ";\n" +
                        "原始链表2：" + originalString2 + ";\n");

                log("原始链表1：" + originalString1 + ";\n" +
                        "原始链表2：" + originalString2 + ";\n");
                break;
            case R.id.bt_test:
                reversedListRoot1 = SindleLinkedListUtils.reverse(originalListRoot1);
                reversedListRoot2 = SindleLinkedListUtils.reverse(originalListRoot2);
                String reversedString1 = SindleLinkedListUtils.listNodeToString(reversedListRoot1);
                String reversedString2 = SindleLinkedListUtils.listNodeToString(reversedListRoot2);

                SingleLinkedListNode sumResult = SindleLinkedListUtils.addTwoNumbers(reversedListRoot1, reversedListRoot2);
                String sumString = SindleLinkedListUtils.listNodeToString(sumResult);
                SingleLinkedListNode finalResult = SindleLinkedListUtils.reverse(sumResult);
                String finalString = SindleLinkedListUtils.listNodeToString(finalResult);
                mTvOutput.setText("反转链表1：" + reversedString1 + ";\n" +
                        "反转链表2：" + reversedString2 + ";\n" +
                        "反转加和链表：" + sumString + ";\n" +
                        "最终结果：" + finalString + ";");

                log("反转链表1：" + reversedString1 + ";\n" +
                        "反转链表2：" + reversedString2 + ";\n" +
                        "反转加和链表：" + sumString + ";\n" +
                        "最终结果：" + finalString + ";");
                break;
            default:
                break;
        }

    }
}
