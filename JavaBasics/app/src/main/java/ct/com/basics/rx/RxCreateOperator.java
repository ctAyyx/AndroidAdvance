package ct.com.basics.rx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * TIME : 2020/6/27 0027
 * AUTHOR : CT
 * DESC : RxJava 创建操作符
 * 一.复杂数据遍历
 * just
 * fromArray
 * fromIterable
 * Range
 * 二.定时任务
 * interval
 * intervalRange
 * 三.嵌套回调异步事件
 * create
 * 四.延迟任务
 * defer
 * timer
 */
public class RxCreateOperator {

    public static void main(String[] args) {
        //使用create操作符
        //createOperator();

        //使用just操作符
        //justOperator();

        //使用fromArray操作符
        //fromArrayOperator();

        //使用fromIterator操作符
        //fromIterableOperator();

        //使用Range操作符
        //rangeOperator();

        //interval操作符
        //intervalOperator();

        //intervalRange操作符
        //intervalRangeOperator();

        //defer 操作符
        //deferOperator();

        //timer 操作符
        timerOperator();
    }


    /**
     * create 操作符
     * 最原始的创建Observable对象的操作符
     * <p>
     * 对于事件的分发要有调用者自己控制
     */
    private static void createOperator() {

        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {

                //发射两个Next事件
                emitter.onNext("A");
                emitter.onNext(name);
                //发送一个完毕通知
                emitter.onComplete();
            }
        });
        name = "王二";
        observable
                .subscribeOn(Schedulers.io())
                .subscribe(createObserver());

    }


    //================复杂数据遍历===================

    /**
     * just 操作符
     * 发送最多十个数据
     * <p>
     * 数组数据迭代发射
     */
    private static void justOperator() {
        Observable.just("A", "B", "C")
                .subscribe(createObserver());
    }

    /**
     * fromArray 操作符
     * <p>
     * 数组数据迭代发射
     */
    private static void fromArrayOperator() {
        Observable.fromArray("A", "B", "C")
                .subscribe(createObserver());
    }

    /**
     * fromIterable 操作符
     * <p>
     * 拥有迭代器对象迭代发射数据
     */
    private static void fromIterableOperator() {
        List<String> mList = new ArrayList<>();
        mList.add("A");
        mList.add("B");
        mList.add("C");
        Observable.fromIterable(mList)
                .subscribe(createObserver());
    }

    /**
     * range 操作符
     * <p>
     * 定义一个左闭右开的区间,迭代递加发送数据
     */
    private static void rangeOperator() {
        //start 开始的位置
        //count 发射的次数
        Observable.range(10, 5)
                .subscribe(createObserver());
    }


    //===============定时任务=================

    /**
     * interval 操作符
     * <p>
     * 周期性的发射数据 会导致线程切换
     */
    private static void intervalOperator() {
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribe(createObserver());

        try {
            Thread.sleep(30000);
        } catch (Exception e) {
        }
    }

    /**
     * intervalRange 操作符
     * <p>
     * 指定一个左闭右开区间 周期性的发射数据 会导致线程切换
     */
    private static void intervalRangeOperator() {
        Observable.intervalRange(0, 5, 3000, 1000, TimeUnit.MILLISECONDS)
                .subscribe(createObserver());

        try {
            Thread.sleep(30000);
        } catch (Exception e) {
        }
    }


    //================延迟任务==================
    private static String name = "张三";

    /**
     * defer 操作符
     * <p>
     * 在订阅的时候才去创建Observable对象
     */
    private static void deferOperator() {


        Observable observable = Observable.defer(new Supplier<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> get() throws Throwable {
                return Observable.just(name);
            }
        });

        name = "李四";

        observable.subscribe(createObserver());
    }


    /**
     * timer 操作符
     * <p>
     * 在指定延迟时间后发送数据 会导致线程切换
     */
    private static void timerOperator() {
        Observable.timer(5000, TimeUnit.MILLISECONDS)
                .subscribe(createObserver());

        try {
            Thread.sleep(30000);
        } catch (Exception e) {
        }
    }


    private static <T> Observer<T> createObserver() {
        return new Observer<T>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe:" + Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull T t) {
                System.out.println("onNext:" + t + " --- " + Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError:" + e.getMessage() + "---" + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete:" + Thread.currentThread().getName());
            }
        };
    }

}




