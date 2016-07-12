package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import java.util.HashSet;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/6/16
 * 类简介：
 */
public class AndroidTestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_test);
    }

    /**
     * 测试按钮被点击
     *
     * @param v
     */
    public void androidTest(View v) {
        showToast("AndroidTest开始");
        handlerThreadTest();
    }


    /**
     * 线程与handle回调测试
     */
    private void handlerThreadTest() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    this.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                                showToast("线程休眠结束");
                                break;
                            default:
                                break;
                        }
                        super.handleMessage(msg);
                    }
                };
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        thread.start();
    }
}
