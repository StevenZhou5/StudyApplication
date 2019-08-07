package study.zhouzhenwu.com.mydemo.android.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Browser;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

import com.example.myservice.IUserInfoController;
import com.example.myservice.UserInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 方式1
//                    String searchUrl = "https://m.baidu.com/s?from=1011237m&word={searchTerms}"; // 搜索链接，包含渠道号，需@索绪岑 提供
//                    String query = "伪装者";  // 搜索词
//                    String url = null;   // 真正的搜索链接
//                    url = searchUrl.replace("{searchTerms}", URLEncoder.encode(query, "UTF-8"));
//                    Intent launchUriIntent = new Intent(Intent.ACTION_VIEW);
//                    launchUriIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//                    launchUriIntent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());    // applicationId传包名，这样我们可以统计
//                    launchUriIntent.setData(Uri.parse(url));
//                    launchUriIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(launchUriIntent);

                    // 方式2
//                    Uri data = Uri.parse("mibrowser://home?web_url=https%3A%2F%2Fm.baidu.com%2Fs%3Ffrom%3D1269a%26word%3D%E5%B0%8F%E7%B1%B3&appid=com.xiaomi");
                    Uri data = Uri.parse("mibrowser://home?web_url=http://staging.site.e.mi.com/site/D14B3643B3118425AD8156873FE57DED?version=20190114.212229&page=F96FCFC60A7291F8F3EB7BD923B7EB53&adId=100005216&siteId=0&pdp=pull&id=com.qunar.travelplan&appClientId=2882303761517740913&appSignature=68AQleOIpI9zmNxsqqFKVTS7hZvY93QYJZbVRKAP3qA&nonce=824483718483157531:26084343&ref=DELIVERY_PHONE_VIDEO_1.1.b.20&ext_passback=hmb_Fq_6U1E2L5JRAlUgpDSAya5r9JTeK11d1s08ycPFn5c-fq1CRqlZhwZn1rgWrXm4gu2G0hKeWSOpbA0Bbp9_krwYJCNhrZR9Dc_VO33g9g2REbZsBAnGunJjPA7U2AzqQT0i63ejitWR1Ky1Q6_w2zf--DAq3jMfHzHu1P0I6gOwo_RCX3O_M_X_YXBBqEq8F_w4V8bB3ptFzW8mvqFNiBgxNY_w6uyD5ummUZHTMcr3tDYjZ7I5bn2NptZZjiBKRo7d1dZ-PE6ZaAW46GJgJLERo7kptmIV8NqThDAytg56IwhRbOG_tfUXvXrWwsaJjumSN1sX8dfy7BkVCL8W3d84ZH5xJuTwFzgCaOmz5ebIFphG8RhbSkgiWx-KsrBdIJ0gSoQeTetKK6kDD5-EWu2kNhjF31lBLu3ED6TWlLoE2aGXWchWx3iG6LJ97r-P6hn-Xl5bp_Z9G3VEvZJ39g&triggerId=d911e671-41a7-4aac-8161-5479745c3631");
                    Intent intent = new Intent(Intent.ACTION_VIEW,data);
//                    //保证新启动的APP有单独的堆栈，如果希望新启动的APP和原有APP使用同一个堆栈则去掉该项
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        bindService();
    }

    private IUserInfoController mIUserInfoController;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("ZZW", "onServiceConnected");
            mIUserInfoController = IUserInfoController.Stub.asInterface(service);
            mTvTitle.setOnClickListener(v -> {
                try {
                    mIUserInfoController.changeUserName(userInfo);
                    mIUserInfoController.changeUserName2(userInfo);
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
