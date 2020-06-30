package ct.com.basics.rx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ct.com.basics.CLog;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;

/**
 * RxJava 条件操作符
 * <p>
 * 一.判断所有事件是否满足
 * all
 */
public class RxConditionOperator {
    public static void main(String[] args) {
        //all 操作符
        //allOperator();

        //takeWhile 操作符
        //takeWhileOperator();

        //skipWhile 操作符
        //skipWhileOperator();

        //takeUnit 操作符
        //takeUntilOperator();

        //skipUnit 操作符
        //skipUntilOperator();

        //sequenceEqual 操作符
        //sequenceEqualOperator();

        //contain 操作符
        //containsOperator();

        //isEmpty 操作符
        //isEmptyOperator();

        //amb 操作符
        ambOperator();

    }

    //===========判断所有事件是否满足=================

    /**
     * all 操作符
     * <p>
     * 判断每个发射的事件是否满足条件 所有都满足返回true 否则返回false
     */
    private static void allOperator() {

        Observable.just("A", "B", "C")
                .all(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Throwable {
                        return s.length() == 1;
                    }
                })
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        CLog.log("all操作符:" + aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    //==========发送的事件判断条件不满足时，就会终止后续事件接收========

    /**
     * takeWhile 操作符
     * <p>
     * 当发射的事件不满足条件的时候 就会终止后续事件的继续接收
     */
    private static void takeWhileOperator() {

        Observable.just("A", "B", "C", "DD", "F", "E")
                .takeWhile(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Throwable {
                        return s.length() == 1;
                    }
                })
                .subscribe(createObserver());
    }

    //==========发送的事件判断条件不满足时，才接收后续的事件============

    /**
     * skipWhile 操作符
     * 当发射的事件不满足条件的时候 才开始接收发射的事件 和takeWhile相反
     */
    private static void skipWhileOperator() {
        Observable.just("A", "B", "C", "DD", "F", "E")
                .skipWhile(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Throwable {
                        return s.length() == 1;
                    }
                }).subscribe(createObserver());
    }


    //==========过滤事件=================

    /**
     * takeUntil 操作符
     * <p>
     * 过滤事件 直到满足条件后不再接收事件
     */
    private static void takeUntilOperator() {
        Observable.just("A", "B", "C", "DD", "F", "E")
                .takeUntil(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Throwable {
                        return s.length() == 1;
                    }
                }).subscribe(createObserver());
    }

    //============接收判断条件满足之外的事件===============

    /**
     * skipUnit 操作符
     * <p>
     * 不接收发射的事件 直到第二个Observable发射一个事件 才开始接收原来Observable的事件
     */
    private static void skipUntilOperator() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .skipUntil(Observable.timer(5, TimeUnit.SECONDS))
                .subscribe(createObserver());

        try {
            Thread.sleep(20 * 1000);
        } catch (Exception e) {
        }

    }

    //============判断2个被观察者发生的事件是否一样===============

    /**
     * sequenceEqual 操作符
     * 判断两个Observable发射的事件是否一致
     */
    private static void sequenceEqualOperator() {

        Observable.sequenceEqual(Observable.just("A"), Observable.just("B"))
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {

                        CLog.log("sequenceEqual:" + aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    //==========判断发送的数据里面是否包含指定数据==============

    /**
     * contain 操作符
     * 判断发射的事件是否有指定的事件
     */
    private static void containsOperator() {
        Observable.just("A", "B", "C")
                .contains("B")
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        CLog.log("contain操作符:" + aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    //========判断发送的数据是否为空=============

    /**
     * isEmpty 操作符
     * 判断发射的事件是否是空事件
     */
    private static void isEmptyOperator() {
        Observable.just("A", "B", "C")
                .isEmpty()
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        CLog.log("isEmpty操作符:" + aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    //======多个被观察者，只接收"第一个成功发送数据的被观察者"=======

    /**
     * amb 操作符
     *
     * 多个被观察者 只接受第一个成功发送数据的被观察者
     */
    private static void ambOperator() {
        Observable<String> observable01 = Observable.just("A");
        Observable<String> observable02 = Observable.just("B");
        Observable<String> observable03 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("C1");
                emitter.onNext("C2");
                emitter.onNext("C3");
                emitter.onError(new NullPointerException("空指针异常...."));
                //表示该被观察者发射完成 可以发射一个被观察者的数据了
                // emitter.onComplete();
            }
        });
        Observable<String> observable04 = Observable.just("D");
        Observable<String> observable05 = Observable.just("E");

        List<ObservableSource<String>> mList = new ArrayList<>();
        Observable.<String>ambArray(observable03,observable01, observable02,  observable04, observable05)
                .subscribe(createObserver());
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

