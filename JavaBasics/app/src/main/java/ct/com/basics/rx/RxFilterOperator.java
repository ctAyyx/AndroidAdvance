package ct.com.basics.rx;

import java.util.concurrent.TimeUnit;

import ct.com.basics.CLog;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;

/**
 * RxJava 过滤操作符
 * <p>
 * 一.指定过滤条件，过滤需要的事件/数据
 * filter
 * 二.过滤指定类型的事件/数据
 * ofType
 * 三.过滤条件不满足的事件/数据
 * skip
 * 四.过滤掉重复的事件/数据
 * distinct
 * distinctUnitChanged
 * 五.按时间或者数量过滤事件/数据
 * take
 * 六.过滤指定位置的事件
 * elementAt
 * 七.按事件段过滤事件
 * throttleFirst
 * throttleLast
 * throttleLatest
 */
public class RxFilterOperator {
    public static void main(String[] args) {

        //filter 操作符
        filterOperator();

        //ofType 操作符
        //ofTypeOperator();

        //skip 操作符
        //skipOperator();

        //distinct 操作符
        //distinctOperator();

        //distinctUnitChanged
        //distinctUntilChangedOperator();

        //take 操作符
        //takeOperator();

        //elementAt 操作符
        //elementAtOperator();


        //throttleFirst 操作符
        //throttleFirstOperator();
    }


    //=============指定过滤条件，过滤需要的事件/数据============

    /**
     * filter 操作符
     * <p>
     * 过滤不满足指定条件的事件
     */
    private static void filterOperator() {

        Observable.just("A", "B", "C")
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Throwable {
                        return s.equals("B");
                    }
                }).subscribe(createObserver());
    }


    //*================过滤指定类型的事件/数据================

    /**
     * ofType 操作符
     * 过滤不满足指定类型事件
     */
    private static void ofTypeOperator() {
        Observable.just("A", "B", "C", 1, 2, 3)
                .ofType(String.class)
                .subscribe(createObserver());
    }


    //=============过滤条件不满足的事件/数据================

    /**
     * skip 操作符
     * <p>
     * 过滤指定个数的事件
     * 跳过指定个数 或 指定时间的事件
     */
    private static void skipOperator() {
        Observable.just("A", "B", 1, 2)
                .skip(2)
                .subscribe(createObserver());
    }

    //==============过滤掉重复的事件/数据=====================

    /**
     * distinct 操作符
     * <p>
     * 除去重复事件 可以自定义去重规则
     */
    private static void distinctOperator() {
        Observable.just("A", "B", "C", "C", "A")
                .distinct(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Throwable {
                        return s.hashCode();
                    }
                })
                .subscribe(createObserver());
    }


    /**
     * distinctUntilChanged 操作符
     * 确保相邻的数据不重复
     */
    private static void distinctUntilChangedOperator() {

        Observable.just("A", "B", "C", "C", "A")
                .distinctUntilChanged()
                .subscribe(createObserver());

    }


    //===========按时间或者数量过滤事件/数据==========

    /**
     * take 操作符
     * 按时间或者数量过滤事件/数据
     * <p>
     * 只接收指定个数 或时间内的时间
     */
    private static void takeOperator() {
        Observable.just("A", "B", "C", "D", "E")
                .take(3)
                .subscribe(createObserver());
    }


    //===========过滤指定位置的事件===============

    /**
     * elementAt 操作符
     * <p>
     * 过滤指定位置的事件
     */
    private static void elementAtOperator() {

        Observable.just("A", "B", "C", "D")
                .elementAt(2)
                .subscribe(new MaybeObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull String s) {
                        CLog.log("elementAt:" + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        Observable.just("A", "B", "C", "D")

                .elementAt(4, "F")
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull String s) {
                        CLog.log("elementAt2:" + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    //=============按事件段过滤事件=============

    /**
     * throttleFirst 操作符
     * <p>
     * 指定时间内只获取第一次发送的事件
     * <p>
     * throttleLast 操作符
     * 以固定间隔而不是相对于最后一项来划分时间。它会在每个窗口中发出最后一个值，而不是它后面的第一个值
     * <p>
     * throttleLatest 操作符
     * 指定时间内只获取最新的值
     */
    private static void throttleFirstOperator() {

        Observable.intervalRange(0, 10, 0, 2, TimeUnit.SECONDS)

                //0 3 6 9
                //.throttleFirst(4, TimeUnit.SECONDS)
                //2 4 5 8
                //.throttleLast(4, TimeUnit.SECONDS)
                //
                .throttleLatest(4, TimeUnit.SECONDS)
                .subscribe(createObserver());

        try {
            Thread.sleep(50 * 1000);
        } catch (Exception e) {
        }

    }

    private static <T> Observer<T> createObserver() {

        return new Observer<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                CLog.log("onSubscribe:" + Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull T t) {
                CLog.log("onNext:" + t + " --- " + Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                CLog.log("onSubscribe:" + e.getMessage() + " --- " + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                CLog.log("onComplete:" + Thread.currentThread().getName());
            }
        };
    }
}
