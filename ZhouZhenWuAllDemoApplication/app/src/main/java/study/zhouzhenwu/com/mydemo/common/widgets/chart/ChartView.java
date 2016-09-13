package study.zhouzhenwu.com.mydemo.common.widgets.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import study.zhouzhenwu.com.mydemo.common.utils.CommonUtils;
import study.zhouzhenwu.com.mydemo.common.utils.DrawUtils;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/9/6
 * 类简介：展示评级流程的趋势折线图的自定义View
 */
public class ChartView extends View {
    private static final int DP_DEFAULT_ITEM_WIDTH = 55; // 默认Item的宽度为55Dp：折线图横坐标每一个单元的宽度
    private static final int DP_DEFAULT_ITEM_HEIGHT = 20; // 默认Item的宽度为20Dp:：折线图纵坐标每一个单元的高度
    private static final int DP_DEFAULT_LEFT_PADDING = 5; // 默认整体折线图距离左边的距离
    private static final int DP_DEFAULT_RIGHT_PADDING = 5; // 默认整体折线图距离右边的距离
    private static final int DP_DEFAULT_LEVEL_TEXT_SIZE = 12; // 默认等级文案的字体大小为14sp:"A级"、“B级”、“C级”.....
    private static final int DP_DEFAULT_LEVEL_TEXT_RADIUS = 10; // 默认等级文案的字体大小为14sp:"A级"、“B级”、“C级”.....
    private static final int DP_DEFAULT_LEVEL_TEXT_HALF_WIDTH = 22; // 默认等级文案的字体总宽度的一半
    private static final int DP_DEFAULT_LEVEL_TEXT_HALF_HEIGHT = 10; // 默认等级文案的字体总宽度的一半
    private static final int DP_DEFAULT_LEVEL_TEXT_POINT_PADDING = 10; // 默认等级文案的字体与等级所在坐标点的间距

    @ColorInt
    private static final int COLOR_LEVEL_A = 0xFF52b658; // A级颜色
    @ColorInt
    private static final int COLOR_LEVEL_B = 0xFFb5c00e; // B级颜色
    @ColorInt
    private static final int COLOR_LEVEL_C = 0xFFffc50d; // C级颜色
    @ColorInt
    private static final int COLOR_LEVEL_D = 0xFFff8f2e; // D级颜色
    @ColorInt
    private static final int COLOR_LEVEL_E = 0xFFf35227; // E级颜色
    @ColorInt
    private static final int COLOR_STEP_TEXT = 0xFF666666; // 默认的流程文案字体颜色
    @ColorInt
    private static final int COLOR_SPLIT_LINE = 0xFFebebeb; // 默认的流程文案字体颜色
    @ColorInt
    private static final int COLOR_AREA = 0xFFf5f6f8; // 默认的流程文案字体颜色
    @ColorInt
    private static final int COLOR_SELECTED_AREA = 0x99ffffff; // 默认的流程文案字体颜色


    private static final int DP_DEFAULT_STEP_TEXT_HEIGHT = 30; // 默认流程文案的字体在底部占据的总高度
    private static final int DP_DEFAULT_STEP_TEXT_SIZE = 16; // 默认流程文案的字体大小为16sp：“发单”、“应答”、“接驾”....

    List<ChartItemBean> mDatas = new ArrayList<>(); // Item的数据集合
    private OnSelectedPositionChangeListener mSelectedPositionChangeListener;

    private int mItemWidth;
    private int mItemHeight;
    private float mLeftPadding; // 整体折线图距离左边间距
    private float mRightPadding; // 整体折线图距离右边的边距
    private int mChartBaseLine;// 整体折线图横坐标的在画布上的Y轴坐标点

    private Paint mLevelTextPaint;
    private int mLevelTextRadius; // 文案矩形的圆角半径
    private int mLevelTextHalfWidth; // 文案矩形的总宽度的一半
    private int mLevelTextHalfHeight; // 文案矩形的总高度的一半
    private int mLevelTextAndPointPadding; // 文案矩形的总高度的一半

    private Paint mStepTextPaint;
    private int mStepTextHeight;

    private Paint mSplitLinePaint; // 分割线画笔

    private Paint mAreaPaint; // 区域画笔

    private Paint mPathPaint;

    private Paint mPointPaint;
    private float mPointRadius;

    private Paint mSelectedAreaPaint;
    private int mSelectedPosition = -1; // 被选中的位置
    private int mDownPosition = -1;
    private int mUpPosition = -1;


    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 设置数据
     *
     * @param datats
     */
    public void setDatats(List<ChartItemBean> datats) {
//        mDatas = datats;
        int max = 10;
        for (int i = 0; i < max; i++) {
            ChartItemBean data = new ChartItemBean();
            data.setStepText("发单");

            int levelIndex = (int) (Math.random() * 5) + 1;
            data.setLevelIndex(levelIndex); // 设置等级索引
            // 根据等级初始化颜色和文案
            String levelText = null;
            @ColorInt int color = Color.BLACK;
            switch (levelIndex) {
                case 1:
                    levelText = "E级";
                    color = COLOR_LEVEL_E;
                    break;
                case 2:
                    levelText = "D级";
                    color = COLOR_LEVEL_D;
                    break;
                case 3:
                    levelText = "C级";
                    color = COLOR_LEVEL_C;
                    break;
                case 4:
                    levelText = "B级";
                    color = COLOR_LEVEL_B;
                    break;
                case 5:
                    levelText = "A级";
                    color = COLOR_LEVEL_A;
                    break;
            }
            data.setLevelText(levelText);
            data.setColor(color);
            mDatas.add(data);
        }
        LogUtils.log("当前的Item个数：" + max);
//        invalidate();
    }

    /**
     * 设置选择回调监听
     *
     * @param listener
     */
    public void setOnSelectedPositionChangeListener(OnSelectedPositionChangeListener listener) {
        mSelectedPositionChangeListener = listener;
    }


    private void init() {
        mItemWidth = CommonUtils.dip2px(getContext(), DP_DEFAULT_ITEM_WIDTH);
        mItemHeight = CommonUtils.dip2px(getContext(), DP_DEFAULT_ITEM_HEIGHT);
        mLeftPadding = CommonUtils.dip2px(getContext(), DP_DEFAULT_LEFT_PADDING);
        mRightPadding = CommonUtils.dip2px(getContext(), DP_DEFAULT_RIGHT_PADDING);

        mLevelTextPaint = DrawUtils.getPaint(Color.RED);
        mLevelTextPaint.setTextAlign(Paint.Align.CENTER);
        mLevelTextPaint.setTextSize(CommonUtils.dip2px(getContext(), DP_DEFAULT_LEVEL_TEXT_SIZE));
        mLevelTextRadius = CommonUtils.dip2px(getContext(), DP_DEFAULT_LEVEL_TEXT_RADIUS); // 文案矩形的圆角半径
        mLevelTextHalfWidth = CommonUtils.dip2px(getContext(), DP_DEFAULT_LEVEL_TEXT_HALF_WIDTH);
        mLevelTextHalfHeight = CommonUtils.dip2px(getContext(), DP_DEFAULT_LEVEL_TEXT_HALF_HEIGHT);
        mLevelTextAndPointPadding = CommonUtils.dip2px(getContext(), DP_DEFAULT_LEVEL_TEXT_POINT_PADDING);


        mStepTextPaint = DrawUtils.getPaint(COLOR_STEP_TEXT);
        mStepTextPaint.setTextAlign(Paint.Align.CENTER);
        mStepTextPaint.setTextSize(CommonUtils.dip2px(getContext(), DP_DEFAULT_STEP_TEXT_SIZE));
        mStepTextHeight = CommonUtils.dip2px(getContext(), DP_DEFAULT_STEP_TEXT_HEIGHT);

        mSplitLinePaint = DrawUtils.getPaint(COLOR_SPLIT_LINE);
        mSplitLinePaint.setStrokeWidth(CommonUtils.dip2px(getContext(), 1));

        mAreaPaint = DrawUtils.getPaint(COLOR_AREA);

        mPathPaint = new Paint();
        mPathPaint.setStrokeCap(Paint.Cap.ROUND); // 设置圆角笔触效果
        mPathPaint.setStrokeJoin(Paint.Join.ROUND); // 设置转折点圆角过度
        mPathPaint.setStrokeWidth(CommonUtils.dip2px(getContext(), 4)); // 设置笔宽度
        mPathPaint.setStyle(Paint.Style.STROKE); //

        mPointPaint = DrawUtils.getPaint(Color.WHITE);
        mPointRadius = CommonUtils.dip2px(getContext(), 3);

        mSelectedAreaPaint = DrawUtils.getPaint(COLOR_SELECTED_AREA);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX() - mLeftPadding;
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownPosition = getTouchPosition((int) x, y);
                return true;
            case MotionEvent.ACTION_UP:
                mUpPosition = getTouchPosition((int) x, y);
                if (mUpPosition >= 0 && mUpPosition < mDatas.size() && mDownPosition == mUpPosition && mSelectedPosition != mUpPosition) {
                    mSelectedPosition = mUpPosition;
                    if (mSelectedPositionChangeListener != null) { // 通知回调
                        mSelectedPositionChangeListener.onSelectedPosition(mSelectedPosition);
                    }
                    invalidate(); // 刷新界面
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 得到触摸位置
     *
     * @param x 触摸点X轴位置，减去了leftPadding
     * @param y 触摸点Y轴位置
     * @return 触摸点的Item位置
     */
    private int getTouchPosition(int x, float y) {
        if (y < 0 || y >= mChartBaseLine || x < 0 || x > getWidth()) {
            return -1;
        }
        int i = x / mItemWidth;
        int j = x % mItemWidth;
        LogUtils.log("除数为：" + i + "; 余数为：" + j);
        return i + (j > 0 ? 1 : 0) - 1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.log("开始的宽高：" + widthMeasureSpec + ";" + heightMeasureSpec);
        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(measureWidth(), View.MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        LogUtils.log("结束时的宽高：" + widthMeasureSpec + ";" + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureWidth() {
        return (int) (mItemWidth * mDatas.size() + mLeftPadding + mRightPadding);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mDatas == null || mDatas.size() < 1) {
            return;
        }
        int height = getHeight();
        mChartBaseLine = height - mStepTextHeight; // 图标横坐标的在画布上的Y轴坐标点

        // 绘制X轴
        canvas.drawLine(mLeftPadding, mChartBaseLine, getWidth() - mRightPadding, mChartBaseLine, mSplitLinePaint);

        // 绘制Y轴
        canvas.drawLine(mLeftPadding, mChartBaseLine - mDatas.get(0).getLevelIndex() * mItemHeight, mLeftPadding, mChartBaseLine, mSplitLinePaint);

        // 绘制整体折线图
        for (int i = 0; i < mDatas.size(); i++) {
            ChartItemBean data = mDatas.get(i);
            float cX = mItemWidth * (i + 0.5f) + mLeftPadding; // 点的X轴中心位置坐标
            float cY = mChartBaseLine - data.getLevelIndex() * mItemHeight; // 点的Y轴中心位置坐标
            int color = data.getColor();
            // step1:绘制底部Step文案
            float baseline = DrawUtils.getTextMiddleBaseline(mChartBaseLine, height, mLevelTextPaint); // 绘制日期文案的baseLine位置
            canvas.drawText(data.getStepText(), cX, baseline, mStepTextPaint);

            // step2:绘制Y轴分割线
            drawItem(canvas, mChartBaseLine, i, data, cX, cY, color);

            // step3:绘制圆角矩阵文案
            float cYLevelText = cY - (mLevelTextAndPointPadding + mLevelTextHalfHeight);
            DrawUtils.drawRoundText(data.getLevelText(), data.getColor(), cX, cYLevelText, mLevelTextRadius, mLevelTextHalfWidth, mLevelTextHalfHeight, canvas, mLevelTextPaint);

            // 绘制被选中区域
            if (mSelectedPosition == i) {
                canvas.drawRect(cX - mItemWidth * 0.5f, 0, cX + mItemWidth * 0.5f, mChartBaseLine, mSelectedAreaPaint);
            }
        }

    }

    /**
     * 绘制对应每一个Item元素
     *
     * @param canvas
     * @param chartBaseLine
     * @param i
     * @param data
     * @param cX
     * @param cY
     * @param color
     */
    private void drawItem(Canvas canvas, int chartBaseLine, int i, ChartItemBean data, float cX, float cY, int color) {
        // step1:声明要用的参数
        int preColor = 0, nextColor = 0;
        ChartItemBean preData; // 前一条Item的数据
        float preCx = 0, preCy = 0;
        ChartItemBean nextData; // 后一条Item的数据
        float nextCx = 0, nextCy = 0;
        float leftBottomX, leftBottomY, leftTopX, leftTopY = 0, rightBottomX, rightBottomY, rightTopX, rightTopY = 0; // 区域的左下角，左上角，右下角和右上角X，Y坐标
        leftBottomX = leftTopX = cX - mItemWidth * 0.5f; // 左下角和左上角的X轴坐标一致
        rightBottomX = rightTopX = cX + mItemWidth * 0.5f; // 右下角和右上角的X轴坐标一致
        leftBottomY = rightBottomY = chartBaseLine; // 左下角和右下角的Y轴坐标一致


        // step2:根据位置的不同初始化会随位置改变而改变的参数
        if (i == 0) { // 如果是开始位置
            leftTopY = cY;
            preColor = data.getColor();
            preCx = leftTopX + mPathPaint.getStrokeWidth() * 0.5f;
            preCy = leftTopY;
        }
        if (i == mDatas.size() - 1) { // 如果是结束位置
            rightTopY = cY;
            nextColor = data.getColor();
            nextCx = rightTopX - mPathPaint.getStrokeWidth() * 0.5f;
            nextCy = rightTopY;
        }
        if (i + 1 < mDatas.size()) { // 如果存在下一条数据
            nextData = mDatas.get(i + 1);
            nextCx = mItemWidth * (i + 1 + 0.5f) + mLeftPadding; // 点的X轴中心位置坐标
            nextCy = chartBaseLine - nextData.getLevelIndex() * mItemHeight; // 点的Y轴中心位置坐标
            nextColor = nextData.getColor();
            rightTopY = (cY + nextCy) * 0.5f;

        }
        if (i - 1 >= 0) {
            preData = mDatas.get(i - 1);
            preCx = mItemWidth * (i - 1 + 0.5f) + mLeftPadding; // 点的X轴中心位置坐标
            preCy = chartBaseLine - preData.getLevelIndex() * mItemHeight; // 点的Y轴中心位置坐标
            preColor = preData.getColor();
            leftTopY = (preCy + cY) * 0.5f;
        }

        // step3:绘制区域
        mAreaPaint.setColor(data.getColor());
        mAreaPaint.setAlpha(30);
        Path path = new Path();
        path.moveTo(leftBottomX, leftBottomY);
        path.lineTo(leftTopX, leftTopY);
        path.lineTo(cX, cY);
        path.lineTo(rightTopX, rightTopY);
        path.lineTo(rightBottomX, rightBottomY);
        path.close();
        canvas.drawPath(path, mAreaPaint);

        // step4:绘制分割线
        canvas.drawLine(rightBottomX, rightBottomY, rightTopX, rightTopY, mSplitLinePaint);


        // step5:绘制折线
        if (i == mDatas.size() - 1) { // 如果是末尾位置，需要末位点和最后的边界连接起来
            Shader mShader = new LinearGradient(cX, cY, nextCx, nextCy, color, nextColor, Shader.TileMode.MIRROR); // 设置颜色渐变
            mPathPaint.setShader(mShader);
            canvas.drawLine(cX, cY, nextCx, nextCy, mPathPaint);
        }
        // 绘制右边折线
        Shader mShader = new LinearGradient(preCx, preCy, cX, cY, preColor, color, Shader.TileMode.MIRROR); // 设置颜色渐变
        mPathPaint.setShader(mShader);
        canvas.drawLine(preCx, preCy, cX, cY, mPathPaint);


        // step5:绘制圆点
        if (i > 0) {
            canvas.drawCircle(preCx, preCy, mPointRadius, mPointPaint);
        }
        if (i == mDatas.size() - 1) {
            canvas.drawCircle(cX, cY, mPointRadius, mPointPaint);
        }
    }

    /**
     * 选中位置改变监听
     */
    public interface OnSelectedPositionChangeListener {
        void onSelectedPosition(int position);
    }

}
