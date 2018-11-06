package study.zhouzhenwu.com.mydemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Set;

import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

public class ThreeDoorsActivity extends BaseActivity {
    private EditText mEtInput;
    private TextView mTvNoChange;
    private TextView mTvChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_door);
        mEtInput = (EditText) findViewById(R.id.et_input);
        mTvNoChange = (TextView) findViewById(R.id.tv_no_change);
        mTvChange = (TextView) findViewById(R.id.tv_change);

        mTvNoChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noChange();
            }
        });

        mTvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
        Uri uri = Uri.parse(Uri.decode("mv://VideoLong?url=entity/V008356873&_lp={\"path\":\"电视剧\"}&position=op-tv-recom|cover-rlqMt1EL|carousel||2"));
        Set<String> params = uri.getQueryParameterNames();
        params.size();
    }

    private void noChange() {
        try {
            int count = Integer.parseInt(String.valueOf(mEtInput.getText())); // 模拟次数
            int successCount = 0; //成功次数
            for (int i = 0; i < count; i++) {
                int prizePosition = ((int) (Math.random() * 1000)) % 3; // 奖品位置
                int firstChoice = ((int) (Math.random() * 1000)) % 3; // 第一次选择位置
                if (firstChoice == prizePosition) {
                    successCount++;
                }
            }
            mTvNoChange.setText("不换门成功次数:" + successCount);
        } catch (Exception e) {

        }
    }

    private void change() {
        try {
            int count = Integer.parseInt(String.valueOf(mEtInput.getText())); // 模拟次数
            int successCount = 0; //成功次数
            for (int i = 0; i < count; i++) {
                int prizePosition = ((int) (Math.random() * 1000)) % 3; // 奖品位置
                int firstChoice = ((int) (Math.random() * 1000)) % 3; // 第一次选择位置
                if (firstChoice != prizePosition) {
                    successCount++;
                }
            }
            mTvChange.setText("换门成功次数:" + successCount);
        } catch (Exception e) {

        }
    }

}
