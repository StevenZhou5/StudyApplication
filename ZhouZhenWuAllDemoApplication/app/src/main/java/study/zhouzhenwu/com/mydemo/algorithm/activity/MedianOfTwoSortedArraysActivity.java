package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/17
 * 类简介：
 */

public class MedianOfTwoSortedArraysActivity extends BaseActivity implements View.OnClickListener {

    Button mBtInit;

    Button mBtTest;

    TextView mTvInput;

    TextView mTvOutput;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_median_of_two_sorted_arrays);
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

    private int[] originalInts1;
    private int[] originalInts2;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_init:
                initArrayInts1();
                initArrayInts2();
                mTvInput.setText("有序数组1：" + intsToString(originalInts1) + ";\n" +
                        "有序数组2：" + intsToString(originalInts2) + ";\n");
                break;
            case R.id.bt_test:

                break;
            default:
                break;
        }
    }


    private void initArrayInts1() {
        int length1 = (int) (Math.random() * 20);
        originalInts1 = new int[length1];
        if (originalInts1.length > 0) {
            originalInts1[0] = (int) (Math.random() * 10);
        }
        for (int i = 1; i < originalInts1.length; i++) {
            int DValue = (int) (Math.random() * 10);
            originalInts1[i] = originalInts1[i - 1] + DValue;
        }
    }

    private void initArrayInts2() {
        int length1 = (int) (Math.random() * 20);
        originalInts2 = new int[length1];
        if (originalInts2.length > 0) {
            originalInts2[0] = (int) (Math.random() * 10);
        }
        for (int i = 1; i < originalInts2.length; i++) {
            int DValue = (int) (Math.random() * 10);
            originalInts2[i] = originalInts2[i - 1] + DValue;
        }
    }

    private String intsToString(int[] ints) {
        if (ints == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i : ints) {
            sb.append(i);
        }

        return sb.toString();
    }
}
