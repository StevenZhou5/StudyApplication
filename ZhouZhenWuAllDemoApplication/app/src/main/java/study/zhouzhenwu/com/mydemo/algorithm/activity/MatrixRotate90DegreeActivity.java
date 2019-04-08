package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.algorithm.adapter.MatrixIntAdapter;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/9/9
 * 类简介：
 */

public class MatrixRotate90DegreeActivity extends BaseActivity {
    Button mBtInit;

    Button mBtTest;

    RecyclerView mRecycleView;

    private MatrixIntAdapter mAdapter;

    private int mLength;

    private int[][] mMatrix;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_ratate_90_degree);
        mBtInit = (Button) findViewById(R.id.bt_init);
        mBtTest = (Button) findViewById(R.id.bt_test);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);

        mAdapter = new MatrixIntAdapter();
        mRecycleView.setAdapter(mAdapter);

        mBtInit.setOnClickListener(v -> initMatrix());

        mBtTest.setOnClickListener(v -> rotate());

    }

    private void initMatrix() {
        Random random = new Random();
        mLength = random.nextInt(6) + 1;
        mRecycleView.setLayoutManager(new GridLayoutManager(this, mLength));

        mMatrix = new int[mLength][mLength];
        List<Integer> mDatas = new ArrayList<>();
        for (int i = 0; i < mLength; i++) {
            for (int j = 0; j < mLength; j++) {
                mMatrix[i][j] = random.nextInt(100);
                mDatas.add(mMatrix[i][j]);
            }
        }
        mAdapter.clearItems();
        mAdapter.addAllItems(mDatas);
    }

    /**
     * 将矩阵选装90度
     */
    private void rotate() {
        int whileMax = mLength % 2 == 0 ? mLength / 2 : (mLength - 1) / 2; // 最大循环次数
        for (int i = 0; i < whileMax; i++) {
            for (int j = i; j < mLength - i - 1; j++) {
                rotateTarget(i, j);
            }
        }

        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < mLength; i++) {
            for (int j = 0; j < mLength; j++) {
                datas.add(mMatrix[i][j]);
            }
        }
        mAdapter.addAllItems(datas);
    }

    /**
     * 将矩阵中[i][j]位置的数进行旋转一圈涉及到的数字都进行更换
     *
     * @param i
     * @param j
     */
    private void rotateTarget(int i, int j) {

        int index = mMatrix[i][j]; // 先把i，j位置上面的数存储起来

        mMatrix[i][j] = mMatrix[mLength - j - 1][i]; // 将要转到1位置上的数赋值给新的一位置

        mMatrix[mLength - j - 1][i] = mMatrix[mLength - i - 1][mLength - j - 1];

        mMatrix[mLength - i - 1][mLength - j - 1] = mMatrix[j][mLength - i - 1];

        mMatrix[j][mLength - i - 1] = index;
    }
}
