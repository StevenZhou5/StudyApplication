package test.zhouzhenwu.com.mydemo.common.widgets.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import test.zhouzhenwu.com.mydemo.common.utils.CommonUtils;
import test.zhouzhenwu.com.mydemo.common.utils.DrawUtils;


/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/9
 * 类简介：通过在Canvas绘制实现自定义日历控件，
 */
public class MyCalendarView extends View {
    @ColorInt
    private static final int COLOR_WEEK_TEXT = 0xFF666666; // 默认的星期文案字体颜色
    @ColorInt
    private static final int COLOR_DAY_TEXT = 0xFF333333; // 默认的日期文案字体颜色
    @ColorInt
    private static final int COLOR_DAY_TEXT_MARKER = 0xFFc69c6d; // 默认的日期文案的底部标注文案字体颜色
    @ColorInt
    private static final int COLOR_SELECTED_CIRCLE = 0xFFfc4f3f; // 被选中天的圆圈颜色
    @ColorInt
    private static final int COLOR_SELECTED_TEXT = Color.WHITE; // 被选中天的日期文案的颜色
    @ColorInt
    private static final int COLOR_SELECTED_TEXT_MARKER = 0xFFc69c6d; // 被选中天的日期文案的颜色
    @ColorInt
    private static final int COLOR_INVALID_TEXT = 0xFFcccccc; // 无效天的日期文案的颜色
    @ColorInt
    private static final int COLOR_INVALID_TEXT_MARKER = 0xFFcccccc; // 无效天的日期文案的底部标注文案字体颜色
    @ColorInt
    private static final int COLOR_CURRENT_DAY_TEXT = 0xFFfc4f3f; // 当前天的日期文案的颜色
    @ColorInt
    private static final int COLOR_CURRENT_DAY_TEXT_MARKER = 0xFFc69c6d; // 当前天的日期文案的底部标注文案字体颜色


    /*---------------- 公共相关 --------------------*/
    private Context mContext;
    private Calendar mCalendar;
    private OnDateSelectedListener mDateSelectedListener;
    private int mCurrentYear; // 当前年
    private int mCurrentMonth; // 当前月
    private int mCurrentDay; // 当前天
    private int mSelectYear; // 选择的年
    private int mSelectMonth; // 选择的月

    private int mScreenWidth; // 屏幕宽度
    private float mCellWidth; // 单元格宽度
    private float mCellHeight; // 单元格高度

    /*---------------- 绘制星期相关-------------------*/
    private boolean mIsShowWeek = false;
    private Paint mWeekTextPaint; //
    private String[] mWeeks = {"一", "二", "三", "四", "五", "六", "天"};


    /*----------------- 绘制具体日期相关 --------------*/
    private Paint mDayTextPaint;
    private Paint mDayMarkerTextPaint;
    private List<CalendarDataBean> mDatas = new ArrayList<>();

    private int mDownLineIndex = -1; // 按下事件的行索引
    private int mDownColumnIndex = -1; // 按下事件的列索引
    private int mUpLineIndex = -1; // 抬起事件的行索引
    private int mUpColumnIndex = -1; // 抬起事件的列索引


    public MyCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initCommon(context);
        initWeek();
        initDay();
    }

    /*----------------------------------------------- 公共相关 ---------------------------------------------------*/

    /**
     * 公共数据的相关初始化
     *
     * @param context
     */
    private void initCommon(Context context) {
        // 获取屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mCellWidth = mScreenWidth / 7f;
        mCellHeight = CommonUtils.dip2px(mContext, 66);

        mCalendar = Calendar.getInstance();
        mCurrentYear = mCalendar.get(Calendar.YEAR);
        mCurrentMonth = mCalendar.get(Calendar.MONTH) + 1;
        mCurrentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mSelectYear = mCurrentYear;
        mSelectMonth = mCurrentMonth;
    }

    /*----------------------------------------------- 顶部星期绘制相关相关 ---------------------------------------------------*/

    /**
     * 星期绘制的相关初始化
     */
    private void initWeek() {
        mWeekTextPaint = DrawUtils.getPaint(COLOR_WEEK_TEXT);
        mWeekTextPaint.setTextAlign(Paint.Align.CENTER);
        mWeekTextPaint.setTextSize(CommonUtils.dip2px(mContext, 14));
        mWeekTextPaint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体类型为加粗
    }

    /**
     * 绘制星期相关的Text
     *
     * @param canvas
     */
    private void drawWeek(Canvas canvas) {
        float baseLine = DrawUtils.getTextMiddleBaseline(0, mCellHeight, mWeekTextPaint);
        for (int i = 0; i < 7; i++) {
            String s = mWeeks[i];
            float x = DrawUtils.getTextMiddleStartX(mCellWidth * i, mCellWidth * (i + 1), mWeekTextPaint, s);
            canvas.drawText(s, x, baseLine, mWeekTextPaint);
        }
    }

    /*----------------------------------------------- 具体日期绘制相关绘制相关相关 ---------------------------------------------------*/

    /**
     * 具体日期绘制的相关初始化
     */
    private void initDay() {
        mDayTextPaint = DrawUtils.getPaint(COLOR_DAY_TEXT);
        mDayTextPaint.setTextAlign(Paint.Align.CENTER);
        mDayTextPaint.setTextSize(CommonUtils.dip2px(mContext, 16));

        mDayMarkerTextPaint = DrawUtils.getPaint(COLOR_DAY_TEXT_MARKER);
        mDayMarkerTextPaint.setTextAlign(Paint.Align.CENTER);
        mDayMarkerTextPaint.setTextSize(CommonUtils.dip2px(mContext, 10));

        initData();
    }

    /**
     * 初始化数据，默认那当前日历中的当前月
     */
    private void initData() {
        int monthStart = CalendarUtils.getFirstDayOfMonthIndexInWeek(mSelectYear, mSelectMonth);
        mDatas.clear();
        for (int i = 1; i < monthStart; i++) {
            mDatas.add(new CalendarDataBean());
        }

        int daysOfMo = CalendarUtils.getDaysOfCurrentMonth(mSelectYear, mSelectMonth);
        for (int i = 0; i < daysOfMo; i++) {
            CalendarDataBean bean = new CalendarDataBean();
            bean.setDayOfMonth(i + 1);
            mDatas.add(bean);
            int preLine = mDatas.size() / 7; // 用总数除于7的商是一定有的行数
            int plusLine = mDatas.size() % 7 == 0 ? 0 : 1; // 用总数除于7看是否有余数（及余数是否为0），如果有需要加1行，否则不用额外加一行
            int lineIndex = preLine + plusLine + (mIsShowWeek ? 1 : 0); // 行索引等于preLine加上plusLine,还要看是否需要再加上1，因为可以展示星期
            bean.setLineIndex(lineIndex);
            bean.setColumnIndex(mDatas.size() % 7 == 0 ? 7 : mDatas.size() % 7); // 列索引等于plusLine
            bean.setCurrentYear(mSelectYear);
            bean.setCurrentMonth(mSelectMonth);

            bean.setMarkerString("本月");
            int random = (int) (Math.random() * 100);
            if (random % 3 == 0) {
                bean.setStateType(CalendarDataBean.STATE_TYPE_INVALID);
                bean.setMarkerString("无");
            }
        }
    }

    /**
     * 绘制具体日期
     *
     * @param canvas
     */
    private void drawDay(Canvas canvas) {
        for (CalendarDataBean bean : mDatas) {
            int dayOfMonth = bean.getDayOfMonth(); // 当月的第几天
            if (dayOfMonth < 1) { // 如果小于1则不是当月天，不绘制
                continue;
            }

            // step1:初始化一些无论什么状态都需要的绘制信息
            String dateString = dayOfMonth + ""; // 要绘制的日期文案字符串
            String markerString = bean.getMarkerString();
            int columcIndex = bean.getColumnIndex(); // 当前bean的列索引值
            int lineIndex = bean.getLineIndex(); // 当前bean的行索引值
            float left = mCellWidth * (columcIndex - 1); //绘制区域的左边界
            float right = mCellWidth * columcIndex; // 绘制区域的右边界
            float top = mCellHeight * (lineIndex - 1); // 绘制区域的上边界
            float bottom = mCellHeight * lineIndex; // 绘制区域的下边界
            float dateStringBaseLine = DrawUtils.getTextMiddleBaseline(top, bottom, mDayTextPaint); // 绘制日期文案的baseLine位置
            float markerStringBaseLine = DrawUtils.getTextMiddleBaseline(dateStringBaseLine + CommonUtils.dip2px(mContext, 10), bottom, mDayMarkerTextPaint);
            float cX = (left + right) * 0.5f; // 绘制区域中的X轴中心的位置


            // step2: 根据不同状态，绘制每一个具体日期
            int state = bean.getStateType();
            switch (state) {
                case CalendarDataBean.STATE_TYPE_NORMAL:
                    drawNormalDay(canvas, dateString, cX, dateStringBaseLine, markerString, markerStringBaseLine, dayOfMonth);
                    break;
                case CalendarDataBean.STATE_TYPR_SELECTED:
                    drawSelectedDay(canvas, dateString, cX, top, bottom, dateStringBaseLine, markerString, markerStringBaseLine);
                    break;
                case CalendarDataBean.STATE_TYPE_INVALID:
                    drawInvalidDay(canvas, dateString, cX, dateStringBaseLine, markerString, markerStringBaseLine);
                    break;
                default:
                    break;
            }

        }
    }

    /**
     * 绘制普通状态天
     */
    private void drawNormalDay(Canvas canvas, String dateString, float cX, float dayBaseLine, String markerString, float markerBaseLine, int dayOfMonth) {
        // step1:判断是否是当前天
        boolean isCurrentday = mSelectYear == mCurrentYear && mSelectMonth == mCurrentMonth && dayOfMonth == mCurrentDay;
        if (isCurrentday) {
            mDayTextPaint.setColor(COLOR_CURRENT_DAY_TEXT);
            mDayMarkerTextPaint.setColor(COLOR_CURRENT_DAY_TEXT_MARKER);
        }
        // step2:绘制日期文案
        canvas.drawText(dateString, cX, dayBaseLine, mDayTextPaint);
        // step3:绘制markerString
        if (!TextUtils.isEmpty(markerString)) {
            canvas.drawText(markerString, cX, markerBaseLine, mDayMarkerTextPaint);
        }
        // step4: 如果是当前天那么要还原画笔颜色
        if (isCurrentday) {
            mDayTextPaint.setColor(COLOR_DAY_TEXT);
            mDayMarkerTextPaint.setColor(COLOR_DAY_TEXT_MARKER);
        }
    }

    /**
     * 绘制被选中天
     */
    private void drawSelectedDay(Canvas canvas, String dateString, float cX, float top, float bottom, float dayBaseLine, String markerString, float markerBaseLine) {
        // step1:记录画笔初始化状态
        int preColor = mDayTextPaint.getColor();
        int preMarkerColor = mDayMarkerTextPaint.getColor();
        // step2: 计算绘制时需要的参数
        float cY = (top + bottom) * 0.5f;
        // step3:绘制圆圈背景
        mDayTextPaint.setColor(COLOR_SELECTED_CIRCLE);
        canvas.drawCircle(cX, cY, CommonUtils.dip2px(mContext, 16), mDayTextPaint);
        // step4:绘制日期文案
        mDayTextPaint.setColor(COLOR_SELECTED_TEXT);
        canvas.drawText(dateString, cX, dayBaseLine, mDayTextPaint);
        // step5:绘制markerString
        if (!TextUtils.isEmpty(markerString)) {
            mDayMarkerTextPaint.setColor(COLOR_SELECTED_TEXT_MARKER);
            canvas.drawText(markerString, cX, markerBaseLine, mDayMarkerTextPaint);
        }
        // step5:还原画笔状态
        mDayTextPaint.setColor(preColor);
        mDayMarkerTextPaint.setColor(preMarkerColor);
    }

    /**
     * 绘制无效的天
     */
    private void drawInvalidDay(Canvas canvas, String dateString, float cX, float dayBaseLine, String markerString, float markerBaseLine) {
        // step1:记录画笔初始化状态
        int preColor = mDayTextPaint.getColor();
        int preMarkerColor = mDayMarkerTextPaint.getColor();
        // step2:绘制日期文案
        mDayTextPaint.setColor(COLOR_INVALID_TEXT);
        canvas.drawText(dateString, cX, dayBaseLine, mDayTextPaint);
        // step3:绘制markerString
        if (!TextUtils.isEmpty(markerString)) {
            mDayMarkerTextPaint.setColor(COLOR_INVALID_TEXT_MARKER);
            canvas.drawText(markerString, cX, markerBaseLine, mDayMarkerTextPaint);
        }
        // step4:还原画笔状态
        mDayTextPaint.setColor(preColor);
        mDayMarkerTextPaint.setColor(preMarkerColor);
    }

    /**
     * 检查处理此次点击事件
     */
    private void checkClickEvent() {
        if (mDownLineIndex != mUpLineIndex || mDownColumnIndex != mUpColumnIndex) { // 如果行列有一个不相等，那么此次点击视为无效
            return;
        }
        if (mUpColumnIndex < 1 || mUpColumnIndex > 7) { // 如果列不在周一至周日，那么为无效日期，返回无效
            return;
        }
        if (mIsShowWeek) {
            mUpLineIndex--; // 如果展示星期行，那么函数默认-1
        }
        if (mUpLineIndex < 1 || mUpLineIndex > 6) { // 日期行数最多六行，不在第一至第六行视为无效
            return;
        }
        int positionIndex = (mUpLineIndex - 1) * 7 + mUpColumnIndex - 1; // 计算在当前data中的位置,因为list位置是从0开始的，所以要减去1
        if (positionIndex >= mDatas.size()) { // 如果位置超过数组边界，那么点击到无效日期位置(非当前月日期)
            return;
        }
        CalendarDataBean bean = mDatas.get(positionIndex);
        int state = bean.getStateType();
        if (state != CalendarDataBean.STATE_TYPE_NORMAL) { // 不是普通状态，不去处理
            return;
        }
        // 普通状态:此时要讲之前选中状态的bean的state改为普通状态，并更新现在这个bean的状态为选中状态
        for (CalendarDataBean dataBean : mDatas) {
            if (dataBean.getStateType() == CalendarDataBean.STATE_TYPR_SELECTED) {
                dataBean.setStateType(CalendarDataBean.STATE_TYPE_NORMAL);
            }
        }
        bean.setStateType(CalendarDataBean.STATE_TYPR_SELECTED);
        if (mDateSelectedListener != null) { // 日期选择的监听监听回调
            mDateSelectedListener.onDateSelected(bean.getCurrentYear(), bean.getCurrentMonth(), bean.getDayOfMonth());
        }
        invalidate(); // 刷新界面
    }


    /*----------------------------------------------- 重载系统方法 ---------------------------------------------------*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // step1 : 绘制星期相关的Text
        if (mIsShowWeek) {
            drawWeek(canvas);
        }

        // step2:绘制具体日期
        drawDay(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(); // 获取触摸的X轴位置
        float y = event.getY(); // 获取触摸的Y轴位置
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 点击事件
                mDownLineIndex = (int) (y / mCellHeight) + 1;
                mDownColumnIndex = (int) (x / mCellWidth) + 1;
                return true;
            case MotionEvent.ACTION_MOVE: // 移动事件

                break;
            case MotionEvent.ACTION_UP:  // 抬起事件
                mUpLineIndex = (int) (y / mCellHeight) + 1;
                mUpColumnIndex = (int) (x / mCellWidth) + 1;
                checkClickEvent();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    /*----------------------------------------------- 公共方法定义区(根据需求扩展) ---------------------------------------------------*/
    public Calendar getmCalendar() {
        return mCalendar;
    }

    /**
     * 设置所选年月
     *
     * @param year  年
     * @param month 月
     */
    public void refreshYearAndMonth(int year, int month) {
        mSelectYear = year;
        mSelectMonth = month;
        mCalendar.set(Calendar.YEAR, mSelectYear);
        mCalendar.set(Calendar.MONTH, mSelectMonth - 1);
        initData();
        invalidate();
    }

    /**
     * 设置日期选择的监听
     *
     * @param listener
     */
    public void setDateSelectedListener(OnDateSelectedListener listener) {
        mDateSelectedListener = listener;
    }

    /*----------------------------------------------- 回调接口定义 ---------------------------------------------------*/

    /**
     * 日期选择监听接口
     */
    public interface OnDateSelectedListener {
        void onDateSelected(int year, int month, int day);
    }

}
