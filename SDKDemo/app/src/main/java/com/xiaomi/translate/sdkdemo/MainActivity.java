package com.xiaomi.translate.sdkdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.xiaomi.ai.minmt.Translator;
import com.xiaomi.ai.minmt.TranslatorDebugInfo;
import com.xiaomi.ai.minmt.TranslatorRequest;
import com.xiaomi.ai.minmt.TranslatorResponse;
import com.xiaomi.ai.minmt.common.Language;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSION_WRITE_EXTERNAL_STORAGE_CODE = 123;

    private boolean isZhToEn = true; // 默认是中到英的翻译

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        initView();

        initData();
    }

    // 动态权限申请
    void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_WRITE_EXTERNAL_STORAGE_CODE);
        }
    }

    // ======================================<editor-fold des="翻译相关操作"> ======================================
    private EditText mEtInput;

    private TextView mTvSource;
    private TextView mTvTarget;
    private Button mBtChange;

    private TextView mTvTest;
    private TextView mTvTestResult;

    private TextView mTvTestMoreInfo;
    private TextView mTvTestMoreInfoResult;

    private View mLayoutLoading;

    private void initView() {
        mEtInput = findViewById(R.id.et_input);

        mTvSource = findViewById(R.id.tv_source);
        mTvTarget = findViewById(R.id.tv_target);
        mBtChange = findViewById(R.id.bt_change);
        mBtChange.setOnClickListener(this);

        mTvTest = findViewById(R.id.tv_test);
        mTvTest.setOnClickListener(this);
        mTvTestResult = findViewById(R.id.tv_test_result);

        mTvTestMoreInfo = findViewById(R.id.tv_test_more_info);
        mTvTestMoreInfo.setOnClickListener(this);
        mTvTestMoreInfoResult = findViewById(R.id.tv_test_more_info_result);

        mLayoutLoading = findViewById(R.id.layout_loading);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_change: // 交换源语言和目标语言
                isZhToEn = !isZhToEn;
                if (isZhToEn) {
                    mTvSource.setText("中文");
                    mTvTarget.setText("English");
                } else {
                    mTvSource.setText("English");
                    mTvTarget.setText("中文");
                }
            case R.id.tv_test:
                test();
                break;
            case R.id.tv_test_more_info:
                testMoreInfo();
            default:
                break;
        }
    }
    // </editor-fold>

    // ======================================<editor-fold des="将assets下的模型数据复制到SD卡指定的目录下面"> ======================================
    private String assetsDataPath = "model_data"; // assets下中翻英的模型数据
    private String mDataDirectoryPath; // 存放模型的参数文件和缓存db文件

    /**
     * 数据初始化，将assets/model_data下的文件拷贝到指定的SD卡文件目录下面
     */
    private void initData() {
        mDataDirectoryPath = "/storage/emulated/0/Android/data/" + getPackageName();
        mLayoutLoading.setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    copyFilesFromAssets(MainActivity.this, assetsDataPath, mDataDirectoryPath);
                    initTranslate();
                    mLayoutLoading.post(new Runnable() {
                        @Override
                        public void run() {
                            mLayoutLoading.setVisibility(View.GONE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
        thread.start();
    }


    /**
     * 从assets目录中复制整个文件夹内容
     *
     * @param context Context 使用CopyFiles类的Activity
     * @param oldPath String  原文件路径  如：/aa
     * @param newPath String  复制后路径  如：xx:/bb/cc
     */
    public void copyFilesFromAssets(final Context context, final String oldPath, final String newPath) {
        File newFile = new File(newPath);
        if (newFile.exists()) {
            return;
        }
        try {
            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {//如果是目录
                File file = new File(newPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context, oldPath + File.separator + fileName, newPath + File.separator + fileName);
                }
            } else {//如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //如果捕捉到错误则通知UI线程
//            MainActivity.handler.sendEmptyMessage(COPY_FALSE);
        }

    }
    // </editor-fold>


    // ======================================<editor-fold des="翻译相关操作"> ======================================
    private Translator mZhToEnTranslator;
    private Translator mEnToZhTranslator;
    private int mEngineThreadNum = 2; // 翻译器Engine的线程数目

    private boolean isZhToEnEngineAvailable = false; // 翻译器Engine是否创建成功
    private boolean isEnToZhEngineAvailable = false; // 翻译器Engine是否创建成功

    private void initTranslate() {
        // 初始化Translator
        // 四个参数分别为:参数1:Android中的Context；参数2：源语言：Language.ZH；参数3：目标语言： Language.EN；参数4：模型参数和缓存DB在Android系统中的绝对路径：mDataDirectoryPath；
        mZhToEnTranslator = new Translator(this.getApplicationContext(), Language.ZH, Language.EN, mDataDirectoryPath);

        // 创建Engine;参数1：mEngineThreadNum:翻译器Engine的线程数目
        isZhToEnEngineAvailable = mZhToEnTranslator.createEngine(mEngineThreadNum);


        mEnToZhTranslator = new Translator(this.getApplicationContext(), Language.EN, Language.ZH, mDataDirectoryPath);
        isEnToZhEngineAvailable = mEnToZhTranslator.createEngine(mEngineThreadNum);
    }

    /**
     * 只要翻译结果的简单测试
     */
    private void test() {
        if (mZhToEnTranslator == null || !isZhToEnEngineAvailable) {
            return;
        }
        String input = mEtInput.getText().toString();
        // 参数一：input：需要翻译的源语言的字符串；参数2：false(不使用)：是否需要使用缓存(创建Translator指定的文件路径下必须包含缓存文件DB)
        // 缓存数据属于商业数据，demo示例中暂不提供缓存数据，如需使用请接正式版本SDK
        String result = "";
        if (isZhToEn) {
            result = mZhToEnTranslator.translate(input, false);
        } else {
            result = mEnToZhTranslator.translate(input, false);
        }
        mTvTestResult.setText(result);
    }


    /**
     * 需要详细的翻译信息测试
     */
    private void testMoreInfo() {
        if (mZhToEnTranslator == null || !isZhToEnEngineAvailable) {
            return;
        }
        String input = mEtInput.getText().toString();

        // 创建翻译请求所需要的参数实体类TranslatorRequest
        TranslatorRequest translatorRequest = new TranslatorRequest();
        translatorRequest.setSourceText(input); // 设置源语言字符串
//        translatorRequest.setUseCaches(true); // 是否需要使用缓存
//        translatorRequest.setUsePunctuation(true); // 是否启动标点符号处理逻辑:删除不合适位置的的标点符号，在合适的位置添加合适的标点符号


        String finalResultStr = "";
        // 发起翻译请求并返回翻译结果数据
        TranslatorResponse translatorResponse = mZhToEnTranslator.translate(translatorRequest);
        // 翻译返回的目标语言结果字符串
        String resultStr = translatorResponse.getTargetText();
        finalResultStr += "翻译结果：\n" + resultStr + "；\n\n";

        // 发起翻译请求的请求参数信息
        TranslatorRequest requestInfo = translatorResponse.getRequest();
        String requestSourceText = requestInfo.getSourceText(); // 源语言字符串
        boolean requestIsUseCaches = requestInfo.isUseCaches(); // 请求参数是否使用缓存
        boolean requestIsUsePunctuation = requestInfo.isUsePunctuation(); // 是否使用符号处理
        finalResultStr += "请求参数信息:\n" + "源语言:" + requestSourceText + ";\n" +
                "是否使用缓存:" + requestIsUseCaches + ";\n" + "是否是用符号处理:" + requestIsUsePunctuation + ";\n\n";

        // 是否命中缓存
        boolean isHitCaches = translatorResponse.isHitCaches();
        finalResultStr += "是否命中缓存:" + isHitCaches + ";\n\n";

        // 请求返回的DebugInfo
        TranslatorDebugInfo debugInfo = translatorResponse.getDebugInfo();
        if (debugInfo != null) { // 当命中缓存的时候是不会反悔debugInfo的
            String puncedSourceStr = debugInfo.getPuncedSourceText(); // 标点处理后的源语言字符串
            String taggedSourceStr = debugInfo.getTaggedSourceText(); // tag处理后的源语言字符串
            String afterPreProcessedSourceStr = debugInfo.getAfterPreProcessedSourceText(); // 前处理后的源语言字符串
            String beforePostProcessedTargetStr = debugInfo.getBeforePostProcessedTargetText(); // 后处理前的目标语言字符串
            boolean isTruncated = debugInfo.isTruncated(); // 超过100个词会进行截断
            finalResultStr += "用于调试的debug信息:\n" + "标点处理后:" + puncedSourceStr + ";\n" + "tag处理后:" + taggedSourceStr + ";\n" +
                    "前处理后:" + afterPreProcessedSourceStr + ";\n" + "后处理前:" + beforePostProcessedTargetStr + ";\n" +
                    "是否截断:" + isTruncated + ";\n";
        }

        mTvTestMoreInfoResult.setText(finalResultStr);
    }
    // </editor-fold>


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在onDestroy的时候将翻译器mTranslator资源释放
        if (mZhToEnTranslator != null) {
            mZhToEnTranslator.freeEngine();
            mZhToEnTranslator = null;
        }

        if (mEnToZhTranslator != null) {
            mEnToZhTranslator.freeEngine();
            mEnToZhTranslator = null;
        }
    }
}
