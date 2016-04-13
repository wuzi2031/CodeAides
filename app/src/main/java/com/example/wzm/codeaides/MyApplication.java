package com.example.wzm.codeaides;

import android.app.Application;

import com.example.wzm.codeaides.push.UmengPushUtils;

/**
 * Created by wzm on 2016/4/12.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UmengPushUtils.initApplica(this, true);
    }

}
