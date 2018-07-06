package study.zhouzhenwu.com.mydemo.rxjava.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * Created by Steven on 2018/7/5.
 */

public class RxJavaSimpleTestActivity extends BaseActivity {
    @Bind(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_simple_test);
        ButterKnife.bind(this);

        Observable
                .create((Observable.OnSubscribe<View>) subscriber
                        -> mTvTest.setOnClickListener(v -> subscriber.onNext(v)))
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Subscriber<View>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(View o) {
//                        observableCreateTest();
//                        fromTest();
                        justTest();
                    }
                });
    }

    /**
     * observable的创建测试
     */
    private void observableCreateTest() {
        Observable
                .create((Observable.OnSubscribe<Integer>) observer -> {
                    try {
                        if (!observer.isUnsubscribed()) {
                            for (int i = 1; i < 5; i++) {
                                observer.onNext(i);
                            }
                            observer.onCompleted();
                        }
                    } catch (Exception e) {
                        observer.onError(e);
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        LogUtils.log("observableCreateTest-Next: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        LogUtils.log("observableCreateTest-Error: " + error.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        LogUtils.log("observableCreateTest-Sequence complete.");
                    }
                });
    }

    /**
     * from操作符测试
     */
    private void fromTest() {
        Integer[] items = {0, 1, 2, 3, 4, 5};
        Observable myObservable = Observable.from(items);

        myObservable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                LogUtils.log("fromTest-Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                LogUtils.log("fromTest-Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                LogUtils.log("fromTest-Sequence complete.");
            }
        });
    }


    private void justTest() {
        Observable
                .just(0, 1, 2, 3, 4, 5, 6)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        LogUtils.log("justTest-Next: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        LogUtils.log("justTest-Error: " + error.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        LogUtils.log("justTest-Sequence complete.");
                    }
                });

    }

}
