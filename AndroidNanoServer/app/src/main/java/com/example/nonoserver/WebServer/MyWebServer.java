package com.example.nonoserver.WebServer;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

/**
 * NanoHTTPD服务器
 */
public class MyWebServer extends NanoHTTPD {
    private final static int PORT = 33445; // 定义一个端口名称

    private Context mContext;

    /**
     * 主构造函数
     *
     * @param context
     * @throws IOException
     */
    public MyWebServer(Context context) throws IOException {
        super(PORT);
        mContext = context;
        System.out.println("\nRunning! Point your browsers to [http://0.0.0.0:33445/](http://localhost:33445/)\n");
    }

    public MyWebServer(String hostname, int port) {
        super(hostname, port);
    }

    /**
     * 解析的主入口函数，所有请求从这里进，也从这里出
     *
     * @param session
     * @return
     */
    @Override
    public Response serve(IHTTPSession session) {
        Log.d("ZZW", "收到请求:" + session.toString());
        String msg = "<html><body><h1>Hello server</h1>\n";
        msg += "<p>ZZW Server " + session.getUri() + " !</p>";
        return newFixedLengthResponse(msg + "</body></html>\n");
    }
}
