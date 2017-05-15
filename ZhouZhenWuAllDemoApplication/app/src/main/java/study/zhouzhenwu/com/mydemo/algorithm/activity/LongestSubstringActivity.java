package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.StringUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/5/11
 * 类简介： 查找最长不重复子串算法
 */

public class LongestSubstringActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.bt_test)
    Button mBtTest;

    @Bind(R.id.tv_input)
    TextView mTvInput;
    @Bind(R.id.tv_output)
    TextView mTvOutput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longest_substring);
        ButterKnife.bind(this);

        mBtTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_test:
                test();
                break;
            default:
                break;
        }
    }

    private void test() {
        String inputString = StringUtils.RandomString(10);
        mTvInput.setText("输入：" + inputString);
        int length = lengthOfLongestSubstring(inputString);

        mTvOutput.setText("输出：最大长度为：" + length + "; 最长子串为：" + inputString.substring(resultStart, resultStart + length));
    }

    int resultStart = 0;

    private int lengthOfLongestSubstring(String s) {

        int maxLength = 1; // 最大长度
        int currentStart = 0; // 开始位置

        // 如果当前开始位置没有超过字符串长度（说明还没有遍历完），
        // 并且从当前开始位置到末尾所形成的字符串长度大于目前最大长度（因为如果小于最大长度，那么及时后面的所有字符都不重复，最长子串也不是他，所以没有必要继续判断）
        while (currentStart < s.length() && (s.length() - currentStart) > maxLength) {
            int currentLength = lengthOfLongestSubstringFromStartIndex(s, currentStart);
            if (currentLength > maxLength) {
                maxLength = currentLength;

                resultStart = currentStart;
            }
            currentStart++;
        }
        return maxLength;
    }

    private int lengthOfLongestSubstringFromStartIndex(String s, int startIndex) {
        return lengthOfSubString(s, startIndex, startIndex + 1);
    }

    private int lengthOfSubString(String s, int start, int end) {
        if (start + end > s.length() - 1) { // 如果end已经是最后一个字符的位置了，返回start到end的长度
            return end - start + 1;
        }

        // 如果end不是最后一个字符的位置
        String subString = s.substring(start, end); // 拿到当前截取的字符串;这里的end是从start开始，向后取几个，不是sunString的index
        char nextChar = s.charAt(end );  // 取出下一个字符
        String nextString = String.valueOf(nextChar); // 将下一个字符转换字符串
        log("当前：start:" + start + "； end：" + end + "; subString：" + subString + "；nextChar：" + nextChar);
        if (subString.contains(nextString)) { // 如果当前字符串已经包含此字符串，那么return当前字符串长度
            log("当前：" + subString + "的length为：" + subString.length());
            return subString.length();
        }

        // 当前字符串没有包含下一个字符串
        return lengthOfSubString(s, start, end + 1); // end+1后递归
    }

}
