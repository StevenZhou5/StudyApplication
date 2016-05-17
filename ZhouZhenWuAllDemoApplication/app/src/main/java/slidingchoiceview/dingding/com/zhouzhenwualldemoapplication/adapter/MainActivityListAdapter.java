package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.MyApplication;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.bean.ActivityListItemBean;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：16/4/9
 * 类简介：
 */
public class MainActivityListAdapter extends MyBaseAdapter {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoleder holeder = null;
        if (convertView == null) {
            holeder = new ViewHoleder();
            convertView = LayoutInflater.from(MyApplication.instance.getApplicationContext()).inflate(R.layout.adapter_item_main_activity_list, null);
            holeder.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holeder);
        } else {
            holeder = (ViewHoleder) convertView.getTag();
        }
        ActivityListItemBean data = (ActivityListItemBean) mDatas.get(position);
        holeder.mTvName.setText(data.getName());

        return convertView;
    }

    private class ViewHoleder {
        TextView mTvName;
    }
}
