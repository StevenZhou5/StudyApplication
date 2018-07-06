package study.zhouzhenwu.com.mydemo.common.weixin;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Zhouzhenwu/Steven on 2018/7/6.
 */

public class WeiXinTest {
    private static final String APP_ID = "wx666888";

    private IWXAPI iwxapi;

    private void regToWx(Context context){
        iwxapi = WXAPIFactory.createWXAPI(context,APP_ID,true);

        iwxapi.registerApp(APP_ID);

    }
}
