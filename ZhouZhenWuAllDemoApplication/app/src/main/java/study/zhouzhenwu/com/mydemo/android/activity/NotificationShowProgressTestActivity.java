package study.zhouzhenwu.com.mydemo.android.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 在通知栏展示下载进度的测试
 * Created by Zhouzhenwu/Steven on 2018/4/3.
 */

public class NotificationShowProgressTestActivity extends BaseActivity {
    TextView tvDownload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_show_progress);
        tvDownload = (TextView) findViewById(R.id.tv_download);

        tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownload();
            }
        });
    }

    //定义notification实用的ID
    private static final int NOTIFY_ID = 0x3;

    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    private void startDownload() {
        manager = (NotificationManager) getSystemService(Activity.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.mipmap.ic_my_launcher);
        builder.setContentTitle("下载");
        builder.setContentText("正在下载");
        builder.setProgress(100, 0, false);

        manager.notify(NOTIFY_ID, builder.build());
        //下载以及安装线程模拟
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    builder.setProgress(100, i, false);
                    manager.notify(NOTIFY_ID, builder.build());
                    //下载进度提示
                    builder.setContentText("下载" + i + "%");
                    try {
                        Thread.sleep(50);//演示休眠50毫秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //下载完成后更改标题以及提示信息
                builder.setContentTitle("开始安装");
                builder.setContentText("安装中...");
                //设置进度为不确定，用于模拟安装
                builder.setProgress(0, 0, true);
                manager.notify(NOTIFY_ID, builder.build());
                //                manager.cancel(NOTIFY_ID);//设置关闭通知栏
            }
        }).start();
    }

}
