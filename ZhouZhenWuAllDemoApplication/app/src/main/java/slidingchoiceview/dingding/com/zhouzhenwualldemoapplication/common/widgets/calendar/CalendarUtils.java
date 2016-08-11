package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.widgets.calendar;

import java.util.Calendar;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/10
 * 类简介：用于处理日历中日期数据的工具类
 */
public class CalendarUtils {

    /**
     * 根据所选的年月，得到当前月的有多少天
     *
     * @param selectedYear  所选的年
     * @param selectedMonth 所选的月
     * @return 本月有多少天
     */
    public static int getDaysOfCurrentMonth(int selectedYear, int selectedMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, selectedYear);
        cal.set(Calendar.MONTH, selectedMonth - 1);//Java月份从0开始算，所以要减去1
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 根据所选年月，得到当月第一天是星期几
     *
     * @param selectedYear
     * @param selectedMonth
     * @return
     */
    public static int getFirstDayOfMonthIndexInWeek(int selectedYear, int selectedMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, selectedYear);
        cal.set(Calendar.MONTH, selectedMonth - 1);//Java月份从0开始算，所以要减去1
        cal.set(Calendar.DAY_OF_MONTH, 1); // 一定要把当前天设置为本月的第一天
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 然后拿到当前天(既本月第一天)是周几
        int start = 0;
        // 由于日历是从周日开始算第一天的，所以这里要做一下处理
        if (dayOfWeek >= 2 && dayOfWeek <= 7) { // 周一至周六
            start = dayOfWeek - 1;
        } else if (dayOfWeek == 1) { // 周日
            start = 7;
        }
        return start;
    }
}
