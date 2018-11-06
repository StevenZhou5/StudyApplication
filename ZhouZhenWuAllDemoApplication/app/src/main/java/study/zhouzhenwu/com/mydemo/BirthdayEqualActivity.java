package study.zhouzhenwu.com.mydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

public class BirthdayEqualActivity extends BaseActivity {
    private EditText mEtInput;
    private TextView mTvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_birthday_equal);

        mEtInput = (EditText) findViewById(R.id.et_input);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mTvResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c();
            }
        });
    }

    private void c() {
        try {
            int personCount = Integer.parseInt(String.valueOf(mEtInput.getText()));
            double a = 1;
            for (int i = 365; i > 365 - personCount; i--) {
                a = a * i;
            }
            double b = Math.pow(365, personCount);
            double percent = 1 - (a / b);
            mTvResult.setText(percent + "");
        } catch (Exception e) {
        }

    }
}
