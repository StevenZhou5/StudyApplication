package study.zhouzhenwu.com.mydemo.common.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.module.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/4/9
 * 类简介：用于进行Activi跳转的的list的Adapter
 */
public class ActivityListAdapter extends ListDataBaseAdapter<ActivityListItemBean> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoleder holeder = null;
        if (convertView == null) {
            holeder = new ViewHoleder();
            convertView = mLayoutInflater.inflate(R.layout.adapter_item_main_activity_list, null);
            holeder.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holeder);
        } else {
            holeder = (ViewHoleder) convertView.getTag();
        }
        ActivityListItemBean data = mDatas.get(position);
        holeder.mTvName.setText(data.getName());

        return convertView;
    }

    private class ViewHoleder {
        TextView mTvName;
    }
}
