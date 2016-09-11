package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.widgets.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.utils.CommonUtils;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.utils.DrawUtils;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.utils.LogUtils;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/9/6
 * 类简介：展示评级流程的趋势折线图的自定义View
 */
public class ChartView extends View {
    private static final int DP_DEFAULT_ITEM_WIDTH = 55; // 默认Item的宽度为55Dp：折线图横坐标每一个单元的宽度
    private static final int DP_DEFAULT_ITEM_HEIGHT = 20; // 默认Item的宽度为20Dp:：折线图纵坐标每一个单元的高度
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


    private static final int DP_DEFAULT_STEP_TEXT_HEIGHT = 30; // 默认流程文案的字体在底部占据的总高度
    private static final int DP_DEFAULT_STEP_TEXT_SIZE = 16; // 默认流程文案的字体大小为16sp：“发单”、“应答”、“接驾”....

    List<ChartItemBean> mDatas = new ArrayList<>(); // Item的数据集合
    private int mItemWidth;
    private int mItemHeight;

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


    private void init() {
        mItemWidth = CommonUtils.dip2px(getContext(), DP_DEFAULT_ITEM_WIDTH);
        mItemHeight = CommonUtils.dip2px(getContext(), DP_DEFAULT_ITEM_HEIGHT);

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
    }

    private float mLeftPadding = 20; // 左边间距
    private float mRightPadding = 20;

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int chartBaseLine = height - mStepTextHeight; // 图标横坐标的在画布上的Y轴坐标点

        // 绘制X轴
        canvas.drawLine(mLeftPadding, chartBaseLine, getWidth() - mRightPadding, chartBaseLine, mSplitLinePaint);

        // 绘制Y轴
        canvas.drawLine(mLeftPadding, 0, mLeftPadding, chartBaseLine, mSplitLinePaint);

        for (int i = 0; i < mDatas.size(); i++) {
            ChartItemBean data = mDatas.get(i);
            float cX = mItemWidth * (i + 0.5f) + mLeftPadding; // 点的X轴中心位置坐标
            float cY = chartBaseLine - data.getLevelIndex() * mItemHeight; // 点的Y轴中心位置坐标
            int color = data.getColor();
            // step1:绘制底部Step文案
            float baseline = DrawUtils.getTextMiddleBaseline(chartBaseLine, height, mLevelTextPaint); // 绘制日期文案的baseLine位置
            canvas.drawText(data.getStepText(), cX, baseline, mStepTextPaint);

            // step2:绘制Y轴分割线
            drawItem(canvas, chartBaseLine, i, data, cX, cY, color);

            // step3:绘制圆角矩阵文案
            float cYLevelText = cY - (mLevelTextAndPointPadding + mLevelTextHalfHeight);
            drawRoundText(data.getLevelText(), data.getColor(), cX, cYLevelText, mLevelTextRadius, mLevelTextHalfWidth, mLevelTextHalfHeight, canvas, mLevelTextPaint);

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
     * 绘制一个被圆角矩形包裹的Text
     *
     * @param text       绘制文案
     * @param color      文案颜色
     * @param cX         文案中心在画布上的X轴坐标
     * @param cY         文案中心在画布上的Y轴坐标
     * @param radius     圆角半径
     * @param halfWidth  整个矩形的宽度的一半
     * @param halfHeight 整个矩形的高度的一半
     * @param canvas     画布
     * @param paint      画笔
     */
    private void drawRoundText(String text, int color, float cX, float cY, int radius, int halfWidth, int halfHeight, Canvas canvas, Paint paint) {
        // step1：设置当前绘制时画笔需要的属性
        Paint.Style preStyle = paint.getStyle();
        int preColor = paint.getColor();
        paint.setColor(color);

        // step2:进行绘制
        float baseline = DrawUtils.getTextMiddleBaseline(cY - radius, cY + radius, paint); // 绘制日期文案的baseLine位置
        canvas.drawText(text, cX, baseline, paint);
        paint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(cX - halfWidth, cY - halfHeight, cX + halfWidth, cY + halfHeight);
        canvas.drawRoundRect(rectF, radius, radius, paint); // 绘制圆角矩形：参数2是

        // step3:还原画笔属性
        paint.setStyle(preStyle);
        paint.setColor(preColor);
    }


}
