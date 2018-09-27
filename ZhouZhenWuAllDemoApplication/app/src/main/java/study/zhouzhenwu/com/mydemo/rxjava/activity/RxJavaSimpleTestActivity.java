package study.zhouzhenwu.com.mydemo.rxjava.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;
import study.zhouzhenwu.com.mydemo.common.utils.LogUtils;

/**
 * Created by Steven on 2018/7/5.
 */

public class RxJavaSimpleTestActivity extends BaseActivity {
    TextView mTvTest;

    private List<Subscription> subscriptionList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_simple_test);
        mTvTest = (TextView) findViewById(R.id.tv_test);

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
//                        justTest();
//                        timerTest();
//                        rangAndRepeatTest();
//                        repeatWhenTest();
//                        bufferTest();
                        flatMapTest();
                       }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Subscription subscription : subscriptionList) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
        subscriptionList.clear();
    }

    // ===============================<editor-fold des="RxJava的创建型操作符">==========================

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


    /**
     * just操作符测试：其实just就是from，just最多有10个参数
     */
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


    /**
     * timer操作符测试
     */
    private void timerTest() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.log("timerTest-timer-onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtils.log("timerTest-timer-onNext:" + aLong);
                    }
                });

        Subscription sInterval = Observable.interval(1, 2, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.log("timerTest-interval-onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtils.log("timerTest-interval-onNext:" + aLong);
                    }
                });

        subscriptionList.add(sInterval);
    }


    private void rangAndRepeatTest() {
        Observable.range(5, 3)
                .repeat(3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.log("rangAndRepeatTest-onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.log("rangAndRepeatTest-onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtils.log("rangAndRepeatTest-nNext:" + integer);
                    }
                });
    }

    /**
     * repeatWhen是在接收到onCompleted()时执行操作
     */
    private void repeatWhenTest() {
        Observable.range(5, 4)
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        //重复3次
                        return observable.zipWith(Observable.range(1, 3), new Func2<Void, Integer, Integer>() {
                            @Override
                            public Integer call(Void aVoid, Integer integer) {
                                return integer;
                            }
                        }).flatMap(new Func1<Integer, Observable<?>>() {
                            @Override
                            public Observable<?> call(Integer integer) {
                                System.out.println("repeatWhenTest:delay repeat the " + integer + " count");
                                //1秒钟重复一次
                                return Observable.timer(1, TimeUnit.SECONDS);
                            }
                        });
                    }
                }).retryWhen(observable -> observable.flatMap((Func1<Throwable, Observable<?>>) throwable -> {
            if (throwable instanceof IOException) {
                return observable;
            } else {
                return observable.error(throwable);
            }
        }))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.log("repeatWhenTest-onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.log("repeatWhenTest-onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtils.log("repeatWhenTest-onNext:" + integer);
                    }
                });
    }
    // </editor-fold>


    // ============================<editor-fold des = "转换操作符测试">========================
    private void bufferTest() {
        //定义邮件内容
        final String[] mails = new String[]{"Here is an email!", "Another email!", "Yet another email!"};
        //每隔1秒就随机发布一封邮件
        Observable<String> endlessMail = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (subscriber.isUnsubscribed()) return;
                    Random random = new Random();
                    while (true) {
                        String mail = mails[random.nextInt(mails.length)];
                        subscriber.onNext(mail);
                        Thread.sleep(1000);
                    }
                } catch (Exception ex) {
                    if (subscriber.isUnsubscribed()) return;
                    subscriber.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.io());
        //把上面产生的邮件内容缓存到列表中，并每隔3秒通知订阅者
        Subscription subscription = endlessMail.buffer(3, TimeUnit.SECONDS).subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> list) {

                LogUtils.log(String.format("You've got %d new messages!  Here they are!", list.size()));
                for (int i = 0; i < list.size(); i++)
                    LogUtils.log("**" + list.get(i).toString());
            }
        });
        subscriptionList.add(subscription);
    }

    public void flatMapTest() {
        Observable.just(getApplicationContext().getExternalCacheDir())
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        //参数file是just操作符产生的结果，这里判断file是不是目录文件，如果是目录文件，则递归查找其子文件flatMap操作符神奇的地方在于，返回的结果还是一个Observable，而这个Observable其实是包含多个文件的Observable的，输出应该是ExternalCacheDir下的所有文件
                        return listFiles(file);
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        LogUtils.log(file.getAbsolutePath());
                    }
                });

    }

    private Observable<File> listFiles(File f) {
        if (f.isDirectory()) {
            return Observable.from(f.listFiles()).flatMap(new Func1<File, Observable<File>>() {
                @Override
                public Observable<File> call(File file) {
                    return listFiles(f);
                }
            });
        } else {
            return Observable.just(f);
        }
    }


    // </editor-fold>

}
