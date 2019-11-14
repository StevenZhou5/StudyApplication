package com.example.nonoserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.nonoserver.WebServer.MyWebServer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ZZW";
    private MyWebServer myWebServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (myWebServer == null) {
                myWebServer = new MyWebServer(this);
            }
            if (!myWebServer.isAlive()) {
                myWebServer.start();
                Log.d(TAG, "WebServer started");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "WebServer started failed:" + e.getMessage());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

//        if (myWebServer == null) {
//            return;
//        }
//
//        myWebServer.closeAllConnections();
//        myWebServer = null;
//        Log.d(TAG, "app pause, WebServer close");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myWebServer != null) {
            myWebServer.closeAllConnections();
            myWebServer = null;
        }
        Log.d(TAG, "app Destroy, WebServer Destroy");
    }
}
