package study.zhouzhenwu.com.mydemo.android.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

import com.example.myservice.IUserInfoController;
import com.example.myservice.UserInfo;

public class AIDLTestActivity extends BaseActivity {

    private TextView mTvTitle;

    private UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_aidl_test);
        mTvTitle = (TextView) findViewById(R.id.tv_title);

        userInfo = new UserInfo();
        userInfo.setUserName("客户端名称");
        mTvTitle.setText(userInfo.getUserName());
        bindService();
    }

    private IUserInfoController mIUserInfoController;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("ZZW","onServiceConnected");
            mIUserInfoController = IUserInfoController.Stub.asInterface(service);
            mTvTitle.setOnClickListener(v -> {
                try {
                    mIUserInfoController.changeUserName(userInfo);
                    mTvTitle.setText(userInfo.getUserName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.example.myservice");
        intent.setAction("com.example.myservice.action");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
