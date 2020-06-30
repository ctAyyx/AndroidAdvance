package ct.com.basics.rx;

import java.util.concurrent.TimeUnit;

import ct.com.basics.CLog;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;

/**
 * RxJava 其它功能操作符
 * 一.常用的do系列操作符
 */
public class RxFunctionOperator {
    public static void main(String[] args) {

        //do系列的操作符
        // doOperator();

        //onErrorReturn 操作符
        //onErrorReturnOperator();

        //onErrorResumeNext 操作符
        //onErrorResumeNextOperator();

        //retry 操作符
        //retryOperator();


        //repeat 操作符
        //repeatOperator();

        //repeatWhen 操作符
        //repeatWhenOperator();

        //repeatUntil 操作符
        //repeatUntilOperator();

        //delay 操作符
        // delayOperator();

        timeoutOperator();
    }

    //=========常用的do系列操作符=============

    /**
     * doOnSubscribe 在订阅之前调用
     * doOnNext 在onNext之前调用
     * doOnAfterNext 在onNext之后调用
     * doOnComplete 在onComplete之前调用
     * doOnError 在onError之前调用
     * doOnEach 在观察者的onNext onError onComplete方法之前调用一次
     */
    private static void doOperator() {

        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
                emitter.onError(new NullPointerException("数据为空"));
                //emitter.onComplete();
            }
        })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {

                        CLog.log("doOnSubscribe操作符");
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        CLog.log("doOnNext操作符:" + s);
                    }
                })
                .doAfterNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        CLog.log("doAfterNext操作符:" + s);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        CLog.log("doOnError操作符:" + throwable.getMessage());
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Throwable {
                        CLog.log("doOnComplete操作符");
                    }
                })
                .doOnEach(new Consumer<Notification<String>>() {
                    @Override
                    public void accept(Notification<String> stringNotification) throws Throwable {
                        CLog.log("doOnEach操作符:" + stringNotification.getValue());
                    }
                })
                .subscribe(createObserver());


    }


    //=======错误/异常处理===========

    /**
     * onErrorReturn 操作符
     * <p>
     * 捕获异常 并返回特殊的结果 正常终止剩余事件发射
     */
    private static void onErrorReturnOperator() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("A");
                emitter.onNext("B");
                String a = null;
                a.length();
                // emitter.onError(new NullPointerException("空数据异常"));
                emitter.onNext("C");
            }
        })
                .onErrorReturn(new Function<Throwable, String>() {
                    @Override
                    public String apply(Throwable throwable) throws Throwable {
                        return "处理异常";
                    }
                })
                .subscribe(createObserver());
    }


    /**
     * 捕获异常 并返回一个新的被观察者
     */
    private static void onErrorResumeNextOperator() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("A");
                emitter.onNext("B");
                String a = null;
                a.length();
                // emitter.onError(new NullPointerException("空数据异常"));
                emitter.onNext("C");
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> apply(Throwable throwable) throws Throwable {
                return Observable.just("处理异常", "D", "F");
            }
        }).subscribe(createObserver());
    }

    /**
     * retry 操作符
     * <p>
     * 异常重试 可以控制重试的次数 和 那些异常才重试
     */
    private static void retryOperator() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("A");
                emitter.onNext("B");
                String a = null;
                a.length();
                // emitter.onError(new NullPointerException("空数据异常"));
                emitter.onNext("C");
            }
        })
                .retry(new Predicate<Throwable>() {
                    @Override
                    public boolean test(Throwable throwable) throws Throwable {
                        return false;
                    }
                })
                .subscribe(createObserver());
    }


    //===========事件重发==============

    /**
     * repeat 操作符
     * 事件重复发射 可以指定总共发射次数
     */
    private static void repeatOperator() {
        Observable.just("A", "C")
                .repeat(2)
                .subscribe(createObserver());
    }


    /**
     * repeatWhen 操作符
     * <p>
     * 在事件发射完成后 再次根据返回的被观察重新发射事件 线程切换为返回的被观察者线程
     */
    private static void repeatWhenOperator() {
        Observable.just("A", "C")
                .repeatWhen(new Function<Observable<Object>, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Observable<Object> objectObservable) throws Throwable {
                        //return Observable.just(1, 2, 3);
                        return Observable.timer(2, TimeUnit.SECONDS);
                    }
                }).subscribe(createObserver());

        try {
            Thread.sleep(10 * 1000);
        } catch (Exception e) {
        }
    }


    /**
     * repeatUntil 操作符
     * <p>
     * 一直重复事件发射 直到满足停止条件
     */
    private static void repeatUntilOperator() {
        Observable.just("A", "C")
                .repeatUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() throws Throwable {
                        return true;
                    }
                })
                .subscribe(createObserver());
    }

    //======延迟发送被观察者的事件===========
    private static void delayOperator() {
        Observable.just("A", "C")
                .delay(5, TimeUnit.SECONDS)
                .subscribe(createObserver());

        try {
            Thread.sleep(10 * 1000);
        } catch (Exception e) {
        }
    }

    //=========发送事件超时处理==========
    private static void timeoutOperator() {
        Observable.timer(15,TimeUnit.SECONDS)
                .timeout(5, TimeUnit.SECONDS, new ObservableSource<Long>() {
                    @Override
                    public void subscribe(@NonNull Observer<? super Long> observer) {
                        CLog.log("----TimeOut");
                        observer.onNext(1L);
                        observer.onComplete();
                    }
                })
                .subscribe(createObserver());


        try {
            Thread.sleep(20 * 1000);
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
