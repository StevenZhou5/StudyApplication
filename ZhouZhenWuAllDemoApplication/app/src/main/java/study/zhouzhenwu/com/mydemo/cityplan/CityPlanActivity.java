package study.zhouzhenwu.com.mydemo.cityplan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import study.zhouzhenwu.com.mydemo.R;

public class CityPlanActivity extends AppCompatActivity {
    TextView mTvCityList;
    Button mBtRun;
    TextView mTvCityResult;

    private String[] mCityLists = {"山东省--济南", "山西省--太原", "吉林省 --长春", "江苏省--南京",
            "湖北省--武汉", "福建省-福州市", "重庆市-重庆", "宁夏-银川", "贵州省-贵阳", "云南省-昆明", "广西省-南宁",
            "西藏-拉萨", "江西省-南昌", "海南省-海口", "上海市 --上海", "深圳", "香港", "澳门", "台湾"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_plan);
        mTvCityList = (TextView) findViewById(R.id.tv_city_list);
        mBtRun = (Button) findViewById(R.id.bt_run);
        mTvCityResult = (TextView) findViewById(R.id.tv_city_result);


        mTvCityList.setText(getCityListString(mCityLists));
        mBtRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) (Math.random() * (mCityLists.length));
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
