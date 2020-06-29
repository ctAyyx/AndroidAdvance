package ct.com.rxjava.base01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ct.com.rxjava.CLog;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * RxJava 合并操作符
 * <p>
 * 一.组合多个被观察者，合并事件
 * concat | concatArray
 * concatDelayError | concatDelayArrayError
 * merge | mergeArray
 * merge | mergeArray
 * 二.组合多个被观察者，合并为一个被观察者
 * zip
 * combineLatest
 * 三.发送事件前追加其他事件
 * startWith | startWithArray
 * 四.组合多个事件为一个事件
 * reduce
 * collect
 * 五.汇总发送事件数量
 * count
 */
public class RxMergeOperator {
    public static void main(String[] args) {

        //concat|concatArray 操作符
        //concatArrayOperator();

        //concatDelayError | concatDelayArrayError 操作符
        //concatDelayArrayErrorOperator();

        //merge|mergeArray 操作符
        //mergeArrayOperator();

        //mergeDelayError | mergeArrayDelayError 操作符
        //mergeArrayDelayErrorOperator();


        //zip 操作符
        //zipOperator();

        //combineLatest 操作符
        //combineLatestOperator();


        //startWith | startWithArray 操作符
        //startWithArrayOperator();


        //reduce 操作符
        //reduceOperator();

        //collect 操作符
        //collectOperator();


        //count 操作符
        countOperator();

    }


    /**
     * concat/concatArray 操作符
     * concat 支持<=4个观被察者
     * concatArray 支持>=4个被观察者
     * <p>
     * 组合多个被观察者 顺序发送 如果其中一个发射onError 将会停止后续事件继续发射
     */
    private static void concatArrayOperator() {

        Observable<String> observable01 = Observable.just("A");
        Observable<String> observable02 = Observable.just("B");
        Observable<String> observable03 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("C1");
                emitter.onNext("C2");
                emitter.onNext("C3");
                //emitter.onError(new NullPointerException("空指针异常...."));
                //表示该被观察者发射完成 可以发射一个被观察者的数据了
                emitter.onComplete();
            }
        });
        Observable<String> observable04 = Observable.just("D");
        Observable<String> observable05 = Observable.just("E");


        Observable.concatArray(observable01, observable02, observable03, observable04, observable05)
                .subscribe(createObserver());
    }

    /**
     * concatDelayError|concatDelayArrayError 操作符
     * <p>
     * 组合多个被观察者 如果其中一个发射onError 会被推迟到其他被观察者发射完事件
     */
    private static void concatDelayArrayErrorOperator() {
        Observable<String> observable01 = Observable.just("A");
        Observable<String> observable02 = Observable.just("B");
        Observable<String> observable03 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("C1");
                emitter.onNext("C2");
                emitter.onNext("C3");
                emitter.onError(new NullPointerException("空指针异常...."));
            }
        });
        Observable<String> observable04 = Observable.just("D");
        Observable<String> observable05 = Observable.just("E");


        Observable
                .concatArrayDelayError(observable01, observable02, observable03, observable04, observable05)
                .subscribe(createObserver());
    }

    /**
     * merge/mergeArray 操作符
     * merge 支持<=4个观被察者
     * mergeArray 支持>=4个被观察者
     * <p>
     * 组合多个被观察者 并行发送数据 如果其中一个发射onError 将会停止后续事件继续发射
     */
    private static void mergeArrayOperator() {

        Observable<Long> observable01 = Observable.intervalRange(0, 3, 3, 2, TimeUnit.SECONDS);
        Observable<Long> observable02 = Observable.intervalRange(10, 3, 3, 2, TimeUnit.SECONDS);
        Observable<Long> observable03 = Observable.intervalRange(20, 3, 4, 2, TimeUnit.SECONDS).map(new Function<Long, Long>() {
            @Override
            public Long apply(Long aLong) throws Throwable {
                if (aLong == 22)
                    throw new NullPointerException("当前数据错误" + aLong);
                return aLong;
            }
        });
        Observable<Long> observable04 = Observable.intervalRange(30, 3, 4, 2, TimeUnit.SECONDS);
        Observable<Long> observable05 = Observable.intervalRange(40, 3, 5, 2, TimeUnit.SECONDS);


        Observable.mergeArray(observable01, observable02, observable03, observable04, observable05)
                .subscribe(createObserver());

        try {
            Thread.sleep(20 * 1000);
        } catch (Exception e) {
        }

    }

    /**
     * mergeDelayError|mergeArrayDelayError 操作符
     * <p>
     * 组合多个被观察者 并行发送数据 如果其中一个发射onError 会被推迟到其他被观察者发射完事件
     */
    private static void mergeArrayDelayErrorOperator() {
        Observable<Long> observable01 = Observable.intervalRange(0, 3, 3, 2, TimeUnit.SECONDS);
        Observable<Long> observable02 = Observable.intervalRange(10, 3, 3, 2, TimeUnit.SECONDS);
        Observable<Long> observable03 = Observable.intervalRange(20, 3, 4, 2, TimeUnit.SECONDS).map(new Function<Long, Long>() {
            @Override
            public Long apply(Long aLong) throws Throwable {
                if (aLong == 22)
                    throw new NullPointerException("当前数据错误" + aLong);
                return aLong;
            }
        });
        Observable<Long> observable04 = Observable.intervalRange(30, 3, 4, 2, TimeUnit.SECONDS);
        Observable<Long> observable05 = Observable.intervalRange(40, 3, 5, 2, TimeUnit.SECONDS);


        Observable.mergeArrayDelayError(observable01, observable02, observable03, observable04, observable05)
                .subscribe(createObserver());

        try {
            Thread.sleep(20 * 1000);
        } catch (Exception e) {
        }
    }

    //================组合多个被观察者，合并为一个被观察者=====================

    /**
     * zip 操作符
     * <p>
     * 将多个被观察者合并成一个被观察者A 并将多个被观察者的事件合并成A中的事件 事件个数等于最少事件的被观察者
     * 严格按照事件原来的顺序进行对位合并
     * A(3)
     * B(4)
     * ->zip
     * C(3)
     * <p>
     * 第一个被观察者发射全部数据
     * 后续的被观察者依次发射数据
     * 被观察者中多余的事件也是会被发射 只会被zip过滤
     */
    private static void zipOperator() {


        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {

                CLog.e("被观察者A(A)");
                emitter.onNext("A");
                CLog.e("被观察者A(C)");
                emitter.onNext("C");
                CLog.e("被观察者A(D)");
                emitter.onNext("D");
                CLog.e("被观察者A(E)");
                emitter.onNext("E");
            }
        });
        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                CLog.e("被观察者B(10)");
                emitter.onNext(10);
                CLog.e("被观察者B(20)");
                emitter.onNext(20);
                CLog.e("被观察者B(30)");
                emitter.onNext(30);
                CLog.e("被观察者B(50)");
                emitter.onNext(40);
                CLog.e("被观察者B(60)");
                emitter.onNext(50);
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Throwable {
                CLog.e("Zip操作符C:" + s + "---" + integer);
                return s + "--" + integer;
            }
        })
                .subscribe(createObserver());


    }


    /**
     * combineLatest 操作符
     * <p>
     * 当两个Observables中的任何一个发送了数据后，将先发送了数据的Observables 的最新（最后）一个数据 与 另外一个Observable发送的每个数据结合，最终基于该函数的结果发送数据
     */
    private static void combineLatestOperator() {


        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {

                CLog.e("被观察者A(A)");
                emitter.onNext("A");
                CLog.e("被观察者A(C)");
                emitter.onNext("C");
                CLog.e("被观察者A(D)");
                emitter.onNext("D");
                CLog.e("被观察者A(E)");
                emitter.onNext("E");
            }
        });
        Observable<Long> observable2 = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> emitter) throws Throwable {
                CLog.e("被观察者B(10)");
                emitter.onNext(10L);
                CLog.e("被观察者B(20)");
                emitter.onNext(20L);
                CLog.e("被观察者B(30)");
                emitter.onNext(30L);
                CLog.e("被观察者B(40)");
                emitter.onNext(40L);
                CLog.e("被观察者B(50)");
                emitter.onNext(50L);
            }
        });

        //observable2 = Observable.intervalRange(10, 5, 3, 3, TimeUnit.SECONDS);

        Observable
                .combineLatest(observable2, observable1, new BiFunction<Long, String, String>() {
                    @Override
                    public String apply(Long integer, String s) throws Throwable {
                        CLog.e("combineLatest操作符C:" + s + "--" + integer);
                        return s + "--" + integer;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(createObserver());

        try {
            Thread.sleep(20 * 1000);
        } catch (Exception e) {
        }
    }

    //================发送事件前追加其他事件=====================

    /**
     * startWith | startWithArray 操作符
     * <p>
     * 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
     * 后追加的在最前面
     */
    private static void startWithArrayOperator() {

        Observable.just("A", "B", "C")
                .startWithArray("D", "E", "F")
                .startWith(Observable.fromArray("1", "2", "3"))
                .subscribe(createObserver());

    }


    //=============组合多个事件为一个事件===================

    /**
     * reduce 操作符
     * <p>
     * 把被观察者需要发送的事件聚合成1个事件并且发送
     * 本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推
     */
    private static void reduceOperator() {

        Observable.just(1, 2, 3, 4)
                .reduce(10, new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Throwable {
                        CLog.e("reduce操作符:" + integer + "---" + integer2);
                        return integer + integer2;
                    }
                })
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        CLog.e("reduce操作符-最后的结果是:" + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }


                });
    }


    /**
     * collect 操作符
     * <p>
     * 将被观察者Observable发送的数据事件收集到一个数据结构里
     */
    private static void collectOperator() {
        Observable

                .just("A", "B", "C")
                .collect(new Supplier<List<String>>() {

                    @Override
                    public List<String> get() throws Throwable {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<List<String>, String>() {
                    @Override
                    public void accept(List<String> strings, String s) throws Throwable {
                        strings.add(s);
                    }
                })
                .subscribe(new SingleObserver<List<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<String> strings) {
                        CLog.e("collect操作符-最后获取的数据:" + strings);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    //================汇总发送事件数量================

    /**
     * count 操作符
     * 统计被观察者发送事件的数量
     */
    private static void countOperator() {

      Observable.just("A","B","C")
              .count()
              .subscribe(new SingleObserver<Long>() {
                  @Override
                  public void onSubscribe(@NonNull Disposable d) {

                  }

                  @Override
                  public void onSuccess(@NonNull Long aLong) {
                      CLog.e("count操作符-事件发射的次数:"+aLong);
                  }

                  @Override
                  public void onError(@NonNull Throwable e) {

                  }
              });
    }


    private static <T> Observer<T> createObserver() {

        return new Observer<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                CLog.e("onSubscribe:" + Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull T t) {
                CLog.e("onNext:" + t + " --- " + Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                CLog.e("onSubscribe:" + e.getMessage() + " --- " + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                CLog.e("onComplete:" + Thread.currentThread().getName());
            }
        };
    }
}
