package study.zhouzhenwu.com.mydemo.android.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/2/21
 * 类简介：用于测试AndroidHandle机制相关的test
 */

public class HandlerTestActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.bt_normal_handler)
    Button mBtNormalHandler;
    @Bind(R.id.bt_handler_thread)
    Button mBtHandlerThread;
    @Bind(R.id.et_delay)
    EditText mEtDelay;
    @Bind(R.id.tv_show_time)
    TextView mTvShowTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test_layout);
        ButterKnife.bind(this);

        initListener();
    }

    private void initListener() {
        mBtNormalHandler.setOnClickListener(this);
        mBtHandlerThread.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_normal_handler:
                handlerNormalTest();

                break;
            case R.id.bt_handler_thread:
                handlerThreadTest();
                break;
            default:
                break;
        }
    }

    /**
     * 线程与handle回调测试;普通线程没有创建looper，如果在这个线程内部创建handler，必须制定一个looper（一般是mainLooper）
     */
    private void handlerNormalTest() {
        showToast("测试开始");
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    this.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final Handler handler = new Handler(Looper.getMainLooper()) { // 必须指定为MainLooper否则不能进行UI操作
                    @Override
                    public void handleMessage(Message msg) {
                        Log.d("ZZW", "handler接收到了消息:" + msg);
                        switch (msg.what) {
                            case 1:
                                showToast("接受到消息");
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


    /**
     * handlerThread与handle回调测试；HandlerThread实现了Looper的创建，并用过同步锁的方式保证在getLooper的时候能够去得到looper
     */
    private void handlerThreadTest() {
        showToast("测试开始");
        HandlerThread thread = new HandlerThread("testHandlerThread");
        thread.start();
        Handler handler = new Handler(thread.getLooper()) { // 必须指定为MainLooper否则不能进行UI操作
            @Override
            public void handleMessage(Message msg) {
                Log.d("ZZW", "handler接收到了消息:" + msg);
                switch (msg.what) {
                    case 1:
                        showToast("接受到消息");
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };

        Message message = new Message();
        message.what = 1;
        handler.sendMessageDelayed(message, 3000);

    }
}
