package ct.com.basics.rx;

import ct.com.basics.CLog;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

/**
 * RxJava 变换操作符
 * <p>
 * map
 * flatMap
 * concatMap
 */
public class RxTransformOperator {


    public static void main(String[] args) {

        //map 操作符
        //mapOperator();

        //flatMap 操作符
        flatMapOperator();

        //concatMap 操作符
        //concatMapOperator();
    }


    /**
     * map 操作符
     * <p>
     * 将T类型转变成R类型 传递给下流
     */
    private static void mapOperator() {

        Observable.just("A")
                .map(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String s) throws Throwable {
                        CLog.log("map操作符:" + s + "---" + Thread.currentThread().getName());
                        return true;
                    }
                })
                .subscribe(createObserver());

    }


    /**
     * flatMap 操作符
     * <p>
     * flatMap使用一个指定的函数对原始Observable发射的每一项数据之行相应的变换操作，
     * 这个函数返回一个本身也发射数据的Observable，然后FlatMap合并这些Observables发射的数据
     * 最后将合并后的结果当做它自己的数据序列发射
     */
    private static void flatMapOperator() {

        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("AAAAA");
                emitter.onNext("AAA");
                emitter.onNext("AAAAAAAAA");
                emitter.onNext("A");
                emitter.onComplete();
            }
        })

                .flatMap(new Function<String, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(String s) throws Throwable {
                        CLog.log("------------------");
                        return Observable.just(s.length(), 23, 33);
                    }
                }, false, 1)
                .subscribe(createObserver());

    }


    /**
     * concatMap 操作符
     * <p>
     * 使用和flatMap操作符一直 原理也是一直的
     * <p>
     * 只不过 flatMap操作符在合并数据时 使用的是(合并)merge 可能出现数据不是按顺序发射的
     * 而    concatMap操作符在合并数据时 使用的是(连接)concat 数据是按顺序发射的
     */
    private static void concatMapOperator() {

        Observable.just("A", "B", "C")
                .concatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Throwable {
                        return Observable.just("concatMap转换:" + s);
                    }
                })
                .subscribe(createObserver());

    }

    private static <T> Observer<T> createObserver() {

        return new Observer<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                CLog.log("onSubscribe:" + Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull T t) {
                CLog.log("+++++++++++++++++++++");
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
