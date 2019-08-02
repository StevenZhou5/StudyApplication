package study.zhouzhenwu.com.mydemo.java.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2019-08-02
 * 类简介：测试java中各个基本数据类型的长度
 */
public class JavaByteLengthTestActivity extends BaseActivity {
    private TextView mTvContent;

    private short mShort = 1;
    private int mInt = 1;
    private long mLong = 1;
    private float mFloat = 1;
    private double mDouble = 1;
    private char mChar = 'a';
    private boolean mBoolean = true;
    private byte mByte = 1;
    private String mString1 = "hello";
    private String mString2 = "你好aaa";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_byte_length_test);

        mTvContent = (TextView) findViewById(R.id.tv_content);
        try {
            String strResult = getString(R.string.java_byte_length);
            strResult += "\t\thello在utf-8编码下所占的字节数:" + mString1.getBytes("utf-8").length + "\n";
            strResult += "\t\thello在gbk编码下所占的字节数:" + mString1.getBytes("gbk").length + "\n";

            strResult += "\t\t你好aaa在utf-8编码下所占的字节数:" + mString2.getBytes("utf-8").length + "\n";
            strResult += "\t\t你好aaa在gbk编码下所占的字节数:" + mString2.getBytes("gbk").length + "\n";

            mTvContent.setText(Html.fromHtml(strResult.replace("\n", "<br>")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
