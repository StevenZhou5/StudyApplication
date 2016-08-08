package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/2/28
 * 类简介：
 */
public class BaseActivity extends AppCompatActivity {

    public void showToast(String string) {
        Toast.makeText(this.getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }
}
