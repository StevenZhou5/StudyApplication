package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.widgets.ClockView;

/**
 * 基于Camera实现的伪3D动画
 */
public class Camera3DAnimationActivity extends BaseActivity {
    private ClockView mClockView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_3d_animation);
        mClockView = (ClockView) findViewById(R.id.clock_view);
        RelativeLayout.LayoutParams rlParams =
                (RelativeLayout.LayoutParams) mClockView.getLayoutParams();
        rlParams.setMargins(-1 * 500, 0, 800, 0);
        mClockView.setLayoutParams(rlParams);


        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        Test3DAnimation animation = new Test3DAnimation(metrics.xdpi / 2, metrics.ydpi / 2, 3500);
        animation.setRepeatCount(-1);
        mClockView.startAnimation(animation);
    }

    class Test3DAnimation extends Animation {
        private float centerX;
        private float centerY;
        //定义动画的持续时间
        private int duration;
        private Camera camera = new Camera();

        public Test3DAnimation(float x, float y, int duration) {
            this.centerX = x;
            this.centerY = y;
            this.duration = duration;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            setDuration(duration);//设置动画的持续时间
            setFillAfter(true); //设置动画结束后效果
            setInterpolator(new LinearInterpolator());
        }

        /**
         * @param interpolatedTime :代表了抽象的动画持续时间，不管动画实际持续多长时间，interpolatedTime参数总是从0（动画开始时）变化到1（动画结束时）
         * @param t                :代表了对目标组件所做的改变
         */
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            camera.save();
            //根据interpolateTime时间来控制X,Y,Z上的偏移
//            camera.translate(100.0f - 100.0f * interpolatedTime, 150.0f * interpolatedTime - 150, 80.0f - 80.0f * interpolatedTime);
//            camera.translate(0,0,80.0f);
//            camera.rotateY(360 * (interpolatedTime));
            //根据interpolateTime时间在X轴上旋转不同角度
//            camera.rotateZ(360 * (interpolatedTime));
            camera.translate(800*(interpolatedTime), 0, 360 * (interpolatedTime));
            //获取Transformation的Matrix参数
            Matrix matrix = t.getMatrix();
            camera.getMatrix(matrix);
            camera.translate(centerX, centerY, 0);
//            matrix.preTranslate(-centerX, -centerY);
//            matrix.postTranslate(centerX, centerY);
            camera.restore();

        }
    }
}
