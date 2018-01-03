package study.zhouzhenwu.com.mydemo.cityplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.zhouzhenwu.com.mydemo.R;

public class CityPlanActivity extends AppCompatActivity {
    @Bind(R.id.tv_city_list)
    TextView mTvCityList;
    @Bind(R.id.bt_run)
    Button mBtRun;
    @Bind(R.id.tv_city_result)
    TextView mTvCityResult;

    private String[] mCityLists = {"河北省--石家庄", "山东省--济南", "山西省--太原", "吉林省 --长春",
            "辽宁省 --沈阳", "安徽省--合肥", "黑龙江省 --哈尔滨", "江苏省--南京", "湖北省--武汉", "上海市 --上海",
            "浙江省--杭州"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_plan);
        ButterKnife.bind(this);
        mTvCityList.setText(getCityListString(mCityLists));
        mBtRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) (Math.random() * (mCityLists.length + 1));
                mTvCityResult.setText(mCityLists[index]);
            }
        });
    }

    private String getCityListString(String[] cityLists) {
        StringBuilder sb = new StringBuilder();
        for (String cityName : cityLists) {
            sb.append(cityName);
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
