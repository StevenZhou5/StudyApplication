package study.zhouzhenwu.com.mydemo.designpattern.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：19/4/9
 * 类简介：有多个对象，每个对象持有对下一个对象的引用，这样就会形成一条链，请求在这条链上传递，直到某一对象决定处理该请求。
 * 但是发出者并不清楚到底最终那个对象会处理该请求，所以，责任链模式可以实现，在隐瞒客户端的情况下，对系统进行动态的调整;
 */
public class DesignPatternChainOfResponsibility extends BaseActivity {
    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 必须现有上级部门，才能建立下级部门,下级部门都有自己所属的上级部门
        ProvinceDepartment provinceDepartment = new ProvinceDepartment(LEVEL_3);
        CityDepartment cityDepartment = new CityDepartment(LEVEL_2);
        cityDepartment.setSuperiorDepartment(provinceDepartment);
        LocalDepartment localDepartment = new LocalDepartment(LEVEL_1);
        localDepartment.setSuperiorDepartment(cityDepartment);


        // 现在地方民众发起了一起诉讼事件
        Event event = new Event(LEVEL_2);
        // 事件先交由地方政府去处理
        localDepartment.handle(event);

    }


    public class Event {
        private int level;

        public Event(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }


    /**
     * 基础行政部门类
     */
    public abstract class BaseDepartment {
        protected BaseDepartment superiorDepartment; // 上级部分

        protected int level;

        public BaseDepartment(int level) {
            this.level = level;
        }

        public void setSuperiorDepartment(BaseDepartment superiorDepartment) {
            this.superiorDepartment = superiorDepartment;
        }

        public abstract void handle(Event event);

    }

    /**
     * 地方行政部门
     */
    public class LocalDepartment extends BaseDepartment {

        public LocalDepartment(int level) {
            super(level);
        }

        @Override
        public void handle(Event event) {
            if (event.getLevel() == level) {
                Log.d("ZZW", "地方政府处理了此事件");
                return;
            }
            if (superiorDepartment != null) {
                Log.d("ZZW", "地方政府将事件转给了上级部门去处理");
                superiorDepartment.handle(event);
            }

        }
    }

    /**
     * 市级行政部门
     */
    public class CityDepartment extends BaseDepartment {

        public CityDepartment(int level) {
            super(level);
        }

        @Override
        public void handle(Event event) {
            if (event.getLevel() == level) {
                Log.d("ZZW", "市级政府处理了此事件");
                return;
            }
            if (superiorDepartment != null) {
                Log.d("ZZW", "市级政府将事件转给了上级部门去处理");
                superiorDepartment.handle(event);
            }
        }
    }

    /**
     * 省级行政部门
     */
    public class ProvinceDepartment extends BaseDepartment {

        public ProvinceDepartment(int level) {
            super(level);
        }

        @Override
        public void handle(Event event) {
            if (event.getLevel() == level) {
                Log.d("ZZW", "省级政府处理了此事件");
                return;
            }
            if (superiorDepartment != null) {
                Log.d("ZZW", "省级政府将事件转给了上级部门去处理");
                superiorDepartment.handle(event);
            }
        }
    }

}
