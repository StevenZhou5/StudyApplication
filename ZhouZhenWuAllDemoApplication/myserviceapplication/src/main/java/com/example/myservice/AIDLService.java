package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class AIDLService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    private final IUserInfoController.Stub stub = new IUserInfoController.Stub() {
        @Override
        public void changeUserName(UserInfo userInfo) throws RemoteException {
            Log.d("ZZW","服务端changeUserName取得的UserName："+userInfo.getUserName());
            userInfo.setUserName("服务器修改了UserInfo的名字");
        }
        @Override
        public void changeUserName2(UserInfo userInfo) throws RemoteException {
            Log.d("ZZW","服务端changeUserName2取得的UserName："+userInfo.getUserName());
            userInfo.setUserName("服务器修改了UserInfo的名字");
        }
    };
}
