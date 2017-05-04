package study.zhouzhenwu.com.mydemo.android.animation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.pickerview.MyTimePickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.widgets.calendar.MyCalendarView;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/8
 * 类简介：展示日历控件
 */
public class CalendarActivity extends BaseActivity {
    @Bind(R.id.calendar_view)
    MyCalendarView mCalendar;
    @Bind(R.id.bt_choice_date)
    View mBtChoiceDate;

    private TimePickerView timePickerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_calendar);
        ButterKnife.bind(this);

        mBtChoiceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerView.setTime(mCalendar.getmCalendar().getTime());
                timePickerView.show();
            }
        });
        timePickerView = new MyTimePickerView(this);
        timePickerView.setTitle("Select Year/Month");
        timePickerView.setCyclic(true);
        timePickerView.setCancelable(true);
        timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                mCalendar.refreshYearAndMonth(year, month);
            }
        });
    }
}
