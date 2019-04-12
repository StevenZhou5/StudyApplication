package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AIDLService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    private final IUserInfoController.Stub stub = new IUserInfoController.Stub() {
        @Override
        public void changeUserName(UserInfo userInfo) throws RemoteException {
            userInfo.setUserName("服务器修改了UserInfo的名字");
        }
    };
}
