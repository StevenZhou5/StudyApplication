package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.R;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.adapter.MainActivityListAdapter;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.adapter.MyBaseAdapter;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.bean.ActivityListItemBean;
import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.utils.dynamicUtils;

public class MainActivity extends AppCompatActivity {

    private ListView mLvMain;
    private MyBaseAdapter mLvMainAdapter;

    /**
     * 首页入口列表Item数据
     */
    private ActivityListItemBean[] mAllItemBeans = {
            new ActivityListItemBean("item1", AboutScrollViewActivity.class),
            new ActivityListItemBean("滑动吸顶效果", ChangeWithScrollActivity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initListView();
        initFloationButton();

        // 自增陷阱测试
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < 10; i++) {
            count1 = ++count1;
            count2 = count2++;
            Log.d("ZZW", "count1:" + count1 + ";" + "count2:" + count2); // count为0，并没有增加
        }

        String a = "abc";
        String b = new String("abc");
        Log.d("ZZW", "abc的HashCode：" + "abc".hashCode() + "; a的HashCode：" + a.hashCode() + "; b的HashCode：" + b.hashCode()
        +(a==b)+(a=="abc")+("abc"==b));

    }

    /**
     * 选择按钮初始化
     */
    private void initFloationButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * // ListView初始化
     */
    private void initListView() {
        mLvMain = (ListView) findViewById(R.id.lv_main);
        mLvMainAdapter = new MainActivityListAdapter();
        // 数据初始化
        List<Object> datas = new ArrayList<>();
        for (ActivityListItemBean data : mAllItemBeans) {
            datas.add(data);
        }
        mLvMainAdapter.setDatas(datas);
        mLvMain.setAdapter(mLvMainAdapter);
        mLvMain.setOnItemClickListener(mOnItemClickListener);
    }

    /**
     * ListView的点击监听
     */
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ActivityListItemBean data = (ActivityListItemBean) mLvMainAdapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, data.getActivity());
            startActivity(intent);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void aboutScrollView(View v) {
        Intent intent = new Intent(this, AboutScrollViewActivity.class);
        startActivity(intent);
    }

    /**
     * 动态规划测试
     *
     * @param v
     */
    public void dynamicTest(View v) {
        int total = (int) ((Math.random() * 100));
        int[] base = {1, 3, 5, 10, 20, 50, 100};
        Log.d("ZZW", "total为:" + total + "; minCount:" + dynamicUtils.getMinCount(40, base));
    }
}
