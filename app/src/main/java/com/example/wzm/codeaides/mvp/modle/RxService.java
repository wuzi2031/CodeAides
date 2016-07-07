package com.example.wzm.codeaides.mvp.modle;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by wzm on 2016/6/24.
 */
public class RxService {
    private RxService() {
    }

    private static final String BASETESTURL = "http://apis.baidu.com/showapi_open_bus/";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASETESTURL)
            .client(getClient())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> T creatApi(Class<T> tClass) {
        return retrofit.create(tClass);
    }

    public static <T> T creatApi(Class<T> tClass, String baseurl) {
        return new Retrofit.Builder()
                .baseUrl(baseurl)
                .client(getClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(tClass);
    }

    private static OkHttpClient getClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
//                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                                .addHeader("Accept-Encoding", "gzip, deflate")
//                                .addHeader("Connection", "keep-alive")
//                                .addHeader("Accept", "*/*")
                                .addHeader("apikey", "83ec99fff780989a5376a1bc595ed5ff")
                                .build();
                        return chain.proceed(request);
                    }

                }).build();
        return httpClient;
    }
}
