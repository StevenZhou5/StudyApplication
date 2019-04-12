package study.zhouzhenwu.com.mydemo.designpattern.activity;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：19/4/9
 * 类简介：命令模式测试Activity
 */
public class DesignPatternCommendActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_design_pattern_commend);
        init();
    }

    private void init() {
        // step1:我们需要一个机器人
        Robot robot = new Robot(this);
        robot.setImageResource(R.drawable.ic_robot);
        robot.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        robot.setLayoutParams(params);
        RelativeLayout layoutContent = (RelativeLayout) findViewById(R.id.layout_content);
        layoutContent.addView(robot);

        // step2:我们需要一个遥控器，就是初始化遥控器上的每一个命令
        TurnLeftCommend turnLeftCommend = new TurnLeftCommend(robot);
        TurnRightCommend turnRightCommend = new TurnRightCommend(robot);
        GoStraightCommend goStraightCommend = new GoStraightCommend(robot);

        // step3:我们需要一个人来去按遥控器
        Operator operator = new Operator();
        Button btTurnLeft = (Button) findViewById(R.id.bt_turn_left);
        btTurnLeft.setOnClickListener(v -> {
            // 点击时模拟这个操作者产生点某一个控制器上的按钮，并按下这个按钮的过程
            //step1:执行者产生了要要机器人左转的想法
            operator.setCommend(turnLeftCommend);
            // step2:执行者按下了这个按钮执行了命令
            operator.executeCommend();
        });
        Button btTurnRight = (Button) findViewById(R.id.bt_turn_right);
        btTurnRight.setOnClickListener(v -> {
            // 执行右转命令
            operator.setCommend(turnRightCommend);
            operator.executeCommend();
        });
        Button btGoStraight = (Button) findViewById(R.id.bt_go_straight);
        btGoStraight.setOnClickListener(v -> {
            // 执行执行命令
            operator.setCommend(goStraightCommend);
            operator.executeCommend();
        });

    }

    // ================================= <editor-fold desc="只要有一个遥控器，操作者就可以让机器人按照他的意愿去做事，当然他并不需要关心遥控器是怎么控制机器人运动的，他只需要下命令就行"> ==========================

    /**
     * 操作者，命令的发起者
     */
    public static class Operator {
        private Commend commend;

        public Operator() {

        }

        public void setCommend(Commend commend) {
            this.commend = commend;
        }

        public void executeCommend() {
            commend.action();
        }
    }
    // </editor-fold>

    // ================================= <editor-fold desc="机器人的开发团队只用专心研发机器人的基本功能即可：旋转，跳跃，向前走等"> ==========================

    /**
     * 机器人：命令的执行者:1.0版本我们可以只支持左转，右转，直行功能；2.0版本我们可以将左转右转变成转动多少角度，这样机器人就可以向任意方向直行；3.0版本我们可以增加跳跃功能
     */
    public static class Robot extends android.support.v7.widget.AppCompatImageView implements Animator.AnimatorListener {
        private boolean isAnimationRunning;
        private Orientation mOrientation = Orientation.DOWN;

        public enum Orientation {
            LEFT, // 向左
            RIGHT, // 向右
            UP, // 向上
            DOWN, // 向下
        }

        public Robot(Context context) {
            super(context);
            animate().setListener(this);
        }

        /**
         * 直行
         *
         * @param distance
         */
        public void goStraight(int distance) {
            switch (mOrientation) {
                case LEFT:
                    animate().translationXBy(distance);
                    break;
                case RIGHT:
                    animate().translationXBy(-distance);
                    break;
                case UP:
                    animate().translationYBy(-distance);
                    break;
                case DOWN:
                    animate().translationYBy(distance);
                    break;
            }
        }

        /**
         * 向左转
         */
        public void turnLeft() {
            if (isAnimationRunning) {
                return;
            }
            animate().rotationBy(-90);
        }

        /**
         * 向左转
         */
        public void turnRight() {
            if (isAnimationRunning) {
                return;
            }
            animate().rotationBy(90);
        }


        @Override
        public void onAnimationStart(Animator animation) {
            isAnimationRunning = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            Log.d("ZZW", "onAnimationEnd-Rotation:" + getRotation());
            isAnimationRunning = false;
            int degree = (int) (getRotation() % 360);
            degree = degree >= 0 ? degree : degree + 360;
            switch (degree) {
                case 0:
                    mOrientation = Orientation.DOWN;
                    break;
                case 90:
                    mOrientation = Orientation.RIGHT;
                    break;
                case 180:
                    mOrientation = Orientation.UP;
                    break;
                case 270:
                    mOrientation = Orientation.LEFT;
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            isAnimationRunning = false;
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            isAnimationRunning = true;
        }
    }
    // </editor-fold>

    // ================================= <editor-fold desc="每一条命令都对应一个操作按钮，扩展命令就对应扩展相应的操作按钮,遥控器的开始团队可以完全独立，他们只需要基于机器人现有的能力就能开发出各种不同的遥控器"> ==========================

    /**
     * 抽象的命令
     */
    public static interface Commend {
        void action();
    }

    /**
     * 向左转命令
     */
    public class TurnLeftCommend implements Commend {
        private Robot robot;

        public TurnLeftCommend(Robot robot) {
            this.robot = robot;
        }

        @Override
        public void action() {
            robot.turnLeft();
        }
    }

    /**
     * 向右转命令
     */
    public class TurnRightCommend implements Commend {
        private Robot robot;

        public TurnRightCommend(Robot robot) {
            this.robot = robot;
        }

        @Override
        public void action() {
            robot.turnRight();
        }
    }

    /**
     * 直走命令
     */
    public class GoStraightCommend implements Commend {
        private Robot robot;

        public GoStraightCommend(Robot robot) {
            this.robot = robot;
        }

        @Override
        public void action() {
            robot.goStraight(100);
        }
    }

    // 基于1.0版本的机器人，我们还可以实现掉头命令，后退命令；
    // </editor-fold>

}
