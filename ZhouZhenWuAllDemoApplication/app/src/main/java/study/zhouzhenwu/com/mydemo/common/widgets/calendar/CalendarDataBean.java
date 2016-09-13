package study.zhouzhenwu.com.mydemo.common.widgets.calendar;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/9
 * 类简介：对应caleandarView中每一个具体日期位置要在示时所要用到的数据集合bean
 */
public class CalendarDataBean {
    public static final int STATE_TYPE_NORMAL = 0; // 普通状态
    public static final int STATE_TYPR_SELECTED = 1; // 被选中状态
    public static final int STATE_TYPE_INVALID = 2; // 无效状态，不可被点击
    private int dayOfMonth = -1; // 日期索引：属于当月的第几天，小于0认为是无效日期
    private int currentYear; // 当前年份
    private int currentMonth; // 当前月份
    private int lineIndex = 1; // 行索引：在日历中属于第几行，默认是第一行
    private int columnIndex = 1; // 列索引：在日历中属于第几列，默认是第一列

    private int stateType = STATE_TYPE_NORMAL; // 记录日期的状态，默认为普通状态

    private String markerString; // 用于日期下面的标注的文案


    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getStateType() {
        return stateType;
    }

    public void setStateType(int stateType) {
        this.stateType = stateType;
    }

    public String getMarkerString() {
        return markerString;
    }

    public void setMarkerString(String markerString) {
        this.markerString = markerString;
    }
}
