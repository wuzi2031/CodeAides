package com.example.wzm.codeaides.rx_android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.wzm.codeaides.R;
import com.example.wzm.codeaides.rx_android.rxbus.RxBus;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.internal.util.ActionSubscriber;
import rx.schedulers.Schedulers;

/**
 * Created by wzm on 2016/6/8.
 */
public class RxActivity extends AppCompatActivity {


    TextView tv_show;
    Observable.OnSubscribe observable;
    Subscriber subscriber;
    Subscriber subscriber1;
    Observable observable1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android);
        tv_show = (TextView) findViewById(R.id.tv_show);
        TestObservable.from("hello world!").map(new TestObservable.Fun() {
            @Override
            public String call(String string) {
                return string.substring(0, 5);
            }
        }).map(new TestObservable.Fun() {
            @Override
            public String call(String string) {
                return string + "---";
            }
        }).subscribe(new TestObservable.Action() {
            @Override
            public void call(String string) {
                tv_show.setText(string);
            }
        });
        observable = new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hhh");
            }
        };
        subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
//                tv_show.setText(s);
            }
        };
        Observable.create(observable).observeOn(Schedulers.io()).
                subscribeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
        observable1 = RxBus.get().register("set", String.class);//rxBus

        subscriber1 = new Subscriber<String>() {//rxBus
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                tv_show.setText(s);
            }
        };
        observable1.subscribe(subscriber1);//rxBus
        RxBus.get().post("set","kkkk");//rxBus
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscriber != null && subscriber.isUnsubscribed())
            subscriber.unsubscribe();
        RxBus.get().unregister("set",observable1);//rxBus
    }
}
