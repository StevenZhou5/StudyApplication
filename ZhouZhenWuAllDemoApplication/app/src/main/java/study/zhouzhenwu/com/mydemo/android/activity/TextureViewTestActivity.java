package study.zhouzhenwu.com.mydemo.android.activity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.widget.FrameLayout;

import java.io.IOException;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * Created by Steven on 2018/5/10.
 */

public class TextureViewTestActivity extends BaseActivity implements TextureView.SurfaceTextureListener {
    private TextureView mTextureView;
    private Camera mCamera;

    private float mCurrentRotation = 0;
    private float mCurrentScaleXY = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture_view_test);

        mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);
        setContentView(mTextureView);

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Log.d("ZZW", "onSurfaceTextureAvailable");
        mCamera = Camera.open();
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        mTextureView.setLayoutParams(new FrameLayout.LayoutParams(previewSize.width, previewSize.height, Gravity.CENTER));
        try {
            mCamera.setPreviewTexture(surfaceTexture);
        } catch (IOException e) {
        }
        mCamera.startPreview();
        mTextureView.setAlpha(0.8f);
        mTextureView.setRotation(mCurrentRotation);
        mTextureView.setScaleX(mCurrentScaleXY);
        mTextureView.setScaleY(mCurrentScaleXY);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
        Log.d("ZZW", "onSurfaceTextureSizeChanged");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Log.d("ZZW", "onSurfaceTextureDestroyed");
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    boolean isIncrease = true;

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        Log.d("ZZW", "onSurfaceTextureUpdated");
        mCurrentRotation += 1;

        if (mCurrentScaleXY >= 1) {
            isIncrease = false;
        } else if (mCurrentScaleXY <= 0) {
            isIncrease = true;
        }
        if (isIncrease) {
            mCurrentScaleXY += 0.01f;
        } else {
            mCurrentScaleXY -= 0.01;
        }
        mTextureView.setRotation(mCurrentRotation);
        mTextureView.setScaleX(mCurrentScaleXY);
        mTextureView.setScaleY(mCurrentScaleXY);
    }
}
