package study.zhouzhenwu.com.mydemo.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.Socket;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * Created by Steven on 2018/4/11.
 */

public class AndroidSocketClientActivity extends BaseActivity {
    EditText mEtInput;

    Button mBtSend;

    TextView mTvReply;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);
        mEtInput = (EditText) findViewById(R.id.et_input);
        mBtSend = (Button) findViewById(R.id.bt_send);
        mTvReply = (TextView) findViewById(R.id.tv_server_reply);

        startClient();
    }

    public void startClient() {
        Socket socket = new Socket();
//        BufferedReader reader = new BufferedReader(socket);
    }

    public void send(){

    }

}
