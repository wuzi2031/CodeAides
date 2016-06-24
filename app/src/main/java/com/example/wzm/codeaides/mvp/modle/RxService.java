package com.example.wzm.codeaides.mvp.modle;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wzm on 2016/6/24.
 */
public class RxService {
    private RxService() {
    }
    private static final String BASETESTURL = "http://apis.baidu.com/showapi_open_bus/";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASETESTURL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> T creatApi(Class<T> tClass) {
        return retrofit.create(tClass);
    }
}
