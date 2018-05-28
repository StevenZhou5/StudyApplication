package study.zhouzhenwu.com.mydemo.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.net.Socket;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * Created by Steven on 2018/4/11.
 */

public class AndroidSocketClientActivity extends BaseActivity {
    @Bind(R.id.et_input)
    EditText mEtInput;

    @Bind(R.id.bt_send)
    Button mBtSend;

    @Bind(R.id.tv_server_reply)
    TextView mTvReply;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);
        ButterKnife.bind(this);
        startClient();
    }

    public void startClient() {
        Socket socket = new Socket();
//        BufferedReader reader = new BufferedReader(socket);
    }

    public void send(){

    }

}
