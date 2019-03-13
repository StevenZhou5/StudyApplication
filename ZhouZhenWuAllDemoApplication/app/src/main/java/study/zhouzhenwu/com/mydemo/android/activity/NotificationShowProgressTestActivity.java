package study.zhouzhenwu.com.mydemo.android.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.android.broadcastreceiver.NotificationBroadcastReceiver;
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
                showNotification();
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

    boolean isClose;

    private void showNotification() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent intentClose = new Intent(this, NotificationBroadcastReceiver.class);
        intentClose.setAction("close_notification");
        PendingIntent pendingIntentClose = PendingIntent.getBroadcast(this, 0, intentClose, 0);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            //当sdk版本大于26
            String id = "channel_1";
            String description = "143";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(id, description, importance);
//                     channel.enableLights(true); // 通知状态灯
//                     channel.enableVibration(true); // 震动

            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification_remote_views);
            remoteViews.setOnClickPendingIntent(R.id.layout_content, pendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.tv_close, pendingIntentClose);
            remoteViews.setTextViewText(R.id.tv_close, isClose ? "关闭" : "不关闭");
            isClose = !isClose;

            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(this, id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("This is a content title")
                    .setContentText("This is a content text")
                    .setCustomContentView(remoteViews)
                    .setAutoCancel(false)
                    .build();

//            notification.flags |= Notification.FLAG_NO_CLEAR; // 是否可以被清楚按钮清楚掉
            manager.notify(1, notification);
        } else {
            //当sdk版本小于26
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            manager.notify(1, notification);
        }
    }


}
