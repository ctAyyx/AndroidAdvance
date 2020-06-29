package ct.com.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * 针对 RxJava的使用 原理
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Thread(){
            @Override
            public void run() {
                test();
            }
        }.start();
    }


    private void test() {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {

                emitter.onNext("A");
                emitter.onComplete();
                Log.e("TAG","自定义Source工作在:" + Thread.currentThread().getName());
            }
        })

                //.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("TAG","自定义Observer --- onSubscribe在:" + Thread.currentThread().getName());

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.e("TAG","自定义Observer --- onNext在:" + Thread.currentThread().getName());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG","自定义Observer --- onComplete在:" + Thread.currentThread().getName());

                    }
                });
    }


    private void flow01() {
        Observable.create(

                //TODO 自定义的source
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                        emitter.onNext("SSS");
                    }
                })


                //相当于 ObservableCreate.subscribe
                .subscribe(


                        //TODO 观察者  终点
                        new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull String s) {

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
    }


    private void flow02() {
        Observable.create(


                new ObservableOnSubscribe<String>() {

                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {

                    }
                })

                //相当于 ObservableCreate.map
                .map(


                        new Function<String, Boolean>() {
                            @Override
                            public Boolean apply(String s) throws Throwable {
                                return null;
                            }
                        })
                //相当于 ObservableMap.subscribe
                .subscribe(


                        //TODO 自定义观察者 终点
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull Boolean aBoolean) {

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
    }


    private void threadScheduling01() {


        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {

            }
        })

                //ObservableCreate.subscribeOn

                .subscribeOn(

                        Schedulers.io()


                )


//ObservableSubscribeOn
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    private void threadScheduling02() {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {

            }
        })

                .observeOn(


                       // AndroidSchedulers.mainThread()
                        Schedulers.io()


                )


                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}