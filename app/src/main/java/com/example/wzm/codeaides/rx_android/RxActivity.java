package com.example.wzm.codeaides.rx_android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.wzm.codeaides.R;

/**
 * Created by wzm on 2016/6/8.
 */
public class RxActivity extends AppCompatActivity {


    TextView tv_show;

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
                return string+"---";
            }
        }).subscribe(new TestObservable.Action() {
            @Override
            public void call(String string) {
                tv_show.setText(string);
            }
        });

    }

}
