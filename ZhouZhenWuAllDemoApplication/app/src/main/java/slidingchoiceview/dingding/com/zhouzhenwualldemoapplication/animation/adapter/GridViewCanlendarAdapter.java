package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.animation.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.animation.model.GridCalendarBean;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.adapter.ListDataBaseAdapter;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/8/8
 * 类简介：
 */
public class GridViewCanlendarAdapter extends ListDataBaseAdapter<GridCalendarBean> {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.adapter_grid_calendar, null);
            holder.mTvDayOfMonth = (TextView) convertView.findViewById(R.id.tv_day_of_month);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GridCalendarBean data = mDatas.get(position);
        holder.mTvDayOfMonth.setText(data.getDayOfMonth());

        return convertView;
    }

    private class ViewHolder {
        TextView mTvDayOfMonth;
    }
}
