package ct.com.basics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * TIME : 2020/6/20 0020
 * AUTHOR : CT
 * DESC :
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_main);
        new Thread() {
            @Override
            public void run() {


            }
        }.start();
        test();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reflect:
                readyGoReflectJobActivity();
                break;
            case R.id.btn_reflect3:

                break;
        }
    }

    private void readyGoReflectJobActivity() {


    }

    private void test() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                Log.e("TAG", "subscribe -- Thread:" + Thread.currentThread().getName());
                emitter.onNext("A");
                emitter.onComplete();
            }
        })
                .subscribeOn(
                        Schedulers.io()
                )
//                .observeOn(
//                        AndroidSchedulers.mainThread()
//                )
                .subscribe(
                        new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.e("TAG", "onSubscribe -- Thread:" + Thread.currentThread().getName());
                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                Log.e("TAG", "onNext -- Thread:" + Thread.currentThread().getName());
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("TAG", "onError -- Thread:" + Thread.currentThread().getName());
                            }

                            @Override
                            public void onComplete() {
                                Log.e("TAG", "onComplete -- Thread:" + Thread.currentThread().getName());
                            }
                        });
    }

}
